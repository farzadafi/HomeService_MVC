package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.controller.exception.InvalidProposedPriceException;
import com.example.HomeService_MVC.controller.exception.NotEnoughBalanceException;
import com.example.HomeService_MVC.controller.exception.OrderNotFoundException;
import com.example.HomeService_MVC.controller.exception.SubServicesNotFoundException;
import com.example.HomeService_MVC.dto.order.OrderDTO;
import com.example.HomeService_MVC.model.*;
import com.example.HomeService_MVC.model.enumoration.OrderStatus;
import com.example.HomeService_MVC.repository.OrderRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import com.example.HomeService_MVC.service.interfaces.OrderService;

import java.util.ArrayList;
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
    public void PlaceAnOrder(Integer customerId,OrderDTO orderDTO, Integer subServicesId) {
        Customer customer = customerServiceImpel.getById(customerId);
        SubServices subServices = subServicesServiceImpel.findById(subServicesId).orElseThrow(() -> new SubServicesNotFoundException("This subServices not found!"));
        if(subServices.getMinimalPrice() > orderDTO.getProposedPrice())
            throw new InvalidProposedPriceException("You have to enter a price grater than " + subServices.getMinimalPrice());
        Order order = mapper.map(orderDTO,Order.class);
        order.setCustomer(customer);
        order.setSubService(subServices);
        order.setOrderStatus(OrderStatus.EXPERT_SUGGESTION);
        orderRepository.save(order);
    }

    @Override
    public List<OrderDTO> findAllByCustomerId(Integer customerId) {
        List<Order> orderList = orderRepository.findAllByCustomerId(customerId);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("You dont have any order until now!");
        else
            return orderToOrderDTO(orderList);
    }

    @Override
    public List<OrderDTO> findAllStartOrder(Integer customerId) {
        List<Order> orderList = orderRepository.findAllByCustomerIdAndTwoOrderStatus(customerId, OrderStatus.EXPERT_SUGGESTION,OrderStatus.EXPERT_SELECTION);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("You dont have any order in started status!");
        else
            return orderToOrderDTO(orderList);
    }

    @Override
    public List<OrderDTO> findAllStartedOrderByCity(String city, Set<SubServices> subServices) {
        List<Order> orderList = orderRepository.findAllStartedOrderByCity(city,subServices,OrderStatus.EXPERT_SUGGESTION,OrderStatus.EXPERT_SELECTION);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("You dont have any order for your subServices and city!");
        else
            return orderToOrderDTO(orderList);

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
    public List<OrderDTO> findAllByCustomerIdAndOrderStatus(Integer customerId, OrderStatus orderStatus) {
        List<Order> orderList = orderRepository.findAllByCustomerIdAndOrderStatus(customerId,orderStatus);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("You dont have any order In Started Status!");
        else
            return orderToOrderDTO(orderList);
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


    public List<OrderDTO> orderToOrderDTO(List<Order> orderList){
        List<OrderDTO> dtoList = new ArrayList<>();
        for (Order o:orderList
        ) {
            dtoList.add(mapper.map(o,OrderDTO.class));
        }
        return dtoList;
    }
}
