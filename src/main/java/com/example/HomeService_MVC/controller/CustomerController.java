package com.example.HomeService_MVC.controller;


import com.example.HomeService_MVC.dto.user.CustomerDTO;
import com.example.HomeService_MVC.dto.user.DynamicSearchDTO;
import com.example.HomeService_MVC.dto.user.PasswordDTO;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.model.base.User;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.HomeService_MVC.service.impel.CustomerServiceImpel;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerServiceImpel customerServiceImpel;
    private final DozerBeanMapper mapper;

    public CustomerController(CustomerServiceImpel customerServiceImpel, DozerBeanMapper mapper) {
        this.customerServiceImpel = customerServiceImpel;
        this.mapper = mapper;
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute @RequestBody CustomerDTO customerSave) {
        Customer customer = mapper.map(customerSave,Customer.class);
        customerServiceImpel.save(customer);
        return "OK";
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody PasswordDTO passwordDTO){
        customerServiceImpel.updatePassword(new Customer(),passwordDTO);
        return ResponseEntity.ok("OK");
    }

    //@PreAuthorize("hasAnyRole('EXPERT','ADMIN')")
    @GetMapping("/security")
    public String security(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return user.toString();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/gridSearch")
    public ResponseEntity<List<CustomerDTO>> gridSearch(@ModelAttribute @RequestBody DynamicSearchDTO dynamicSearch) {
        List<Customer> customerList = customerServiceImpel.filterCustomer(dynamicSearch);
        List<CustomerDTO> dtoList = new ArrayList<>();
        for (Customer s:customerList
        ) {
            dtoList.add(mapper.map(s,CustomerDTO.class));
        }
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/showBalance")
    public String showBalance(){
        Customer customer = customerServiceImpel.getById(3);
        return String.valueOf(customer.getBalance());
    }
}
