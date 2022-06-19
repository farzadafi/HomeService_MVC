package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.controller.exception.ServicesNotFoundException;
import com.example.HomeService_MVC.dto.services.ServicesDto;
import com.example.HomeService_MVC.model.Services;
import com.example.HomeService_MVC.service.impel.ServicesServiceImpel;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/services")
public class ServicesController {

    private final ServicesServiceImpel servicesServiceImpel;
    private final DozerBeanMapper mapper;

    public ServicesController(ServicesServiceImpel servicesServiceImpel, DozerBeanMapper mapper) {
        this.servicesServiceImpel = servicesServiceImpel;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute @RequestBody ServicesDto servicesDTO){
        servicesServiceImpel.save(servicesDTO);
        return "OK";
    }

    @GetMapping("/getAllServices")
    public ResponseEntity<List<ServicesDto>> getAllServices(){
        List<Services> servicesList = servicesServiceImpel.findAll();
        if(servicesList.size() == 0)
            throw  new ServicesNotFoundException("not services defined until now");
        List<ServicesDto> dtoList = new ArrayList<>();
        for (Services s:servicesList
        ) {
            dtoList.add(mapper.map(s, ServicesDto.class));
        }
        return ResponseEntity.ok(dtoList);
    }
}
