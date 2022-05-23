package com.example.HomeService_MVC.controller;


import com.example.HomeService_MVC.dto.user.CustomerDTO;
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
    public String save(@Valid @ModelAttribute @RequestBody CustomerDTO customerSave) {
        customerServiceImpel.save(customerSave);
        return "OK";
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody PasswordDTO passwordDTO){
        customerServiceImpel.updatePassword(new Customer(),passwordDTO);
        return ResponseEntity.ok("OK");
    }

    /*
    @PreAuthorize("hasRole('EXPERT')")
    //@PreAuthorize("hasAnyRole('EXPERT','ADMIN')")
    //@PreAuthorize("hasAuthority('EXPERT')")
    @GetMapping("/hello")
    public String farzad(Authentication authentication){
        //return principal.getName();
        Expert user = (Expert) authentication.getPrincipal();
        return user.toString();
    }
     */

}
