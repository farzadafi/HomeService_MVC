package com.example.HomeService_MVC.service.interfaces;

import com.example.HomeService_MVC.dto.services.SubServicesDto;
import com.example.HomeService_MVC.model.Services;
import com.example.HomeService_MVC.model.SubServices;

import java.util.List;
import java.util.Optional;

public interface SubServicesService {
    void save(SubServicesDto subServicesDTO, Services services);
    SubServices findBySubServicesName(String subServicesName);
    List<SubServices> findAllByServicesId(Integer servicesId);
    Optional<SubServices> findById(Integer id);
}
