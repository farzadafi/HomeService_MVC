package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.controller.exception.ServicesNotFoundException;
import com.example.HomeService_MVC.controller.exception.SubServicesNotFoundException;
import com.example.HomeService_MVC.dto.services.SubServicesDTO;
import com.example.HomeService_MVC.model.Services;
import com.example.HomeService_MVC.model.SubServices;
import com.example.HomeService_MVC.service.impel.ServicesServiceImpel;
import com.example.HomeService_MVC.service.impel.SubServicesServiceImpel;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/subServices")
public class SubServicesController {

    private final SubServicesServiceImpel subServicesServiceImpel;
    private final ServicesServiceImpel servicesServiceImpel;
    private final DozerBeanMapper mapper;

    public SubServicesController(SubServicesServiceImpel subServicesServiceImpel, ServicesServiceImpel servicesServiceImpel, DozerBeanMapper mapper) {
        this.subServicesServiceImpel = subServicesServiceImpel;
        this.servicesServiceImpel = servicesServiceImpel;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute @RequestBody SubServicesDTO subServicesDTO){
        Services services = servicesServiceImpel.findById(subServicesDTO.getId()).orElseThrow(() -> new ServicesNotFoundException("This services not found!"));
        subServicesServiceImpel.save(subServicesDTO,services);
        return "OK";
    }

    @GetMapping("/getAllSubServices/{servicesId}")
    public ResponseEntity<List<SubServicesDTO>> getAllSubServices(@PathVariable("servicesId") Integer servicesId){
        List<SubServices> subServicesList = subServicesServiceImpel.findAllByServicesId(servicesId);
        if(subServicesList == null || subServicesList.size() == 0)
            throw  new SubServicesNotFoundException("not subServices defined until now");
        List<SubServicesDTO> dtoList = new ArrayList<>();
        for (SubServices s:subServicesList
        ) {
            dtoList.add(mapper.map(s,SubServicesDTO.class));
        }
        return ResponseEntity.ok(dtoList);
    }
}
