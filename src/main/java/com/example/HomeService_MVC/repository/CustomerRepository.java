package com.example.HomeService_MVC.repository;

import com.example.HomeService_MVC.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
