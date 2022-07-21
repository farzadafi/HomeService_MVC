package com.example.HomeService_MVC.mapper.interfaces;

import com.example.HomeService_MVC.dto.user.ExpertDto;
import com.example.HomeService_MVC.model.Expert;

public interface ExpertMapper {

    Expert dtoToModel(ExpertDto expertDto);
}
