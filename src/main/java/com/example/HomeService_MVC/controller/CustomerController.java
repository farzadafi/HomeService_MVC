package com.example.HomeService_MVC.controller;


import com.example.HomeService_MVC.controller.exception.ConfirmationTokenNotFoundException;
import com.example.HomeService_MVC.dto.user.CustomerDTO;
import com.example.HomeService_MVC.dto.user.DynamicSearchDTO;
import com.example.HomeService_MVC.dto.user.PasswordDTO;
import com.example.HomeService_MVC.model.ConfirmationToken;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.model.base.User;
import com.example.HomeService_MVC.service.impel.ConfirmTokenServiceImpel;
import com.example.HomeService_MVC.service.impel.UserServiceImpel;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.example.HomeService_MVC.service.impel.CustomerServiceImpel;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerServiceImpel customerServiceImpel;
    private final DozerBeanMapper mapper;
    private final ConfirmTokenServiceImpel confirmTokenServiceImpel;
    private final UserServiceImpel userServiceImpel;
    private final JavaMailSender mailSender;

    public CustomerController(CustomerServiceImpel customerServiceImpel, DozerBeanMapper mapper, ConfirmTokenServiceImpel confirmTokenServiceImpel, UserServiceImpel userServiceImpel, JavaMailSender mailSender) {
        this.customerServiceImpel = customerServiceImpel;
        this.mapper = mapper;
        this.confirmTokenServiceImpel = confirmTokenServiceImpel;
        this.userServiceImpel = userServiceImpel;
        this.mailSender = mailSender;
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute @RequestBody CustomerDTO customerSave) throws MessagingException {
        Customer customer = mapper.map(customerSave,Customer.class);
        customerServiceImpel.save(customer);
        ConfirmationToken confirmationToken = new ConfirmationToken(customer);
        confirmTokenServiceImpel.save(confirmationToken);
        String verifyCode = ("please click on link for confirm your email!"
                +"http://localhost:8080/customer/confirm_account?token="+confirmationToken.getConfirmToken());
        sendVerificationMessage(customer.getEmail(),verifyCode);
        return "OK";
    }

    @GetMapping(value="/confirm_account/{token}")
    public String confirmUserAccount(@PathVariable("token")String confirmationToken) {
        ConfirmationToken token = confirmTokenServiceImpel.findByConfirmToken(confirmationToken).orElseThrow(() -> new ConfirmationTokenNotFoundException("متاسفانه توکن مورد نظر وجود ندارد!"));
        Customer customer = (Customer) userServiceImpel.findByEmail(token.getUser().getEmail()).orElseThrow(() -> new UsernameNotFoundException("متاسفانه شما پیدا نشدید!"));
        if(customer.isEnabled())
            throw new ConfirmationTokenNotFoundException("شما قبلا ایمیل خود را تایید کرده اید!");
        customer.setEnabled(true);
        customerServiceImpel.save(customer);
        confirmTokenServiceImpel.deleteToken(token);
        return "OK";
    }

    public void sendVerificationMessage(
            String mail, String text) {
        MimeMessage msg = mailSender.createMimeMessage();

        MimeMessageHelper message;
        try {
            message = new MimeMessageHelper(msg, true);
            message.setTo(mail);
            message.setSubject("تایید ایمیل");
            message.setText(text, true);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
        mailSender.send(msg);
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
