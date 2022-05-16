package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.controller.GlobalExceptionHandler;
import com.example.HomeService_MVC.dto.user.ExpertSave;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.enumoration.Role;
import com.example.HomeService_MVC.repository.ExpertRepository;
import com.example.HomeService_MVC.service.interfaces.ExpertService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ExpertServiceImpel implements ExpertService {

    private final ExpertRepository expertRepository;
    private final DozerBeanMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public ExpertServiceImpel(ExpertRepository expertRepository, DozerBeanMapper mapper) {
        this.expertRepository = expertRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(ExpertSave expertSave) {
        Expert expert = new Expert();
        try {
            expert = new Expert(expertSave.getFirstName(),expertSave.getLastName(),
                    expertSave.getEmail(),expertSave.getPassword(),null,50000L, Role.EXPERT,
                    expertSave.getCity(),expertSave.getImage().getBytes(),0,false,null);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.warn(e.getMessage());
        }
        expertRepository.save(expert);
    }
}
