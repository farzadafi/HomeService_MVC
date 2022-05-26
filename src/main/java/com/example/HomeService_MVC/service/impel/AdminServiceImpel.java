package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.dto.user.PasswordDTO;
import com.example.HomeService_MVC.model.Admin;
import com.example.HomeService_MVC.model.base.User;
import com.example.HomeService_MVC.repository.UserRepository;
import com.example.HomeService_MVC.service.interfaces.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpel implements AdminService {

    private final UserRepository userRepository;

    public AdminServiceImpel( UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void updateAdmin(PasswordDTO passwordDTO) {
        User user = userRepository.getById(1);
        user.setPassword(passwordDTO.getSinglePassword());
        userRepository.save(user);
    }
}
