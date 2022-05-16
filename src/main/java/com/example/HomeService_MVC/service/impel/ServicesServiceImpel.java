package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.dto.services.ServicesSave;
import com.example.HomeService_MVC.model.Services;
import com.example.HomeService_MVC.repository.ServicesRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import com.example.HomeService_MVC.service.interfaces.ServicesService;

@Service
public class ServicesServiceImpel implements ServicesService {

    private final ServicesRepository servicesRepository;
    private final DozerBeanMapper mapper;

    public ServicesServiceImpel(ServicesRepository servicesRepository, DozerBeanMapper mapper) {
        this.servicesRepository = servicesRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(ServicesSave servicesSave) {
        Services services = mapper.map(servicesSave,Services.class);
        System.out.println(services.getServices());
        servicesRepository.save(services);
    }

    @Override
    public Services findByServicesName(String servicesName) {
        return servicesRepository.findByServices(servicesName);
    }
}
