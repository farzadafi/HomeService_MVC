package com.example.HomeService_MVC.service.interfaces;

import com.example.HomeService_MVC.model.base.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}
