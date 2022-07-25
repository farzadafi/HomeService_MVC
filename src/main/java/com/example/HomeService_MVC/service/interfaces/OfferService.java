package com.example.HomeService_MVC.service.interfaces;

import com.example.HomeService_MVC.dto.offer.OfferDto;
import com.example.HomeService_MVC.model.Offer;
import com.example.HomeService_MVC.model.enumoration.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OfferService {
    void placeAnOffer(OfferDto offerDTO, Integer orderId);
    List<Offer> findAllByOrdersId(Integer orderId);
    void selectOffer(Integer orderId,Integer offerId);
    Offer getById(Integer id);
    List<Offer> findAllByExpertIdAndStatus(Integer expertId, OrderStatus orderStatus);
    Offer findByOrderIdAndAcceptedTrue(Integer orderId);
    Optional<Offer> findById(Integer id);
}
