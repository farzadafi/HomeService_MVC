package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.repository.UserRepository;
import com.example.HomeService_MVC.service.interfaces.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpel implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
