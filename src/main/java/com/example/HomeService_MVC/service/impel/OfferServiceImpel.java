package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.controller.exception.InvalidProposedPriceException;
import com.example.HomeService_MVC.dto.offer.OfferDTO;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.Offer;
import com.example.HomeService_MVC.model.Order;
import com.example.HomeService_MVC.repository.OfferRepository;
import com.example.HomeService_MVC.service.interfaces.OfferService;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceImpel implements OfferService {

    private final ExpertServiceImpel expertServiceImpel;
    private final OrderServiceImpel orderServiceImpel;
    private final OfferRepository offerRepository;

    public OfferServiceImpel(ExpertServiceImpel expertServiceImpel, OrderServiceImpel orderServiceImpel, OfferRepository offerRepository) {
        this.expertServiceImpel = expertServiceImpel;
        this.orderServiceImpel = orderServiceImpel;
        this.offerRepository = offerRepository;
    }


    @Override
    public void placeAnOffer(Integer expertId, OfferDTO offerDTO, Integer orderId) {
        Order order = orderServiceImpel.getById(orderId);
        if(order.getProposedPrice() > offerDTO.getProposedPrice())
            throw new InvalidProposedPriceException("You have to enter a price more than " + order.getProposedPrice());
        Expert expert = expertServiceImpel.getById(expertId);
        Offer offer = new Offer(offerDTO.getProposedPrice(),offerDTO.getDurationWork(),offerDTO.getStartTime(),order,expert);
        offer.setExpert(expert);
        offer.setOrders(order);
        offerRepository.save(offer);
    }
}
