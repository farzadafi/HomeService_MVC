package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.model.ConfirmationToken;
import com.example.HomeService_MVC.repository.ConfirmationTokenRepository;
import com.example.HomeService_MVC.service.interfaces.ConfirmTokenService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmTokenServiceImpel implements ConfirmTokenService {

    private final JavaMailSender javaMailSender;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmTokenServiceImpel(JavaMailSender javaMailSender, ConfirmationTokenRepository confirmationTokenRepository) {
        this.javaMailSender = javaMailSender;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    @Override
    public void save(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public Optional<ConfirmationToken> findByConfirmToken(String confirmationToken) {
        return confirmationTokenRepository.findByConfirmToken(confirmationToken);
    }

    @Override
    public void deleteToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.delete(confirmationToken);
    }


}
