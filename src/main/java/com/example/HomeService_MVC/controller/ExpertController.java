package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.dto.user.ExpertSave;
import com.example.HomeService_MVC.dto.user.PasswordDTO;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.enumoration.Role;
import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/expert")
public class ExpertController {

    private final ExpertServiceImpel expertServiceImpel;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ExpertController(ExpertServiceImpel expertServiceImpel) {
        this.expertServiceImpel = expertServiceImpel;
    }

    @PostMapping(value = "/save",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE} )
    public String save(@Valid @ModelAttribute @RequestBody ExpertSave expertSave) {
        expertServiceImpel.save(convertExpertDTO(expertSave));
        return "OK";
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody PasswordDTO passwordDTO){
        expertServiceImpel.updatePassword(1,passwordDTO);
        return ResponseEntity.ok("OK");
    }

    private Expert convertExpertDTO(ExpertSave expertSave){
        Expert expert = new Expert(expertSave.getFirstName(),expertSave.getLastName(),
                expertSave.getEmail(),expertSave.getConfPassword(),
                5000L, Role.EXPERT,expertSave.getCity()
                ,null,0);
        try {
            expert.setImage(expertSave.getImage().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.warn(e.getMessage());
        }
        return expert;
    }
}
