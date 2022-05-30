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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.HomeService_MVC.service.interfaces.OrderService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
        order.setWorkDate(orderDTO.getDate());
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
    public List<Order> findAllStartOrder(Integer customerId) {
        return orderRepository.findAllByCustomerIdAndTwoOrderStatus(customerId, OrderStatus.EXPERT_SUGGESTION,OrderStatus.EXPERT_SELECTION);
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
    public void setDoneOrder(Integer orderId) {
        Order order = getById(orderId);
        order.setOrderStatus(OrderStatus.DONE);
        orderRepository.save(order);
    }

    @Override
    public void paidOrder(Integer customerId, Integer orderId,Offer offer) {
        Customer customer = customerServiceImpel.getById(customerId);
        Expert expert = offer.getExpert();
        if(customer.getBalance() < offer.getProposedPrice())
            throw new NotEnoughBalanceException("You dont have enough money in your account!");
        Order order = getById(orderId);
        order.setOrderStatus(OrderStatus.PAID);
        customer.setBalance(customer.getBalance() - offer.getProposedPrice());
        expert.setBalance(offer.getProposedPrice());
        expertServiceImpel.updateBalance(expert);
        customerServiceImpel.updateBalance(customer);
        orderRepository.save(order);
    }
}
