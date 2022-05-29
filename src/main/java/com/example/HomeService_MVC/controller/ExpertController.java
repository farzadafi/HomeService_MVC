package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.dto.user.DynamicSearchDTO;
import com.example.HomeService_MVC.dto.user.ExpertDTO;
import com.example.HomeService_MVC.dto.user.PasswordDTO;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.enumoration.Role;
import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/expert")
public class ExpertController {

    private final ExpertServiceImpel expertServiceImpel;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ExpertController(ExpertServiceImpel expertServiceImpel) {
        this.expertServiceImpel = expertServiceImpel;
    }

    @PostMapping(value = "/save",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE} )
    public String save(@Valid @ModelAttribute @RequestBody ExpertDTO expertSave) {
        expertServiceImpel.save(convertExpertDTO(expertSave));
        return "OK";
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody PasswordDTO passwordDTO){
        expertServiceImpel.updatePassword(1,passwordDTO);
        return ResponseEntity.ok("OK");
    }

    private Expert convertExpertDTO(ExpertDTO expertSave){
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
    public ResponseEntity<List<ExpertDTO>> gridSearch(@ModelAttribute @RequestBody DynamicSearchDTO dynamicSearch) {
        List<Expert> expertList = expertServiceImpel.filterExpert(dynamicSearch);
        List<ExpertDTO> dtoList = new ArrayList<>();
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

    public ExpertDTO convertExpertToExpertDTO(Expert expert){
        return new ExpertDTO(expert.getId(),expert.getFirstName(),expert.getLastName(),expert.getEmail()
                            ,expert.getPassword(),expert.getConfPassword(),expert.getCity(),null);
    }
}
