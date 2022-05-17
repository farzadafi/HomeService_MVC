package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.controller.exception.ExpertNotFoundException;
import com.example.HomeService_MVC.dto.user.ExpertSave;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.SubServices;
import com.example.HomeService_MVC.model.enumoration.Role;
import com.example.HomeService_MVC.repository.ExpertRepository;
import com.example.HomeService_MVC.service.interfaces.ExpertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ExpertServiceImpel implements ExpertService {

    private final ExpertRepository expertRepository;
    private final SubServicesServiceImpel subServicesServiceImpel;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ExpertServiceImpel(ExpertRepository expertRepository, SubServicesServiceImpel subServicesServiceImpel) {
        this.expertRepository = expertRepository;
        this.subServicesServiceImpel = subServicesServiceImpel;
    }

    @Override
    public void save(ExpertSave expertSave) {
        Expert expert = new Expert();
        try {
            expert = new Expert(expertSave.getFirstName(),expertSave.getLastName(),
                    expertSave.getEmail(),expertSave.getPassword()[0],null,50000L, Role.EXPERT,
                    expertSave.getCity(),expertSave.getImage().getBytes(),0,false,null);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.warn(e.getMessage());
        }
        expertRepository.save(expert);
    }

    @Override
    public List<Expert> findAllByAcceptedFalse() {
        return expertRepository.findAllByAcceptedFalse();
    }

    @Override
    public Optional<Expert> findById(Integer id) {
        return expertRepository.findById(id);
    }

    @Override
    public void ExpertAccept(Integer id) {
        Expert expert = findById(id).orElseThrow(() -> new ExpertNotFoundException("This Expert is not found!"));
        expert.setAccepted(true);
        expertRepository.save(expert);
    }

    @Override
    public void addExpertToSubService(String expertEmail, Integer subServicesId) {
        Expert expert = findByEmail(expertEmail).orElseThrow(() -> new ExpertNotFoundException("This Expert is not found!"));
        SubServices subServices = subServicesServiceImpel.findById(subServicesId).orElseThrow(() -> new ExpertNotFoundException("This subServices is not found!"));
        Set<SubServices> subServicesSet = expert.getSubServices();
        subServicesSet.add(subServices);
        expert.setSubServices(subServicesSet);
        expertRepository.save(expert);
    }

    @Override
    public Optional<Expert> findByEmail(String expertEmail) {
        return expertRepository.findExpertByEmail(expertEmail);
    }
}
