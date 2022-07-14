package com.example.HomeService_MVC.service.interfaces;

import com.example.HomeService_MVC.dto.user.passwordChangeRequest;
import com.example.HomeService_MVC.model.Expert;

import java.util.List;
import java.util.Optional;

public interface ExpertService {
    void save(Expert expert);
    List<Expert> findAllByAcceptedFalse();
    Optional<Expert> findById(Integer id);
    Expert getById(Integer id);
    void ExpertAccept(Integer id);
    void addExpertToSubService(String expertEmail,Integer subServicesId);
    Optional<Expert> findByEmail(String expertEmail);
    void removeExpertSubServices(String email,Integer subServicesId);
    void updatePassword(Integer expertId, passwordChangeRequest passwordDTO);
    void updateBalance(Expert expert);
    void updateStars(Expert expert);
    void updateEnable(Expert expert);
}
