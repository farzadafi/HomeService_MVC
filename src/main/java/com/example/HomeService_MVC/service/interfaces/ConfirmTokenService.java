package com.example.HomeService_MVC.service.interfaces;

import com.example.HomeService_MVC.model.ConfirmationToken;
import org.springframework.mail.SimpleMailMessage;


public interface ConfirmTokenService {
    void sendEmail(SimpleMailMessage email);
    void save(ConfirmationToken confirmationToken);
    ConfirmationToken findByConfirmToken(String confirmationToken);
    void deleteToken(ConfirmationToken confirmationToken);
    ConfirmationToken findConfirmationTokensByUserId(Integer userId);
}
