package com.example.HomeService_MVC.service.interfaces;

import com.example.HomeService_MVC.dto.order.OrderDTO;
import com.example.HomeService_MVC.model.Order;

import java.util.List;

public interface OrderService {
    void PlaceAnOffer(Integer customerId,OrderDTO orderDTO,Integer subServicesId);
    List<Order> findAllByCustomerId(Integer customerId);
}
