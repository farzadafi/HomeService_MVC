package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.dto.user.ExpertSave;
import com.example.HomeService_MVC.dto.user.PasswordDTO;
import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/expert")
public class ExpertController {

    private final ExpertServiceImpel expertServiceImpel;

    public ExpertController(ExpertServiceImpel expertServiceImpel) {
        this.expertServiceImpel = expertServiceImpel;
    }

    @PostMapping(value = "/save",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE} )
    public String save(@Valid @ModelAttribute @RequestBody ExpertSave expertSave) {
        expertServiceImpel.save(expertSave);
        return "OK";
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody PasswordDTO passwordDTO){
        expertServiceImpel.updatePassword(1,passwordDTO);
        return ResponseEntity.ok("OK");
    }
}
