package com.example.HomeService_MVC.mapper.interfaces;

import com.example.HomeService_MVC.dto.user.CustomerDto;
import com.example.HomeService_MVC.mapper.impel.CustomerMapperDecorator;
import com.example.HomeService_MVC.model.Customer;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
@DecoratedWith(CustomerMapperDecorator.class)
public interface CustomerMapper {

}
