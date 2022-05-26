package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.controller.exception.ExpertNotFoundException;
import com.example.HomeService_MVC.controller.exception.SubServicesNotFoundException;
import com.example.HomeService_MVC.dto.services.SubServicesDTO;
import com.example.HomeService_MVC.dto.user.AdminDTO;
import com.example.HomeService_MVC.dto.user.ExpertDTO;
import com.example.HomeService_MVC.dto.user.ExpertSubServicesDTO;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.SubServices;
import com.example.HomeService_MVC.service.impel.AdminServiceImpel;
import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    private final ExpertServiceImpel expertServiceImpel;
    private final AdminServiceImpel adminServiceImpel;
    private final DozerBeanMapper mapper;
    private final ExpertController expertController;

    public AdminController(ExpertServiceImpel expertServiceImpel, AdminServiceImpel adminServiceImpel, DozerBeanMapper mapper, ExpertController expertController) {
        this.expertServiceImpel = expertServiceImpel;
        this.adminServiceImpel = adminServiceImpel;
        this.mapper = mapper;
        this.expertController = expertController;
    }

    @GetMapping("/getAllExpertFalse")
    public ResponseEntity<List<ExpertDTO>> getAllExpertFalse(){
        List<Expert> expertList = expertServiceImpel.findAllByAcceptedFalse();
        if(expertList == null || expertList.size() == 0)
            throw new ExpertNotFoundException("unfortunately any expert doesn't register until now!");
        List<ExpertDTO> dtoList = new ArrayList<>();
        for (Expert e:expertList
        ) {
            dtoList.add(expertController.convertExpertToExpertDTO(e));
        }
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/confirmExpert/{expertId}")
    public String confirmExpert(@PathVariable("expertId") Integer expertId) {
        expertServiceImpel.ExpertAccept(expertId);
        return "OK";
    }

    @PostMapping("/addExpertToSubServices")
    public String addExpertToSubServices(@ModelAttribute @RequestBody ExpertSubServicesDTO dto){
        expertServiceImpel.addExpertToSubService(dto.getEmail(),dto.getId());
        return "OK";
    }

    @GetMapping("/showExpertSubServices/{expertEmail}")
    public ResponseEntity<List<SubServicesDTO>> showExpertSubServices(@PathVariable("expertEmail") String expertEmail) {
        Expert expert = expertServiceImpel.findByEmail(expertEmail).orElseThrow(() -> new ExpertNotFoundException("This Expert is not found!"));
        Set<SubServices> subServicesSet = expert.getSubServices();
        if (subServicesSet == null || subServicesSet.size() == 0)
            throw new SubServicesNotFoundException("This Expert doesn't have any SubServices until yet");
        else {
            List<SubServicesDTO> dtoList = new ArrayList<>();
            for (SubServices s : subServicesSet
            ) {
                dtoList.add(mapper.map(s, SubServicesDTO.class));
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
