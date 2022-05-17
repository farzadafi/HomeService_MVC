package com.example.HomeService_MVC.controller;


import com.example.HomeService_MVC.dto.user.CustomerSave;
import com.example.HomeService_MVC.dto.user.PasswordDTO;
import com.example.HomeService_MVC.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.HomeService_MVC.service.impel.CustomerServiceImpel;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerServiceImpel customerServiceImpel;

    public CustomerController(CustomerServiceImpel customerServiceImpel) {
        this.customerServiceImpel = customerServiceImpel;
    }

    @PostMapping("/save")
    public ResponseEntity<CustomerSave> save(@Valid @RequestBody CustomerSave customerSave) {
        customerServiceImpel.save(customerSave);
        return ResponseEntity.ok(customerSave);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody PasswordDTO passwordDTO){
        customerServiceImpel.updatePassword(new Customer(),passwordDTO);
        return ResponseEntity.ok("OK");
    }

}
