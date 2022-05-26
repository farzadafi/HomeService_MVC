package com.example.HomeService_MVC.repository;

import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.model.Expert;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer>, JpaSpecificationExecutor<Customer> {
    @NonNull
    List<Customer> findAll(Specification<Customer> specification);
}
