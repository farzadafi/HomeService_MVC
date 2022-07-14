package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.model.ConfirmationToken;
import com.example.HomeService_MVC.repository.ConfirmationTokenRepository;
import com.example.HomeService_MVC.service.interfaces.ConfirmTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class ConfirmTokenServiceImpel implements ConfirmTokenService {

    private final JavaMailSender javaMailSender;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final JavaMailSender mailSender;

    public ConfirmTokenServiceImpel(JavaMailSender javaMailSender, ConfirmationTokenRepository confirmationTokenRepository, JavaMailSender mailSender) {
        this.javaMailSender = javaMailSender;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.mailSender = mailSender;
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
    public ConfirmationToken findByConfirmToken(String confirmationToken) {
        return confirmationTokenRepository.findByConfirmToken(confirmationToken);
    }

    @Override
    public void deleteToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.delete(confirmationToken);
    }

    @Override
    public ConfirmationToken findConfirmationTokensByUserId(Integer userId) {
        return confirmationTokenRepository.findConfirmationTokensByUserId(userId);
    }

    @Override
    public void sendVerificationMessage(ConfirmationToken confirmationToken) {
        String text = ("please click on link for confirm your Account " +
                "http://localhost:8080/token/confirmAccount/"+confirmationToken.getConfirmToken());
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper message;
        try {
            message = new MimeMessageHelper(msg, true);
            message.setTo(confirmationToken.getUser().getEmail());
            message.setSubject("تایید ایمیل");
            message.setText(text, true);
        } catch (MessagingException e) {
            log.warn(e.getMessage());
        }
        mailSender.send(msg);
    }
}
