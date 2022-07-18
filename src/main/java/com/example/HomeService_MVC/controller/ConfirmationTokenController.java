package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.model.ConfirmationToken;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.base.User;
import com.example.HomeService_MVC.model.enumoration.Role;
import com.example.HomeService_MVC.service.impel.ConfirmTokenServiceImpel;
import com.example.HomeService_MVC.service.impel.CustomerServiceImpel;
import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
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
    private final ExpertServiceImpel expertServiceImpel;

    @GetMapping("/confirm/{token}")
    public String confirmUserAccount(@PathVariable("token") String confirmationToken ) {
        ConfirmationToken token = confirmTokenServiceImpel.findByConfirmToken(confirmationToken);
        if(token == null )
            return "شما قبلا ایمیل خود را تایید کرده اید!";
        User user = userServiceImpel.findByEmail(token.getUser().getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("متاسفانه ایمیل شما پیدا نشد"));
        if(user.isEnabled())
            return "شما قبلا ایمیل خود را تایید کرده اید!";
        user.setEnabled(true);
        if(user.getRole().equals(Role.ROLE_CUSTOMER))
            customerServiceImpel.updateEnable((Customer) user);
        else
            expertServiceImpel.update((Expert) user);
        confirmTokenServiceImpel.deleteToken(token);
        return "ایمیل شما با موفقیت تایید شد!";
    }
}
