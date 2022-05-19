package com.example.HomeService_MVC.controller;


import com.example.HomeService_MVC.dto.offer.OfferDTO;
import com.example.HomeService_MVC.model.enumoration.OrderStatus;
import com.example.HomeService_MVC.service.impel.OfferServiceImpel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/offer")
public class OfferController {

    private final OfferServiceImpel offerServiceImpel;

    public OfferController(OfferServiceImpel offerServiceImpel) {
        this.offerServiceImpel = offerServiceImpel;
    }

    @PostMapping("/placeAnOffer/{orderId}")
    public ResponseEntity<String> placeAnOffer(@Valid @RequestBody OfferDTO offerDTO, @PathVariable("orderId") Integer orderId){
        offerServiceImpel.placeAnOffer(1,offerDTO,orderId);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/viewOffer/{orderId}")
    public ResponseEntity<List<OfferDTO>> viewOffer(@PathVariable("orderId") Integer orderId){
        List<OfferDTO> offerDTOList = offerServiceImpel.findAllByOrdersId(orderId);
        return ResponseEntity.ok(offerDTOList);
    }

    @GetMapping("/selectOffer/{orderId}/{offerId}")
    public ResponseEntity<String> selectOffer(@PathVariable("orderId") Integer orderId,@PathVariable("offerId") Integer offerID){
        offerServiceImpel.selectOffer(orderId,offerID);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/viewAcceptedOffer")
    public ResponseEntity<List<OfferDTO>> viewAcceptedOffer(){
        List<OfferDTO> offerDTOList = offerServiceImpel.findAllByExpertIdAndStatus(1, OrderStatus.WAITING_FOR_EXPERT);
        return ResponseEntity.ok(offerDTOList);
    }
}
