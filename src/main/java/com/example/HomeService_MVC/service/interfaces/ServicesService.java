package com.example.HomeService_MVC.service.interfaces;

import com.example.HomeService_MVC.dto.services.ServicesDto;
import com.example.HomeService_MVC.model.Services;

import java.util.List;
import java.util.Optional;

public interface ServicesService {
    void save(ServicesDto servicesSave);
    Services findByServicesName(String servicesName);
    List<Services> findAll();
    Optional<Services> findById(Integer id);
}
