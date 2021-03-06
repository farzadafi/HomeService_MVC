package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.core.SecurityUtil;
import com.example.HomeService_MVC.dto.user.CustomerDto;
import com.example.HomeService_MVC.dto.user.DynamicSearchDto;
import com.example.HomeService_MVC.dto.user.passwordChangeRequest;
import com.example.HomeService_MVC.mapper.interfaces.CustomerMapper;
import com.example.HomeService_MVC.model.ConfirmationToken;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.service.impel.ConfirmTokenServiceImpel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.HomeService_MVC.service.impel.CustomerServiceImpel;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerServiceImpel customerServiceImpel;
    private final ConfirmTokenServiceImpel confirmTokenServiceImpel;

    @PostMapping("/save")
    public String save(@Valid @RequestBody CustomerDto customerDto) throws MessagingException {
        Customer customer = CustomerMapper.INSTANCE.dtoToModel(customerDto);
        customerServiceImpel.save(customer);
        ConfirmationToken confirmationToken = new ConfirmationToken(customer);
        confirmTokenServiceImpel.save(confirmationToken);
        confirmTokenServiceImpel.sendVerificationMessage(confirmationToken);
        return "OK";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody passwordChangeRequest passwordChangeRequest){
        customerServiceImpel.updatePassword(passwordChangeRequest);
        return ResponseEntity.ok("OK");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/gridSearch")
    public ResponseEntity<List<CustomerDto>> gridSearch(@ModelAttribute @RequestBody DynamicSearchDto dynamicSearch) {
        List<Customer> customerList = customerServiceImpel.filterCustomer(dynamicSearch);
        List<CustomerDto> customerDtoList = CustomerMapper.INSTANCE.modelListToDtoList(customerList);
        return ResponseEntity.ok(customerDtoList);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/showBalance")
    public String showBalance(){
        return String.valueOf(SecurityUtil.getCurrentUser().getBalance());
    }
}
