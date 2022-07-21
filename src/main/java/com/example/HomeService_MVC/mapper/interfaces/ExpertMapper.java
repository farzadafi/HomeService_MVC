package com.example.HomeService_MVC.mapper.interfaces;

import com.example.HomeService_MVC.dto.user.ExpertDto;
import com.example.HomeService_MVC.model.Expert;

import java.util.List;

public interface ExpertMapper {

    Expert dtoToModel(ExpertDto expertDto);
    ExpertDto modelToDto(Expert expert);
    List<ExpertDto> modelListToDtoList(List<Expert> expertList);

}
