package com.example.HomeService_MVC.repository;

import com.example.HomeService_MVC.model.Order;
import com.example.HomeService_MVC.model.SubServices;
import com.example.HomeService_MVC.model.enumoration.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllByCustomerId(Integer customerId);

    @Query
    ("FROM Order AS o WHERE o.customer.id = :customerId AND " +
                        "o.orderStatus = :orderStatusOne OR " +
                           "o.orderStatus = :orderStatusTwo ")
    List<Order> findAllByCustomerIdAndOrderStatus(@Param("customerId") Integer customerId,
                                                   @Param("orderStatusOne") OrderStatus orderStatusOne,
                                                   @Param("orderStatusTwo") OrderStatus orderStatusTwo);

    @Query
    ("FROM Order AS o WHERE o.city = :city AND " +
            "o.subService IN (:subServices) AND " +
            "o.orderStatus = :orderStatusOne OR " +
            "o.orderStatus = :orderStatusTwo ")
    List<Order> findAllStartedOrderByCity(@Param("city") String city,
                                            @Param("subServices") Set<SubServices> subServices,
                                            @Param("orderStatusOne") OrderStatus orderStatusOne,
                                            @Param("orderStatusTwo") OrderStatus orderStatusTwo);
}
