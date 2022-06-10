package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.aspect.RequiresCaptcha;
import com.example.HomeService_MVC.controller.exception.OrderNotFoundException;
import com.example.HomeService_MVC.core.SecurityUtil;
import com.example.HomeService_MVC.dto.PaymentDto;
import com.example.HomeService_MVC.dto.order.OrderDTO;
import com.example.HomeService_MVC.model.Customer;
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

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/viewExpertSelectionOrder")
    public ResponseEntity<List<OrderDTO>> viewExpertSelectionOrder(){
        Customer customer = (Customer) SecurityUtil.getCurrentUser();
        List<Order> orderList = orderServiceImpel.findAllExpertSelectionOrder(customer.getId());
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("شما هیچ سفارشی در وضعیت انتخاب پیشنهاد ندارید!");
        List<OrderDTO> dtoList = orderToOrderDTO(orderList);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("hasRole('EXPERT')")
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
    public String startOrder(@PathVariable("offerId") Integer offerId){
        Offer offer = offerServiceImpel.getById(offerId);
        orderServiceImpel.updateStatusToStart(offer);
        return "OK";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/viewOrderForDone")
    public ResponseEntity<List<OrderDTO>> viewOrderForDone(){
        List<Order> orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(SecurityUtil.getCurrentUser().getId(), OrderStatus.STARTED);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("شما هیچ سفارشی در وضعیت شروع شده ندارید!");
        List<OrderDTO> dtoList = orderToOrderDTO(orderList);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/doneOrder/{orderId}")
    public ResponseEntity<String> doneOrder(@PathVariable("orderId") Integer orderId){
        Offer offer = offerServiceImpel.findByOrderIdAndAcceptedTrue(orderId);
        String result = orderServiceImpel.setDoneOrder(offer,orderId);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/viewOrderForPaid")
    public ResponseEntity<List<OrderDTO>> viewOrderForPaid(){
        List<Order> orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(SecurityUtil.getCurrentUser().getId(), OrderStatus.DONE);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("شما هیچ سفارشی در وضعیت قابل پرداخت ندارید!");
        List<OrderDTO> dtoList = orderToOrderDTO(orderList);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/PaidOrder/{orderId}")
    public String paidOrder(@PathVariable("orderId") Integer orderId){
        Offer offer = offerServiceImpel.findByOrderIdAndAcceptedTrue(orderId);
        orderServiceImpel.paidOrder(offer);
        return "OK";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @RequiresCaptcha
    @PostMapping(value = "/onlinePayment/{orderId}")
    public String onlinePayment(@Valid @ModelAttribute @RequestBody PaymentDto paymentDto,@PathVariable("orderId") Integer orderId){
        Offer offer = offerServiceImpel.findByOrderIdAndAcceptedTrue(orderId);
        orderServiceImpel.paidOnlineOrder(offer);
        return "OK";
    }

    @GetMapping("/viewPaidOrder")
    public ResponseEntity<List<OrderDTO>> viewPaidOrder(){
        List<Order> orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(SecurityUtil.getCurrentUser().getId(), OrderStatus.PAID);
        if(orderList == null || orderList.size() == 0)
            throw new OrderNotFoundException("شما هیچ سفارشی در حالت پرداخت شده ندارید!");
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

    @GetMapping("/showHistory/{status}")
    public ResponseEntity<List<OrderDTO>> showHistory(@PathVariable("status") String status) {
        List<Order> orderList = new ArrayList<>();
        Integer customerId = SecurityUtil.getCurrentUser().getId();
        switch (status){
            case "suggestion" :
                orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(customerId,OrderStatus.EXPERT_SUGGESTION);
                break;

            case "selection" :
                orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(customerId,OrderStatus.EXPERT_SELECTION);
                break;

            case "waiting" :
                orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(customerId,OrderStatus.WAITING_FOR_EXPERT);
                break;

            case "started" :
                orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(customerId,OrderStatus.STARTED);
                break;

            case "done" :
                orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(customerId,OrderStatus.DONE);
                break;

            case "paid" :
                orderList = orderServiceImpel.findAllByCustomerIdAndOrderStatus(customerId,OrderStatus.PAID);
                break;
        }
        if(orderList == null || orderList.isEmpty() )
            throw new OrderNotFoundException("هیچ سفارشی در وضعیت انتخابی شما وجود ندارد");
        else {
            List<OrderDTO> dtoList = orderToOrderDTO(orderList);
            return ResponseEntity.ok(dtoList);
        }
    }

    @PreAuthorize("hasRole('EXPERT')")
    @GetMapping("/viewOrderById/{orderId}")
    public ResponseEntity<OrderDTO> viewOrderById(@PathVariable("orderId") Integer orderId){
        Order order = orderServiceImpel.getById(orderId);
        if(order == null)
            throw new OrderNotFoundException("متأسفانه این سفارش پیدا نشد!");
        OrderDTO orderDTO = mapper.map(order,OrderDTO.class);
        return ResponseEntity.ok(orderDTO);
    }
}
