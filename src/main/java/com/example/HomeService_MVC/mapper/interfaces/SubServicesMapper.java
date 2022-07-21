package com.example.HomeService_MVC.mapper.interfaces;

import com.example.HomeService_MVC.dto.services.SubServicesDto;
import com.example.HomeService_MVC.mapper.impel.SubServicesMapperDecorator;
import com.example.HomeService_MVC.model.SubServices;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
@DecoratedWith(SubServicesMapperDecorator.class)
public interface SubServicesMapper {

    SubServicesMapper INSTANCE = Mappers.getMapper(SubServicesMapper.class);

    SubServices dtoToModel(SubServicesDto subServicesDto);
    SubServicesDto modelToDto(SubServices subServices);
}
