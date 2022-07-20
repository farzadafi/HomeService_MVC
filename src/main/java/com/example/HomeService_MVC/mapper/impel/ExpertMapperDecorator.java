package com.example.HomeService_MVC.mapper.impel;

import com.example.HomeService_MVC.dto.user.ExpertDto;
import com.example.HomeService_MVC.mapper.interfaces.ExpertMapper;
import com.example.HomeService_MVC.model.Expert;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class ExpertMapperDecorator implements ExpertMapper {

    private final ExpertMapper expertMapper;

    @Override
    public Expert dtoToModel(ExpertDto expertDto) {
        Expert expert = expertMapper.dtoToModel(expertDto);
        expert.setFirstName(expertDto.getFirstName());
        expert.setLastName(expertDto.getLastName());
        expert.setEmail(expertDto.getEmail());
        expert.setPassword(expertDto.getPassword()[0]);
        expert.setConfPassword(expertDto.getConfPassword());
        try {
            expert.setImage(expertDto.getImage().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
        }
        return expert;
    }
}
