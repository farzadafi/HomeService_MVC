package com.example.HomeService_MVC.service.interfaces;

import com.example.HomeService_MVC.dto.order.OrderDto;
import com.example.HomeService_MVC.model.Offer;
import com.example.HomeService_MVC.model.Order;
import com.example.HomeService_MVC.model.SubServices;
import com.example.HomeService_MVC.model.enumoration.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderService {
    void PlaceAnOrder(Integer subServicesId, OrderDto orderDTO);
    List<Order> findAllByCustomerId(Integer customerId);
    List<Order> findAllExpertSelectionOrder(Integer customerId);
    List<Order> findAllStartedOrderByCity(String city, Set<SubServices> subServices);
    Order getById(Integer id);
    void update(Order order);
    void updateStatusToStart(Offer offer);
    List<Order> findAllByCustomerIdAndOrderStatus(Integer customerId, OrderStatus orderStatus);
    String setDoneOrder(Offer offer,Integer orderId);
    void paidOrder(Offer offer);
    void paidOnlineOrder(Offer offer);
    Optional<Order> findById(Integer id);
}
