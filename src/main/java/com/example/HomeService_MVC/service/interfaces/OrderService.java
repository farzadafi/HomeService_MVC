package com.example.HomeService_MVC.service.interfaces;

import com.example.HomeService_MVC.dto.order.OrderDTO;
import com.example.HomeService_MVC.model.Order;
import com.example.HomeService_MVC.model.SubServices;

import java.util.List;
import java.util.Set;

public interface OrderService {
    void PlaceAnOrder(Integer customerId,OrderDTO orderDTO,Integer subServicesId);
    List<Order> findAllByCustomerId(Integer customerId);
    List<Order> findAllStartOrder(Integer customerId);
    List<Order> findAllStartedOrderByCity(String city, Set<SubServices> subServices);
    Order getById(Integer id);
    void update(Order order);
}
