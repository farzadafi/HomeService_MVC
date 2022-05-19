package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.controller.exception.ServicesNotFoundException;
import com.example.HomeService_MVC.dto.services.SubServicesDTO;
import com.example.HomeService_MVC.model.Services;
import com.example.HomeService_MVC.service.impel.ServicesServiceImpel;
import com.example.HomeService_MVC.service.impel.SubServicesServiceImpel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/subServices")
public class SubServicesController {

    private final SubServicesServiceImpel subServicesServiceImpel;
    private final ServicesServiceImpel servicesServiceImpel;

    public SubServicesController(SubServicesServiceImpel subServicesServiceImpel, ServicesServiceImpel servicesServiceImpel) {
        this.subServicesServiceImpel = subServicesServiceImpel;
        this.servicesServiceImpel = servicesServiceImpel;
    }

    @PostMapping("/save")
    public ResponseEntity<SubServicesDTO> saveSubServices(@Valid @RequestBody SubServicesDTO subServicesDTO){
        Services services = servicesServiceImpel.findById(subServicesDTO.getId()).orElseThrow(() -> new ServicesNotFoundException("This services not found!"));
        subServicesServiceImpel.save(subServicesDTO,services);
        return ResponseEntity.ok(subServicesDTO);
    }

    @GetMapping("/getAllSubServices/{servicesId}")
    public ResponseEntity<List<SubServicesDTO>> getAllSubServices(@PathVariable("servicesId") Integer servicesId){
        List<SubServicesDTO> subServicesDTOList = subServicesServiceImpel.findAllByServicesId(servicesId);
        return ResponseEntity.ok(subServicesDTOList);
    }
}
