package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.controller.exception.InvalidProposedPriceException;
import com.example.HomeService_MVC.core.SecurityUtil;
import com.example.HomeService_MVC.dto.offer.OfferDTO;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.Offer;
import com.example.HomeService_MVC.model.Order;
import com.example.HomeService_MVC.model.enumoration.OrderStatus;
import com.example.HomeService_MVC.repository.OfferRepository;
import com.example.HomeService_MVC.service.interfaces.OfferService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpel implements OfferService {

    private final OrderServiceImpel orderServiceImpel;
    private final OfferRepository offerRepository;

    public OfferServiceImpel( OrderServiceImpel orderServiceImpel, OfferRepository offerRepository) {
        this.orderServiceImpel = orderServiceImpel;
        this.offerRepository = offerRepository;
    }


    @Override
    public void placeAnOffer(OfferDTO offerDTO, Integer orderId) {
        Order order = orderServiceImpel.getById(orderId);
        if(order.getProposedPrice() > offerDTO.getProposedPrice())
            throw new InvalidProposedPriceException("شما باید یک قیمت بزرگ تر از " + order.getProposedPrice() + " وارد کنید");
        Expert expert = (Expert) SecurityUtil.getCurrentUser();
        Offer offer = new Offer(offerDTO.getProposedPrice(),offerDTO.getDurationWork(),offerDTO.getStartTime(),order,expert);
        order.setOrderStatus(OrderStatus.EXPERT_SELECTION);
        orderServiceImpel.update(order);
        offerRepository.save(offer);
    }

    @Override
    public List<Offer> findAllByOrdersId(Integer orderId) {
        return offerRepository.findAllByOrdersId(orderId, Sort.by(Sort.Direction.DESC,"proposedPrice"));
    }

    @Override
    public void selectOffer(Integer orderId, Integer offerId) {
        Order order = orderServiceImpel.getById(orderId);
        Offer offer = getById(offerId);
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT);
        offer.setAccepted(true);
        orderServiceImpel.update(order);
        offerRepository.save(offer);
    }

    @Override
    public Offer getById(Integer id) {
        return offerRepository.getById(id);
    }

    @Override
    public List<Offer> findAllByExpertIdAndStatus(Integer expertId, OrderStatus orderStatus) {
        return offerRepository.findAllByExpertIdAndStatus(expertId,orderStatus);
    }

    @Override
    public Offer findByOrderIdAndAcceptedTrue(Integer orderId) {
        return offerRepository.findByOrderIdAndAcceptedTrue(orderId);
    }

}
