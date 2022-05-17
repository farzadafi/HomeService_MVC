package com.example.HomeService_MVC.controller;


import com.example.HomeService_MVC.dto.offer.OfferDTO;
import com.example.HomeService_MVC.service.impel.OfferServiceImpel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
