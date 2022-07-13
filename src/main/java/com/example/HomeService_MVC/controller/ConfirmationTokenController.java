package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.model.ConfirmationToken;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.service.impel.ConfirmTokenServiceImpel;
import com.example.HomeService_MVC.service.impel.CustomerServiceImpel;
import com.example.HomeService_MVC.service.impel.UserServiceImpel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/token")
@AllArgsConstructor
public class ConfirmationTokenController {

    private final ConfirmTokenServiceImpel confirmTokenServiceImpel;
    private final UserServiceImpel userServiceImpel;
    private final CustomerServiceImpel customerServiceImpel;

    public String confirmUserAccount(@PathVariable("token") String confirmationToken ) {
        ConfirmationToken token = confirmTokenServiceImpel.findByConfirmToken(confirmationToken);
        if(token == null )
            return "شما قبلا ایمیل خود را تایید کرده اید!";
        Customer customer = (Customer) userServiceImpel.findByEmail(token.getUser().getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("متاسفانه ایمیل شما پیدا نشد"));
        if(customer.isEnabled())
            return "شما قبلا ایمیل خود را تایید کرده اید!";
        customer.setEnabled(true);
        customerServiceImpel.updateEnable(customer);
        confirmTokenServiceImpel.deleteToken(token);
        return "ایمیل شما با موفقیت تایید شد!";
    }
}
