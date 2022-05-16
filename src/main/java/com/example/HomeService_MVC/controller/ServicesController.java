package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.dto.services.ServicesSave;
import com.example.HomeService_MVC.service.impel.ServicesServiceImpel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/service")
public class ServicesController {

    private final ServicesServiceImpel servicesServiceImpel;

    public ServicesController(ServicesServiceImpel servicesServiceImpel) {
        this.servicesServiceImpel = servicesServiceImpel;
    }

    @PostMapping("/save")
    public ResponseEntity<ServicesSave> save(@Valid @RequestBody ServicesSave servicesSave){
        servicesServiceImpel.save(servicesSave);
        return ResponseEntity.ok(servicesSave);
    }
}
