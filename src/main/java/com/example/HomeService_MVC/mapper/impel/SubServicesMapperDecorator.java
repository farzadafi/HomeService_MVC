package com.example.HomeService_MVC.mapper.impel;

import com.example.HomeService_MVC.dto.services.SubServicesDto;
import com.example.HomeService_MVC.mapper.interfaces.SubServicesMapper;
import com.example.HomeService_MVC.model.SubServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        return subServices;
    }

    @Override
    public SubServicesDto modelToDto(SubServices subServices) {
        SubServicesDto subServicesDto = subServicesMapper.modelToDto(subServices);
        subServicesDto.setId(subServices.getId());
        subServicesDto.setSubServicesName(subServices.getSubServicesName());
        subServicesDto.setMinimalPrice(subServices.getMinimalPrice());
        subServicesDto.setDescription(subServices.getDescription());
        return subServicesDto;
    }

    @Override
    public List<SubServicesDto> modelListToDtoList(Set<SubServices> subServicesSet) {
        List<SubServicesDto> subServicesDtoList = new ArrayList<>();
        for (SubServices s:subServicesSet
             ) {
            subServicesDtoList.add(modelToDto(s));
        }
        return subServicesDtoList;
    }
}
