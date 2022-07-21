package com.example.HomeService_MVC.mapper.interfaces;

import com.example.HomeService_MVC.mapper.impel.SubServicesMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
@DecoratedWith(SubServicesMapperDecorator.class)
public interface SubServicesMapper {

    SubServicesMapper INSTANCE = Mappers.getMapper(SubServicesMapper.class);
}
