package com.example.HomeService_MVC.repository;

import com.example.HomeService_MVC.model.Offer;
import com.example.HomeService_MVC.model.enumoration.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer,Integer> {
    List<Offer> findAllByOrdersId(Integer orderId);

    @Query
    ("FROM Offer AS o WHERE o.expert.id = :expertId AND " +
        "o.orders.orderStatus = :ordersStatus AND " +
        "o.isAccepted = true " )
    List<Offer> findAllByExpertIdAndStatus(@Param("expertId") Integer expertId,
                                           @Param("ordersStatus") OrderStatus orderStatus);
}
