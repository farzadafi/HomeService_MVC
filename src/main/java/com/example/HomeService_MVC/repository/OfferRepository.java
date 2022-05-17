package com.example.HomeService_MVC.repository;

import com.example.HomeService_MVC.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer,Integer> {
    List<Offer> findAllByOrdersId(Integer orderId);
}
