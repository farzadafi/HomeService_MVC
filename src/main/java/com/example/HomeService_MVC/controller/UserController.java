package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.service.impel.UserServiceImpel;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
}
