package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.controller.exception.ExpertNotFoundException;
import com.example.HomeService_MVC.controller.exception.SubServicesNotFoundException;
import com.example.HomeService_MVC.dto.services.SubServicesDto;
import com.example.HomeService_MVC.dto.user.ExpertDto;
import com.example.HomeService_MVC.dto.user.PasswordDto;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.SubServices;
import com.example.HomeService_MVC.service.impel.AdminServiceImpel;
import com.example.HomeService_MVC.service.impel.ExpertServiceImpel;
import lombok.AllArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final ExpertServiceImpel expertServiceImpel;
    private final AdminServiceImpel adminServiceImpel;
    private final DozerBeanMapper mapper;
    private final ExpertController expertController;

    @GetMapping("/getAllExpertFalse")
    public ResponseEntity<List<ExpertDto>> getAllExpertFalse(){
        List<Expert> expertList = expertServiceImpel.findAllByAcceptedFalse();
        if(expertList == null || expertList.size() == 0)
            throw new ExpertNotFoundException("متخصصی برای تایید وجود ندارد");
        return ResponseEntity.ok(expertToExpertDto(expertList));
    }

    @GetMapping("/confirmExpert/{expertId}")
    public String confirmExpert(@PathVariable("expertId") Integer expertId) {
        expertServiceImpel.ExpertAccept(expertId);
        return "OK";
    }

    @GetMapping("/addExpertToSubServices/{expertEmail}/{subServiceId}")
    public String addExpertToSubServices(@PathVariable("expertEmail") String expertEmail,
                                         @PathVariable("subServiceId") Integer subServiceId ){
        expertServiceImpel.addExpertToSubService(expertEmail,subServiceId);
        return "OK";
    }

    @GetMapping("/showExpertSubServices/{expertEmail}")
    public ResponseEntity<List<SubServicesDto>> showExpertSubServices(@PathVariable("expertEmail") String expertEmail) {
        Expert expert = expertServiceImpel.findByEmail(expertEmail).orElseThrow(() -> new ExpertNotFoundException("This Expert is not found!"));
        Set<SubServices> subServicesSet = expert.getSubServices();
        if (subServicesSet == null || subServicesSet.size() == 0)
            throw new SubServicesNotFoundException("This Expert doesn't have any SubServices until yet");
        else {
            List<SubServicesDto> dtoList = new ArrayList<>();
            for (SubServices s : subServicesSet
            ) {
                dtoList.add(mapper.map(s, SubServicesDto.class));
            }
            return ResponseEntity.ok(dtoList);
        }
    }

    @GetMapping("/removeExpertSubServices/{expertEmail}/{subServiceId}")
    public String removeExpertFromSubServices(@PathVariable("expertEmail") String expertEmail,
                                          @PathVariable("subServiceId") Integer subServiceId){
        expertServiceImpel.removeExpertSubServices(expertEmail,subServiceId);
        return "OK";
    }

    @PostMapping("/updateAdminPassword")
    public String updateAdminPassword(@Valid @RequestBody PasswordDto passwordDTO){
        adminServiceImpel.updateAdmin(passwordDTO);
        return "OK";
    }

    private List<ExpertDto> expertToExpertDto(List<Expert> expertList){
        List<ExpertDto> expertDtoList = new ArrayList<>();
        for (Expert e:expertList
        ) {
            expertDtoList.add(expertController.convertExpertToExpertDTO(e));
        }
        return expertDtoList;
    }
}
