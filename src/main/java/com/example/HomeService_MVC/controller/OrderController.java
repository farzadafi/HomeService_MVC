package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.dto.order.OrderDTO;
import com.example.HomeService_MVC.service.impel.OrderServiceImpel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpel orderServiceImpel;

    public OrderController(OrderServiceImpel orderServiceImpel) {
        this.orderServiceImpel = orderServiceImpel;
    }

    @PostMapping("/placeAnOffer/{subServicesId}")
    public ResponseEntity<String> placeAnOffer(@Valid @RequestBody OrderDTO orderDTO, @PathVariable("subServicesId") Integer subServicesId){
        orderServiceImpel.PlaceAnOffer(1,orderDTO,subServicesId);
        return ResponseEntity.ok("OK");
    }
}
