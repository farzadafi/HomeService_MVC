package com.example.HomeService_MVC.mapper.impel;

import com.example.HomeService_MVC.dto.services.SubServicesDto;
import com.example.HomeService_MVC.mapper.interfaces.SubServicesMapper;
import com.example.HomeService_MVC.model.SubServices;

public class SubServicesMapperDecorator implements SubServicesMapper {

    private final SubServicesMapper subServicesMapper;

    public SubServicesMapperDecorator(SubServicesMapper subServicesMapper) {
        this.subServicesMapper = subServicesMapper;
    }

    @Override
    public SubServices dtoToModel(SubServicesDto subServicesDto) {
        SubServices subServices = subServicesMapper.dtoToModel(subServicesDto);
        subServices.setId(subServicesDto.getId());
        subServices.setSubServicesName(subServicesDto.getSubServicesName());
        subServices.setMinimalPrice(subServicesDto.getMinimalPrice());
        subServices.setDescription(subServicesDto.getDescription());
        return null;
    }
}
