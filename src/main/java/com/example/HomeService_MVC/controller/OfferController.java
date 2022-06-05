package com.example.HomeService_MVC.controller;


import com.example.HomeService_MVC.controller.exception.OfferNotFoundException;
import com.example.HomeService_MVC.core.SecurityUtil;
import com.example.HomeService_MVC.dto.offer.OfferDTO;
import com.example.HomeService_MVC.model.Offer;
import com.example.HomeService_MVC.model.enumoration.OrderStatus;
import com.example.HomeService_MVC.service.impel.OfferServiceImpel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/offer")
public class OfferController {

    private final OfferServiceImpel offerServiceImpel;

    public OfferController(OfferServiceImpel offerServiceImpel) {
        this.offerServiceImpel = offerServiceImpel;
    }

    @PreAuthorize("hasRole('EXPERT')")
    @PostMapping("/placeAnOffer/{orderId}")
    public String placeAnOffer(@Valid @RequestBody OfferDTO offerDTO, @PathVariable("orderId") Integer orderId){
        offerServiceImpel.placeAnOffer(offerDTO,orderId);
        return "OK";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/viewOffer/{orderId}")
    public ResponseEntity<List<OfferDTO>> viewOffer(@PathVariable("orderId") Integer orderId){
        List<Offer> offerList = offerServiceImpel.findAllByOrdersId(orderId);
        if(offerList == null || offerList.size() == 0)
            throw new OfferNotFoundException("هیچ پیشنهادی تاکنون برای این سفارش ثبت نشده است!");
        List<OfferDTO> dtoList = offerToOfferDTO(offerList);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/selectOffer/{orderId}/{offerId}")
    public ResponseEntity<String> selectOffer(@PathVariable("orderId") Integer orderId,@PathVariable("offerId") Integer offerID){
        offerServiceImpel.selectOffer(orderId,offerID);
        return ResponseEntity.ok("OK");
    }

    @PreAuthorize("hasRole('EXPERT')")
    @GetMapping("/viewAcceptedOffer")
    public ResponseEntity<List<OfferDTO>> viewAcceptedOffer(){
        List<Offer> offerList = offerServiceImpel.findAllByExpertIdAndStatus(SecurityUtil.getCurrentUser().getId(), OrderStatus.WAITING_FOR_EXPERT);
        if(offerList == null || offerList.size() == 0)
            throw new OfferNotFoundException("شما هیچ پیشنهاد تأیید شده ای ندارید!");
        List<OfferDTO> dtoList = offerToOfferDTO(offerList);
        return ResponseEntity.ok(dtoList);
    }

    public List<OfferDTO> offerToOfferDTO(List<Offer> offerList){
        List<OfferDTO> dtoList = new ArrayList<>();
        for (Offer o:offerList
        ) {
            dtoList.add(new OfferDTO(o.getId(),o.getProposedPrice(),o.getDurationWork(),o.getStartTime(),o.getOrders().getId()));
        }
        return dtoList;
    }


    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/priceWithOrderId/{orderId}")
    public String priceWithOrderId(@PathVariable("orderId") Integer orderId){
        Offer offer = offerServiceImpel.findByOrderIdAndAcceptedTrue(orderId);
        return String.valueOf(offer.getProposedPrice());
    }
}
