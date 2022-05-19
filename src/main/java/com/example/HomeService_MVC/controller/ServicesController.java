package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.dto.services.ServicesDTO;
import com.example.HomeService_MVC.service.impel.ServicesServiceImpel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/services")
public class ServicesController {

    private final ServicesServiceImpel servicesServiceImpel;

    public ServicesController(ServicesServiceImpel servicesServiceImpel) {
        this.servicesServiceImpel = servicesServiceImpel;
    }

    @PostMapping("/save")
    public ResponseEntity<ServicesDTO> save(@Valid @RequestBody ServicesDTO servicesSave){
        servicesServiceImpel.save(servicesSave);
        return ResponseEntity.ok(servicesSave);
    }

    @GetMapping("/getAllServices")
    public ResponseEntity<List<ServicesDTO>> getAllServices(){
        List<ServicesDTO> servicesDTOList = servicesServiceImpel.findAll();
        return ResponseEntity.ok(servicesDTOList);
    }
}
