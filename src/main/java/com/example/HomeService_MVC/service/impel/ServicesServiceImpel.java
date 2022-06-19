package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.dto.services.ServicesDto;
import com.example.HomeService_MVC.model.Services;
import com.example.HomeService_MVC.repository.ServicesRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import com.example.HomeService_MVC.service.interfaces.ServicesService;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesServiceImpel implements ServicesService {

    private final ServicesRepository servicesRepository;
    private final DozerBeanMapper mapper;

    public ServicesServiceImpel(ServicesRepository servicesRepository, DozerBeanMapper mapper) {
        this.servicesRepository = servicesRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(ServicesDto servicesSave) {
        Services services = mapper.map(servicesSave,Services.class);
        servicesRepository.save(services);
    }

    @Override
    public Services findByServicesName(String servicesName) {
        return servicesRepository.findByServices(servicesName);
    }

    @Override
    public List<Services> findAll() {
        return servicesRepository.findAll();
    }

    @Override
    public Optional<Services> findById(Integer id) {
        return servicesRepository.findById(id);
    }
}
