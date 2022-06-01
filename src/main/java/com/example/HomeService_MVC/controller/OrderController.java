package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.controller.exception.OrderNotFoundException;
import com.example.HomeService_MVC.core.SecurityUtil;
import com.example.HomeService_MVC.dto.order.OrderDTO;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.Offer;
import com.example.HomeService_MVC.model.Order;
import com.example.HomeService_MVC.model.enumoration.OrderStatus;
import com.example.HomeService_MVC.service.impel.OfferServiceImpel;
import com.example.HomeService_MVC.service.impel.OrderServiceImpel;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpel orderServiceImpel;
    private final OfferServiceImpel offerServiceImpel;
    private final DozerBeanMapper mapper;

    public OrderController(OrderServiceImpel orderServiceImpel, OfferServiceImpel offerServiceImpel, DozerBeanMapper mapper) {
        this.orderServiceImpel = orderServiceImpel;
        this.offerServiceImpel = offerServiceImpel;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/placeAnOrder/{subServicesId}")
    public String placeAnOrder(@Valid @RequestBody OrderDTO orderDTO, @PathVariable("subServicesId") Integer subServicesId){
        orderServiceImpel.PlaceAnOrder(subServicesId,orderDTO);
        return "OK";
    }

    @GetMapping("/viewOrder")
    public ResponseEntity<List<OrderDTO>> viewOrder(){
        List<Order> orderList = orderServiceImpel.findAllByCustomerId(1);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("You dont have any order until now!");
        List<OrderDTO> dtoList = orderToOrderDTO(orderList);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/viewStartedOrder")
    public ResponseEntity<List<OrderDTO>> viewStartedOrder(){
        List<Order> orderList = orderServiceImpel.findAllStartOrder(1);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("You dont have any order until now!");
        List<OrderDTO> dtoList = orderToOrderDTO(orderList);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/viewStartedOrderByCity")
    public ResponseEntity<List<OrderDTO>> viewStartedOrderByCity(){
        Expert expert = (Expert) SecurityUtil.getCurrentUser();
        List<Order> orderList = orderServiceImpel.findAllStartedOrderByCity(expert.getCity(),expert.getSubServices());
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("هیچ سفارش قابل نمایشی برای شما وجود ندارد!");
        List<OrderDTO> dtoList = orderToOrderDTO(orderList);
            return ResponseEntity.ok(dtoList);
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
            throw new OrderNotFoundException("You dont have any order until now!");
        List<OrderDTO> dtoList = orderToOrderDTO(orderList);
        return ResponseEntity.ok(dtoList);
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
            throw new OrderNotFoundException("You dont have any order until now!");
        List<OrderDTO> dtoList = orderToOrderDTO(orderList);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/PaidOrder/{orderId}")
    public ResponseEntity<String> paidOrder(@PathVariable("orderId") Integer orderId){
        Offer offer = offerServiceImpel.findByOrderIdAndAcceptedTrue(orderId);
        orderServiceImpel.paidOrder(1,orderId,offer);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/viewPaidOrder")
    public ResponseEntity<List<OrderDTO>> viewPaidOrder(){
        List<Order> orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(1, OrderStatus.PAID);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("You dont have any order until now!");
        List<OrderDTO> dtoList = orderToOrderDTO(orderList);
        return ResponseEntity.ok(dtoList);
    }

    public List<OrderDTO> orderToOrderDTO(List<Order> orderList){
        List<OrderDTO> dtoList = new ArrayList<>();
        for (Order o:orderList
        ) {
            dtoList.add(mapper.map(o,OrderDTO.class));
        }
        return dtoList;
    }

    @PostMapping("/showHistory")
    public ResponseEntity<List<OrderDTO>> showHistory(@RequestBody String status) {
        List<Order> orderList = new ArrayList<>();
        switch (status){
            case "suggestion" :
                orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(2,OrderStatus.EXPERT_SUGGESTION);
                break;

            case "selection" :
                orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(2,OrderStatus.EXPERT_SELECTION);
                break;

            case "waiting" :
                orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(2,OrderStatus.WAITING_FOR_EXPERT);
                break;

            case "started" :
                orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(2,OrderStatus.STARTED);
                break;

            case "done" :
                orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(2,OrderStatus.DONE);
                break;

            case "paid" :
                orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(2,OrderStatus.PAID);
                break;
        }
        if(orderList == null )
            return null;
        else {
            List<OrderDTO> dtoList = orderToOrderDTO(orderList);
            return ResponseEntity.ok(dtoList);
        }
    }
}
