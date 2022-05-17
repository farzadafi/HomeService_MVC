package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.controller.exception.ExpertNotFoundException;
import com.example.HomeService_MVC.dto.services.SubServicesDTO;
import com.example.HomeService_MVC.dto.user.AdminDTO;
import com.example.HomeService_MVC.dto.user.ExpertSave;
import com.example.HomeService_MVC.dto.user.ExpertViewDTO;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.SubServices;
import com.example.HomeService_MVC.service.impel.AdminServiceImpel;
import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ExpertServiceImpel expertServiceImpel;
    private final AdminServiceImpel adminServiceImpel;
    private final DozerBeanMapper mapper;

    public AdminController(ExpertServiceImpel expertServiceImpel, AdminServiceImpel adminServiceImpel, DozerBeanMapper mapper) {
        this.expertServiceImpel = expertServiceImpel;
        this.adminServiceImpel = adminServiceImpel;
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

    @GetMapping("/showExpertSubServices/{expertEmail}")
    public ResponseEntity<List<SubServicesDTO>> showExpertSubServices(@PathVariable("expertEmail") String expertEmail){
        Expert expert = expertServiceImpel.findByEmail(expertEmail).orElseThrow(() -> new ExpertNotFoundException("This Expert is not found!"));
        Set<SubServices> subServicesSet = expert.getSubServices();
        if(subServicesSet == null || subServicesSet.size() == 0)
            return ResponseEntity.ok(null);
        else{
            List<SubServicesDTO> dtoList = new ArrayList<>();
            for (SubServices s:subServicesSet
            ) {
                dtoList.add(mapper.map(s,SubServicesDTO.class));
            }
            return ResponseEntity.ok(dtoList);
        }
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
