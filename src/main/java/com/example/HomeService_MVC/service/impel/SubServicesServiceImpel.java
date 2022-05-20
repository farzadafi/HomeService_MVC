package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.controller.exception.SubServicesNotFoundException;
import com.example.HomeService_MVC.dto.services.SubServicesDTO;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.Services;
import com.example.HomeService_MVC.model.SubServices;
import com.example.HomeService_MVC.repository.SubServicesRepository;
import com.example.HomeService_MVC.service.interfaces.SubServicesService;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public List<SubServicesDTO> findAllByServicesId(Integer servicesId) {
        List<SubServices> subServicesList = subServicesRepository.findAllByServicesId(servicesId);
        if(subServicesList == null || subServicesList.size() == 0)
            throw  new SubServicesNotFoundException("not subServices defined until now");
        List<SubServicesDTO> dtoList = new ArrayList<>();
        for (SubServices s:subServicesList
        ) {
            dtoList.add(mapper.map(s,SubServicesDTO.class));
        }
        return dtoList;
    }

    @Override
    public Optional<SubServices> findById(Integer id) {
        return subServicesRepository.findById(id);
    }
}
