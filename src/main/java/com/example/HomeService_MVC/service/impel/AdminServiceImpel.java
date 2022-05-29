package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.core.SecurityUtil;
import com.example.HomeService_MVC.dto.user.PasswordDTO;
import com.example.HomeService_MVC.model.base.User;
import com.example.HomeService_MVC.repository.UserRepository;
import com.example.HomeService_MVC.service.interfaces.AdminService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpel implements AdminService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminServiceImpel(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public void updateAdmin(PasswordDTO passwordDTO) {
        User user = SecurityUtil.getCurrentUser();
        user.setPassword(bCryptPasswordEncoder.encode(passwordDTO.getSinglePassword()));
        userRepository.save(user);
    }
}
