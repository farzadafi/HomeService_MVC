package com.example.HomeService_MVC.controller;


import com.example.HomeService_MVC.dto.user.CustomerDto;
import com.example.HomeService_MVC.dto.user.DynamicSearchDto;
import com.example.HomeService_MVC.dto.user.PasswordDto;
import com.example.HomeService_MVC.model.ConfirmationToken;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.service.impel.ConfirmTokenServiceImpel;
import com.example.HomeService_MVC.service.impel.UserServiceImpel;
import lombok.AllArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
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
@AllArgsConstructor
public class CustomerController {

    private final CustomerServiceImpel customerServiceImpel;
    private final DozerBeanMapper mapper;
    private final ConfirmTokenServiceImpel confirmTokenServiceImpel;
    private final UserServiceImpel userServiceImpel;
    private final JavaMailSender mailSender;

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute @RequestBody CustomerDto customerSave) throws MessagingException {
        Customer customer = mapper.map(customerSave,Customer.class);
        customerServiceImpel.save(customer);
        ConfirmationToken confirmationToken = new ConfirmationToken(customer);
        confirmTokenServiceImpel.save(confirmationToken);
        String verifyCode = ("please click on link for confirm your email!"
                +"http://localhost:8080/customer/confirmAccount/"+confirmationToken.getConfirmToken());
        sendVerificationMessage(customer.getEmail(),verifyCode);
        return "OK";
    }

    @GetMapping("/confirmAccount/{token}")
    public String confirmUserAccount(@PathVariable("token") String confirmationToken ) {
        ConfirmationToken token = confirmTokenServiceImpel.findByConfirmToken(confirmationToken);
        if(token == null )
            return "شما قبلا ایمیل خود را تایید کرده اید!";
        Customer customer = (Customer) userServiceImpel.findByEmail(token.getUser().getEmail()).orElseThrow(() -> new UsernameNotFoundException("متاسفانه شما پیدا نشدید!"));
        if(customer.isEnabled())
            return "شما قبلا ایمیل خود را تایید کرده اید!";
        customer.setEnabled(true);
        customerServiceImpel.updateEnable(customer);
        confirmTokenServiceImpel.deleteToken(token);
        return "ایمیل شما با موفقیت تایید شد!";
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
    public ResponseEntity<String> updatePassword(@Valid @RequestBody PasswordDto passwordDTO){
        customerServiceImpel.updatePassword(new Customer(),passwordDTO);
        return ResponseEntity.ok("OK");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/gridSearch")
    public ResponseEntity<List<CustomerDto>> gridSearch(@ModelAttribute @RequestBody DynamicSearchDto dynamicSearch) {
        List<Customer> customerList = customerServiceImpel.filterCustomer(dynamicSearch);
        List<CustomerDto> dtoList = new ArrayList<>();
        for (Customer s:customerList
        ) {
            dtoList.add(mapper.map(s, CustomerDto.class));
        }
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/showBalance")
    public String showBalance(){
        Customer customer = customerServiceImpel.getById(3);
        return String.valueOf(customer.getBalance());
    }
}
