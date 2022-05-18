package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.controller.exception.OrderNotFoundException;
import com.example.HomeService_MVC.dto.order.OrderDTO;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.Offer;
import com.example.HomeService_MVC.model.Order;
import com.example.HomeService_MVC.model.enumoration.OrderStatus;
import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
import com.example.HomeService_MVC.service.impel.OfferServiceImpel;
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
    private final ExpertServiceImpel expertServiceImpel;
    private final OfferServiceImpel offerServiceImpel;

    public OrderController(OrderServiceImpel orderServiceImpel, DozerBeanMapper mapper, ExpertServiceImpel expertServiceImpel, OfferServiceImpel offerServiceImpel) {
        this.orderServiceImpel = orderServiceImpel;
        this.mapper = mapper;
        this.expertServiceImpel = expertServiceImpel;
        this.offerServiceImpel = offerServiceImpel;
    }

    @PostMapping("/placeAnOffer/{subServicesId}")
    public ResponseEntity<String> placeAnOffer(@Valid @RequestBody OrderDTO orderDTO, @PathVariable("subServicesId") Integer subServicesId){
        orderServiceImpel.PlaceAnOrder(1,orderDTO,subServicesId);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/viewOrder")
    public ResponseEntity<List<OrderDTO>> viewOrder(){
        List<Order> orderList = orderServiceImpel.findAllByCustomerId(1);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("You dont have any order until now!");
        else{
            List<OrderDTO> dtoList = orderToOrderDTO(orderList);
            return ResponseEntity.ok(dtoList);
        }
    }

    @GetMapping("/viewStartedOrder")
    public ResponseEntity<List<OrderDTO>> viewStartedOrder(){
        List<Order> orderList = orderServiceImpel.findAllStartOrder(1);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("You dont have any order in started status!");
        else{
            List<OrderDTO> dtoList = orderToOrderDTO(orderList);
            return ResponseEntity.ok(dtoList);
        }
    }

    @GetMapping("/viewStartedOrderByCity")
    public ResponseEntity<List<OrderDTO>> viewStartedOrderByCity(){
        Expert expert = expertServiceImpel.getById(1);
        List<Order> orderList = orderServiceImpel.findAllStartedOrderByCity(expert.getCity(),expert.getSubServices());
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("You dont have any order for your subServices and city!");
        else{
            List<OrderDTO> dtoList = orderToOrderDTO(orderList);
            return ResponseEntity.ok(dtoList);
        }
    }

    @GetMapping("/startOrder/{offerId}")
    public ResponseEntity<String> startOrder(@PathVariable("offerId") Integer offerId){
        Offer offer = offerServiceImpel.getById(offerId);
        orderServiceImpel.updateStatusToStart(offer);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/viewOrderForDone")
    public ResponseEntity<List<OrderDTO>> viewOrderForDone(){
        List<Order> orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(1, OrderStatus.STARTED);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("You dont have any order In Started Status!");
        else{
            List<OrderDTO> dtoList = orderToOrderDTO(orderList);
            return ResponseEntity.ok(dtoList);
        }
    }

    @GetMapping("/doneOrder/{orderId}")
    public ResponseEntity<String> doneOrder(@PathVariable("orderId") Integer orderId){
        orderServiceImpel.setDoneOrder(orderId);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/viewOrderForPaid")
    public ResponseEntity<List<OrderDTO>> viewOrderForPaid(){
        List<Order> orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(1, OrderStatus.DONE);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("You dont have any order In Done Status!");
        else{
            List<OrderDTO> dtoList = orderToOrderDTO(orderList);
            return ResponseEntity.ok(dtoList);
        }
    }

    @GetMapping("/PaidOrder/{orderId}")
    public ResponseEntity<String> paidOrder(@PathVariable("orderId") Integer orderId){
        Offer offer = offerServiceImpel.findByOrderIdAndAcceptedTrue(orderId);
        orderServiceImpel.paidOrder(1,orderId,offer);
        return ResponseEntity.ok("OK");
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
