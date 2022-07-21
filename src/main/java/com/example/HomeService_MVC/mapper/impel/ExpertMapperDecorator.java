package com.example.HomeService_MVC.mapper.impel;

import com.example.HomeService_MVC.dto.user.ExpertDto;
import com.example.HomeService_MVC.mapper.interfaces.ExpertMapper;
import com.example.HomeService_MVC.model.Expert;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExpertMapperDecorator implements ExpertMapper {

    @Override
    public Expert dtoToModel(ExpertDto expertDto) {
        Expert expert = new Expert();
        expert.setFirstName(expertDto.getFirstName());
        expert.setLastName(expertDto.getLastName());
        expert.setEmail(expertDto.getEmail());
        expert.setCity(expertDto.getCity());
        expert.setPassword(expertDto.getPassword());
        expert.setConfPassword(expertDto.getConfPassword());
        try {
            expert.setImage(expertDto.getImage().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expert;
    }

    @Override
    public ExpertDto modelToDto(Expert expert) {
        ExpertDto expertDto = new ExpertDto();
        expertDto.setFirstName(expert.getFirstName());
        expertDto.setLastName(expert.getLastName());
        expertDto.setEmail(expert.getEmail());
        expertDto.setCity(expert.getCity());
        return expertDto;
    }

    @Override
    public List<ExpertDto> modelListToDtoList(List<Expert> expertList) {
        List<ExpertDto> expertDtoList = new ArrayList<>();
        for (Expert e : expertList
        ) {
            expertDtoList.add(modelToDto(e));
        }
        return expertDtoList;
    }
}
