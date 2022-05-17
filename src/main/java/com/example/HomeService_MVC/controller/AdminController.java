package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.controller.exception.ExpertNotFoundException;
import com.example.HomeService_MVC.dto.user.ExpertSave;
import com.example.HomeService_MVC.dto.user.ExpertViewDTO;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ExpertServiceImpel expertServiceImpel;
    private final DozerBeanMapper mapper;

    public AdminController(ExpertServiceImpel expertServiceImpel, DozerBeanMapper mapper) {
        this.expertServiceImpel = expertServiceImpel;
        this.mapper = mapper;
    }

    @GetMapping("/getAllExpertFalse")
    public ResponseEntity<List<ExpertViewDTO>> getAllExpertFalse(){
        List<Expert> expertList = expertServiceImpel.findAllByAcceptedFalse();
        if(expertList == null || expertList.size() == 0)
            throw new ExpertNotFoundException("unfortunately any expert doesn't register until now!");
        List<ExpertViewDTO> dtoList = new ArrayList<>();
        for (Expert e:expertList
        ) {
            dtoList.add(mapper.map(e,ExpertViewDTO.class));
        }
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/confirmExpert/{expertId}")
    public ResponseEntity<String> confirmExpert(@PathVariable("expertId") Integer expertId) {
        expertServiceImpel.ExpertAccept(expertId);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/addExpertToSubServices/{subServicesId}")
    public ResponseEntity<String> addExpertToSubServices(@RequestBody ExpertSave expertSave, @PathVariable Integer subServicesId){
        expertServiceImpel.addExpertToSubService(expertSave.getEmail(),subServicesId);
        return ResponseEntity.ok("OK");
    }
}
