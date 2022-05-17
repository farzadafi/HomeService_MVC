package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.controller.exception.InvalidProposedPriceException;
import com.example.HomeService_MVC.controller.exception.SubServicesNotFoundException;
import com.example.HomeService_MVC.dto.order.OrderDTO;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.model.Offer;
import com.example.HomeService_MVC.model.Order;
import com.example.HomeService_MVC.model.SubServices;
import com.example.HomeService_MVC.model.enumoration.OrderStatus;
import com.example.HomeService_MVC.repository.OrderRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import com.example.HomeService_MVC.service.interfaces.OrderService;

import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpel implements OrderService {

    private final SubServicesServiceImpel subServicesServiceImpel;
    private final CustomerServiceImpel customerServiceImpel;
    private final DozerBeanMapper mapper;
    private final OrderRepository orderRepository;

    public OrderServiceImpel(SubServicesServiceImpel subServicesServiceImpel, CustomerServiceImpel customerServiceImpel, DozerBeanMapper mapper, OrderRepository orderRepository) {
        this.subServicesServiceImpel = subServicesServiceImpel;
        this.customerServiceImpel = customerServiceImpel;
        this.mapper = mapper;
        this.orderRepository = orderRepository;
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
    public List<Order> findAllByCustomerId(Integer customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }

    @Override
    public List<Order> findAllStartOrder(Integer customerId) {
        return orderRepository.findAllByCustomerIdAndOrderStatus(customerId, OrderStatus.EXPERT_SUGGESTION,OrderStatus.EXPERT_SELECTION);
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
}
