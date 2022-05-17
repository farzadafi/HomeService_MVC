package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.controller.exception.InvalidProposedPriceException;
import com.example.HomeService_MVC.controller.exception.SubServicesNotFoundException;
import com.example.HomeService_MVC.dto.order.OrderDTO;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.model.Order;
import com.example.HomeService_MVC.model.SubServices;
import com.example.HomeService_MVC.repository.OrderRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import com.example.HomeService_MVC.service.interfaces.OrderService;

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
    public void PlaceAnOffer(Integer customerId,OrderDTO orderDTO, Integer subServicesId) {
        Customer customer = customerServiceImpel.getById(customerId);
        SubServices subServices = subServicesServiceImpel.findById(subServicesId).orElseThrow(() -> new SubServicesNotFoundException("This subServices not found!"));
        if(subServices.getMinimalPrice() < orderDTO.getProposedPrice())
            throw new InvalidProposedPriceException("You have to enter a price grater than " + subServices.getMinimalPrice());
        Order order = mapper.map(orderDTO,Order.class);
        order.setCustomer(customer);
        orderRepository.save(order);
    }
}
