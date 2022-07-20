package com.example.HomeService_MVC.mapper.interfaces;

import com.example.HomeService_MVC.dto.user.ExpertDto;
import com.example.HomeService_MVC.mapper.impel.ExpertMapperDecorator;
import com.example.HomeService_MVC.model.Expert;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
@DecoratedWith(ExpertMapperDecorator.class)
public interface ExpertMapper {

    ExpertMapper INSTANCE = Mappers.getMapper(ExpertMapper.class);

    Expert dtoToModel(ExpertDto expertDto);
}
