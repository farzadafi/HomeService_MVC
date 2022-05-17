package com.example.HomeService_MVC.service.interfaces;

import com.example.HomeService_MVC.dto.offer.OfferDTO;

public interface OfferService {
    void placeAnOffer(Integer expertId, OfferDTO offerDTO,Integer orderId);
}
