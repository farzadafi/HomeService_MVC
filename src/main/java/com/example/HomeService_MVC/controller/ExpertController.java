package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.dto.user.ExpertSave;
import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/Expert")
public class ExpertController {

    private final ExpertServiceImpel expertServiceImpel;

    public ExpertController(ExpertServiceImpel expertServiceImpel) {
        this.expertServiceImpel = expertServiceImpel;
    }

    @PostMapping(value = "/save",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE} )
    public ResponseEntity<ExpertSave> save(@Valid @ModelAttribute @RequestBody ExpertSave expertSave) {
        expertServiceImpel.save(expertSave);
        return ResponseEntity.ok(expertSave);
    }
}