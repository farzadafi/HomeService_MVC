package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.dto.services.SubServicesDTO;
import com.example.HomeService_MVC.dto.user.AdminDTO;
import com.example.HomeService_MVC.dto.user.ExpertSave;
import com.example.HomeService_MVC.dto.user.ExpertViewDTO;
import com.example.HomeService_MVC.service.impel.AdminServiceImpel;
import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
import com.example.HomeService_MVC.service.impel.SubServicesServiceImpel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ExpertServiceImpel expertServiceImpel;
    private final AdminServiceImpel adminServiceImpel;
    private final SubServicesServiceImpel subServicesServiceImpel;

    public AdminController(ExpertServiceImpel expertServiceImpel, AdminServiceImpel adminServiceImpel, SubServicesServiceImpel subServicesServiceImpel) {
        this.expertServiceImpel = expertServiceImpel;
        this.adminServiceImpel = adminServiceImpel;
        this.subServicesServiceImpel = subServicesServiceImpel;
    }

    @GetMapping("/getAllExpertFalse")
    public ResponseEntity<List<ExpertViewDTO>> getAllExpertFalse(){
        List<ExpertViewDTO> expertViewDTOList = expertServiceImpel.findAllByAcceptedFalse();
        return ResponseEntity.ok(expertViewDTOList);
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

    @GetMapping("/showExpertSubServices/{expertEmail}")
    public ResponseEntity<List<SubServicesDTO>> showExpertSubServices(@PathVariable("expertEmail") String expertEmail){
        List<SubServicesDTO> dtoList = subServicesServiceImpel.expertSubService(expertEmail);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/removeExpertSubServices/{expertEmail}/{servicesId}")
    public ResponseEntity<String> removeExpertSubServices(@PathVariable("expertEmail") String expertEmail, @PathVariable("servicesId") Integer servicesId){
        expertServiceImpel.removeExpertSubServices(expertEmail,servicesId);
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/updateAdmin")
    public ResponseEntity<String> updateAdmin(@Valid @RequestBody AdminDTO adminDTO){
        adminServiceImpel.updateAdmin(adminDTO);
        return ResponseEntity.ok("OK");
    }
}
