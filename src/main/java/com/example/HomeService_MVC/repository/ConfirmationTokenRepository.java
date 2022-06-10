package com.example.HomeService_MVC.repository;

import com.example.HomeService_MVC.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Integer> {
    ConfirmationToken findByConfirmToken(String confirmationToken);
}
