package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.dto.user.DynamicSearchDto;
import com.example.HomeService_MVC.dto.user.ExpertDto;
import com.example.HomeService_MVC.dto.user.passwordChangeRequest;
import com.example.HomeService_MVC.model.ConfirmationToken;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.enumoration.Role;
import com.example.HomeService_MVC.model.enumoration.UserStatus;
import com.example.HomeService_MVC.service.impel.ConfirmTokenServiceImpel;
import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
import com.example.HomeService_MVC.service.impel.UserServiceImpel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/expert")
@AllArgsConstructor
public class ExpertController {

    private final ExpertServiceImpel expertServiceImpel;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ConfirmTokenServiceImpel confirmTokenServiceImpel;
    private final JavaMailSender mailSender;
    private final UserServiceImpel userServiceImpel;

    @PostMapping(value = "/save",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE} )
    public String save(@Valid @ModelAttribute @RequestBody ExpertDto expertSave) {
        expertServiceImpel.save(convertExpertDTO(expertSave));
        ConfirmationToken confirmationToken = new ConfirmationToken(convertExpertDTO(expertSave));
        confirmTokenServiceImpel.save(confirmationToken);
        String verifyCode = ("please click on link for confirm your email!"
                +"http://localhost:8080/customer/confirmAccount/"+confirmationToken.getConfirmToken());
        sendVerificationMessage(expertSave.getEmail(),verifyCode);
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

    @GetMapping("/confirmAccount/{token}")
    public String confirmUserAccount(@PathVariable("token") String confirmationToken ) {
        ConfirmationToken token = confirmTokenServiceImpel.findByConfirmToken(confirmationToken);
        if(token == null )
            return "شما قبلا ایمیل خود را تایید کرده اید!";
        Expert expert = (Expert) userServiceImpel.findByEmail(token.getUser().getEmail()).orElseThrow(() -> new UsernameNotFoundException("متاسفانه شما پیدا نشدید!"));
        if(expert.isEnabled())
            return "شما قبلا ایمیل خود را تایید کرده اید!";
        expert.setEnabled(true);
        expert.setUserStatus(UserStatus.WAITING_FOR_ACCEPT);
        expertServiceImpel.updateEnable(expert);
        confirmTokenServiceImpel.deleteToken(token);
        return "ایمیل شما با موفقیت تایید شد!";
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody passwordChangeRequest passwordChangeRequest){
        expertServiceImpel.updatePassword(1,passwordChangeRequest);
        return ResponseEntity.ok("OK");
    }

    private Expert convertExpertDTO(ExpertDto expertSave){
        Expert expert = new Expert(expertSave.getFirstName(),expertSave.getLastName(),
                expertSave.getEmail(),expertSave.getConfPassword(),
                5000L, Role.ROLE_EXPERT,expertSave.getCity()
                ,null,0);
        try {
            expert.setImage(expertSave.getImage().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.warn(e.getMessage());
        }
        return expert;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/gridSearch")
    public ResponseEntity<List<ExpertDto>> gridSearch(@ModelAttribute @RequestBody DynamicSearchDto dynamicSearch) {
        List<Expert> expertList = expertServiceImpel.filterExpert(dynamicSearch);
        List<ExpertDto> dtoList = new ArrayList<>();
        if(expertList.isEmpty())
            return ResponseEntity.ok(dtoList);
        for (Expert e:expertList
             ) {
            dtoList.add(convertExpertToExpertDTO(e));
        }
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/showBalance")
    public String showBalance(){
        Expert expert = expertServiceImpel.getById(3);
        return String.valueOf(expert.getBalance());
    }

    public ExpertDto convertExpertToExpertDTO(Expert expert){
        return new ExpertDto(expert.getId(),expert.getFirstName(),expert.getLastName(),expert.getEmail()
                            ,expert.getPassword(),expert.getConfPassword(),expert.getCity(),null);
    }
}
