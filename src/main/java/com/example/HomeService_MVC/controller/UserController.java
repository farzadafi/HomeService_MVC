package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.dto.user.LoginDTO;
import com.example.HomeService_MVC.service.impel.UserServiceImpel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpel userServiceImpel;

    public UserController(UserServiceImpel userServiceImpel) {
        this.userServiceImpel = userServiceImpel;
    }

    @PostMapping("/findByEmail")
    public String findByEmail(@RequestBody String email){
        if(userServiceImpel.findByEmail(email).isEmpty())
            return "OK";
        else
            return " ";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @RequestBody LoginDTO loginDTO){
        System.out.println("farzad");
        return userServiceImpel.login(loginDTO.getEmail(),loginDTO.getPassword());
    }
}
