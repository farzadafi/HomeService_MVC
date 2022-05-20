package com.example.HomeService_MVC.service.interfaces;

import com.example.HomeService_MVC.dto.services.SubServicesDTO;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.Services;
import com.example.HomeService_MVC.model.SubServices;

import java.util.List;
import java.util.Optional;

public interface SubServicesService {
    void save(SubServicesDTO subServicesDTO, Services services);
    SubServices findBySubServicesName(String subServicesName);
    List<SubServices> findAllByServicesId(Integer servicesId);
    Optional<SubServices> findById(Integer id);
}
