package com.example.HomeService_MVC.service.interfaces;

import com.example.HomeService_MVC.dto.services.ServicesSave;
import com.example.HomeService_MVC.model.Services;

public interface ServicesService {
    void save(ServicesSave servicesSave);
    Services findByServicesName(String servicesName);
}
