package com.example.HomeService_MVC.repository;

import com.example.HomeService_MVC.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
