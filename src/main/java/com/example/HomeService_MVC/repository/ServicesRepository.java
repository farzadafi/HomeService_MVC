package com.example.HomeService_MVC.repository;

import com.example.HomeService_MVC.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepository extends JpaRepository<Services,Integer> {
    Services findByServices(String servicesName);
}
