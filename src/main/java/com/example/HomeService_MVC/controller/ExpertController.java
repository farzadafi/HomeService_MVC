package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.dto.user.DynamicSearchDto;
import com.example.HomeService_MVC.dto.user.ExpertDto;
import com.example.HomeService_MVC.dto.user.passwordChangeRequest;
import com.example.HomeService_MVC.model.ConfirmationToken;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.enumoration.Role;
import com.example.HomeService_MVC.service.impel.ConfirmTokenServiceImpel;
import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/expert")
@AllArgsConstructor
public class ExpertController {

    private final ExpertServiceImpel expertServiceImpel;
    private final ConfirmTokenServiceImpel confirmTokenServiceImpel;

    @PostMapping(value = "/save",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE} )
    public String save(@Valid @ModelAttribute("expertDto") @RequestBody ExpertDto expertDto) {
        Expert expert = convertExpertDTO(expertDto);
        expertServiceImpel.save(expert);
        ConfirmationToken confirmationToken = new ConfirmationToken(expert);
        confirmTokenServiceImpel.save(confirmationToken);
        confirmTokenServiceImpel.sendVerificationMessage(confirmationToken);
        return "OK";
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
            log.warn(e.getMessage());
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
