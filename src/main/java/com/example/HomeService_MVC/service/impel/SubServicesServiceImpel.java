package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.dto.services.SubServicesDTO;
import com.example.HomeService_MVC.model.Services;
import com.example.HomeService_MVC.model.SubServices;
import com.example.HomeService_MVC.repository.SubServicesRepository;
import com.example.HomeService_MVC.service.interfaces.SubServicesService;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubServicesServiceImpel implements SubServicesService {

    private final SubServicesRepository subServicesRepository;
    private final DozerBeanMapper mapper;

    public SubServicesServiceImpel(SubServicesRepository subServicesRepository, DozerBeanMapper mapper) {
        this.subServicesRepository = subServicesRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(SubServicesDTO subServicesDTO, Services services) {
        SubServices subServices = mapper.map(subServicesDTO,SubServices.class);
        subServices.setServices(services);
        subServicesRepository.save(subServices);
    }

    @Override
    public SubServices findBySubServicesName(String subServicesName) {
        return subServicesRepository.findBySubServicesName(subServicesName);
    }

    @Override
    public List<SubServices> findAllByServicesId(Integer servicesId) {
        return subServicesRepository.findAllByServicesId(servicesId);
    }

    @Override
    public Optional<SubServices> findById(Integer id) {
        return subServicesRepository.findById(id);
    }
}
