package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.controller.exception.OrderNotFoundException;
import com.example.HomeService_MVC.dto.order.OrderDTO;
import com.example.HomeService_MVC.model.Order;
import com.example.HomeService_MVC.service.impel.OrderServiceImpel;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpel orderServiceImpel;
    private final DozerBeanMapper mapper;

    public OrderController(OrderServiceImpel orderServiceImpel, DozerBeanMapper mapper) {
        this.orderServiceImpel = orderServiceImpel;
        this.mapper = mapper;
    }

    @PostMapping("/placeAnOffer/{subServicesId}")
    public ResponseEntity<String> placeAnOffer(@Valid @RequestBody OrderDTO orderDTO, @PathVariable("subServicesId") Integer subServicesId){
        orderServiceImpel.PlaceAnOffer(1,orderDTO,subServicesId);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/viewOrder")
    public ResponseEntity<List<OrderDTO>> viewOrder(){
        List<Order> orderList = orderServiceImpel.findAllByCustomerId(1);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("You dont have any order until now!");
        else{
            List<OrderDTO> dtoList = new ArrayList<>();
            for (Order o:orderList
            ) {
                dtoList.add(mapper.map(o,OrderDTO.class));
            }
            return ResponseEntity.ok(dtoList);
        }
    }
}
