package com.example.HomeService_MVC.repository;

import com.example.HomeService_MVC.model.SubServices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubServicesRepository extends JpaRepository<SubServices,Integer> {
    SubServices findBySubServicesName(String subServicesName);
}
