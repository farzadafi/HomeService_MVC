package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.controller.exception.ExpertNotFoundException;
import com.example.HomeService_MVC.controller.exception.SubServicesNotFoundException;
import com.example.HomeService_MVC.dto.user.ExpertSave;
import com.example.HomeService_MVC.dto.user.ExpertViewDTO;
import com.example.HomeService_MVC.dto.user.PasswordDTO;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.SubServices;
import com.example.HomeService_MVC.model.enumoration.Role;
import com.example.HomeService_MVC.repository.ExpertRepository;
import com.example.HomeService_MVC.service.interfaces.ExpertService;
import org.dozer.DozerBeanMapper;
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
        //todo ok this constructor
        Expert expert = new Expert();
        expert.setFirstName(expertSave.getFirstName());
        expert.setLastName(expertSave.getLastName());
        expert.setEmail(expertSave.getEmail());
        expert.setPassword(expertSave.getPassword()[0]);
        expert.setConfPassword(expertSave.getConfPassword());
        expert.setBalance(50000L);
        expert.setRole(Role.EXPERT);
        expert.setCity(expertSave.getCity());
        expert.setStars(0);
        try {
            expert.setImage(expertSave.getImage().getBytes());
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
    public Expert getById(Integer id) {
        return expertRepository.getById(id);
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
        SubServices subServices = subServicesServiceImpel.findById(subServicesId).orElseThrow(() -> new SubServicesNotFoundException("This subServices is not found!"));
        Set<SubServices> subServicesSet = expert.getSubServices();
        subServicesSet.add(subServices);
        expert.setSubServices(subServicesSet);
        expertRepository.save(expert);
    }

    @Override
    public Optional<Expert> findByEmail(String expertEmail) {
        return expertRepository.findExpertByEmail(expertEmail);
    }

    @Override
    public void removeExpertSubServices(String expertEmail, Integer subServicesId) {
        Expert expert = findByEmail(expertEmail).orElseThrow(() -> new ExpertNotFoundException("This Expert is not found!"));
        Set<SubServices> subServicesSet = expert.getSubServices();
        SubServices subServices = subServicesServiceImpel.findById(subServicesId).orElseThrow(() -> new SubServicesNotFoundException("This subServices is not found!"));
        subServicesSet.remove(subServices);
        expert.setSubServices(subServicesSet);
        expertRepository.save(expert);
    }

    @Override
    public void updatePassword(Integer expertId, PasswordDTO passwordDTO) {
        Expert expert = getById(expertId);
        expert.setPassword(passwordDTO.getSinglePassword());
        expertRepository.save(expert);
    }

    @Override
    public void updateBalance(Expert expert) {
        expertRepository.save(expert);
    }

    @Override
    public void updateStars(Expert expert) {
        expertRepository.save(expert);
    }

}
