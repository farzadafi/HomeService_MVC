package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.controller.exception.ServicesNotFoundException;
import com.example.HomeService_MVC.dto.services.ServicesDTO;
import com.example.HomeService_MVC.model.Services;
import com.example.HomeService_MVC.service.impel.ServicesServiceImpel;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/services")
public class ServicesController {

    private final ServicesServiceImpel servicesServiceImpel;
    private final DozerBeanMapper mapper;

    public ServicesController(ServicesServiceImpel servicesServiceImpel, DozerBeanMapper mapper) {
        this.servicesServiceImpel = servicesServiceImpel;
        this.mapper = mapper;
    }

    @PostMapping("/save")
    public ResponseEntity<ServicesDTO> save(@Valid @ModelAttribute @RequestBody ServicesDTO servicesSave){
        servicesServiceImpel.save(servicesSave);
        return ResponseEntity.ok(servicesSave);
    }

    @GetMapping("/getAllServices")
    public ResponseEntity<List<ServicesDTO>> getAllServices(){
        List<Services> servicesList = servicesServiceImpel.findAll();
        if(servicesList.size() == 0)
            throw  new ServicesNotFoundException("not services defined until now");
        List<ServicesDTO> dtoList = new ArrayList<>();
        for (Services s:servicesList
        ) {
            dtoList.add(mapper.map(s,ServicesDTO.class));
        }
        return ResponseEntity.ok(dtoList);
    }
}
