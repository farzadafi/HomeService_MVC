package com.example.HomeService_MVC.service.interfaces;

import com.example.HomeService_MVC.dto.services.SubServicesDTO;
import com.example.HomeService_MVC.model.Services;
import com.example.HomeService_MVC.model.SubServices;

public interface SubServicesService {
    void save(SubServicesDTO subServicesDTO, Services services);
    SubServices findBySubServicesName(String subServicesName);
}
