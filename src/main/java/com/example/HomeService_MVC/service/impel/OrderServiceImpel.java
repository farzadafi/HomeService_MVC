package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.controller.exception.InvalidDateException;
import com.example.HomeService_MVC.controller.exception.InvalidProposedPriceException;
import com.example.HomeService_MVC.controller.exception.NotEnoughBalanceException;
import com.example.HomeService_MVC.controller.exception.SubServicesNotFoundException;
import com.example.HomeService_MVC.core.SecurityUtil;
import com.example.HomeService_MVC.dto.order.OrderDTO;
import com.example.HomeService_MVC.model.*;
import com.example.HomeService_MVC.model.enumoration.OrderStatus;
import com.example.HomeService_MVC.repository.OrderRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import com.example.HomeService_MVC.service.interfaces.OrderService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpel implements OrderService {

    private final SubServicesServiceImpel subServicesServiceImpel;
    private final CustomerServiceImpel customerServiceImpel;
    private final DozerBeanMapper mapper;
    private final OrderRepository orderRepository;
    private final ExpertServiceImpel expertServiceImpel;

    public OrderServiceImpel(SubServicesServiceImpel subServicesServiceImpel, CustomerServiceImpel customerServiceImpel, DozerBeanMapper mapper, OrderRepository orderRepository, ExpertServiceImpel expertServiceImpel) {
        this.subServicesServiceImpel = subServicesServiceImpel;
        this.customerServiceImpel = customerServiceImpel;
        this.mapper = mapper;
        this.orderRepository = orderRepository;
        this.expertServiceImpel = expertServiceImpel;
    }


    @Override
    public void PlaceAnOrder(Integer subServicesId,OrderDTO orderDTO) {
        Customer customer = (Customer) SecurityUtil.getCurrentUser();
        SubServices subServices = subServicesServiceImpel.findById(subServicesId).orElseThrow(() -> new SubServicesNotFoundException("This subServices not found!"));
        if(subServices.getMinimalPrice() > orderDTO.getProposedPrice())
            throw new InvalidProposedPriceException("شما باید مبلغی بیش از " + subServices.getMinimalPrice() + "وارد کنید");
        LocalDate localDate = LocalDate.now();
        if(orderDTO.getDate().before(Date.from(localDate.atStartOfDay(ZoneId.of("Asia/Tehran")).toInstant())))
            throw new InvalidDateException("لطفا تاریخی بعد از الان اتخاب کنید");
        Order order = mapper.map(orderDTO,Order.class);
        order.setDate(orderDTO.getDate());
        order.setCustomer(customer);
        order.setSubService(subServices);
        order.setOrderStatus(OrderStatus.EXPERT_SUGGESTION);
        orderRepository.save(order);
    }

    @Override
    public List<Order> findAllByCustomerId(Integer customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }

    @Override
    public List<Order> findAllExpertSelectionOrder(Integer customerId) {
        return orderRepository.findAllByCustomerIdAndOrderStatus(customerId, OrderStatus.EXPERT_SELECTION);
    }

    @Override
    public List<Order> findAllStartedOrderByCity(String city, Set<SubServices> subServices) {
        return orderRepository.findAllStartedOrderByCity(city,subServices,OrderStatus.EXPERT_SUGGESTION,OrderStatus.EXPERT_SELECTION);
    }

    @Override
    public Order getById(Integer id) {
        return orderRepository.getById(id);
    }

    @Override
    public void update(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void updateStatusToStart(Offer offer) {
        Order order = offer.getOrders();
        order.setOrderStatus(OrderStatus.STARTED);
        orderRepository.save(order);
    }

    @Override
    public List<Order> findAllByCustomerIdAndOrderStatus(Integer customerId, OrderStatus orderStatus) {
        return orderRepository.findAllByCustomerIdAndOrderStatus(customerId,orderStatus);
    }

    @Override
    public String setDoneOrder(Offer offer,Integer orderId) {
        Order order = getById(orderId);
        String startOrderDateString = String.valueOf(order.getDate()).split(" ")[0] + " " + offer.getStartTime() + ":00";
        startOrderDateString = startOrderDateString.replace("-","/");
        Date startOrderDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            startOrderDate = format.parse(startOrderDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        assert startOrderDate != null;
        long duration  = date.getTime() - startOrderDate.getTime();
        long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
        int different = Math.toIntExact(diffInHours - offer.getDurationWork());
        order.setOrderStatus(OrderStatus.DONE);
        if(different > 0 ){
            Expert expert = offer.getExpert();
            String message;
            int numberPercent = different * 5 ;
            if(numberPercent > 100){
                expert = offer.getExpert();
                expert.setStars(0);
                message = "برای " + different + " ساعت تأخیر " + "امتیاز آقای " + expert.getFirstName() + " " + expert.getLastName() + "برابر شد با صفر!";
            }
            else {
                numberPercent = 100 - numberPercent;
                int numberMinesStars = (numberPercent * offer.getExpert().getStars()) / 100;
                expert.setStars(expert.getStars() - numberMinesStars);
                message = "برای " + different + " ساعت تأخیر " + " از آقای " + expert.getFirstName() + " " + expert.getLastName() + "تعداد  " + numberMinesStars + " امتیاز کم شد!";
            }
            expertServiceImpel.updateStars(expert);
            orderRepository.save(order);
            return message;
        }else{
            orderRepository.save(order);
            return "OK";
        }
    }

    @Override
    public void paidOrder(Offer offer) {
        Customer customer = (Customer) SecurityUtil.getCurrentUser();
        Expert expert = offer.getExpert();
        if(customer.getBalance() < offer.getProposedPrice())
            throw new NotEnoughBalanceException("متأسفانه اعتبار شما برای پرداخت کافی نیست!");
        Order order = offer.getOrders();
        order.setOrderStatus(OrderStatus.PAID);
        customer.setBalance(customer.getBalance() - offer.getProposedPrice());
        Long price = (offer.getProposedPrice() * 70 ) / 100;
        expert.setBalance(expert.getBalance() + price);
        expertServiceImpel.updateBalance(expert);
        customerServiceImpel.updateBalance(customer);
        orderRepository.save(order);
    }

    @Override
    public void paidOnlineOrder(Offer offer) {
        Expert expert = offer.getExpert();
        Order order = offer.getOrders();
        order.setOrderStatus(OrderStatus.PAID);
        Long price = (offer.getProposedPrice() * 70 ) / 100;
        expert.setBalance(expert.getBalance() + price);
        expertServiceImpel.updateBalance(expert);
        orderRepository.save(order);
    }
}
