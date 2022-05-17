package com.example.HomeService_MVC.service.interfaces;

import com.example.HomeService_MVC.dto.order.OrderDTO;

public interface OrderService {
    void PlaceAnOffer(Integer customerId,OrderDTO orderDTO,Integer subServicesId);
}
