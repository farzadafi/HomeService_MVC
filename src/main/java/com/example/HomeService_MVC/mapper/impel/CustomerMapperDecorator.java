package com.example.HomeService_MVC.mapper.impel;

import com.example.HomeService_MVC.dto.user.CustomerDto;
import com.example.HomeService_MVC.mapper.interfaces.CustomerMapper;
import com.example.HomeService_MVC.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerMapperDecorator implements CustomerMapper {

    private final CustomerMapper customerMapper;

    public CustomerMapperDecorator(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public Customer dtoToModel(CustomerDto customerDto) {
        Customer customer = customerMapper.dtoToModel(customerDto);
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(customerDto.getPassword()[0]);
        customer.setConfPassword(customerDto.getConfPassword());
        return customer;
    }

    @Override
    public CustomerDto modelToDto(Customer customer) {
        CustomerDto customerDto = customerMapper.modelToDto(customer);
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setEmail(customer.getEmail());
        return customerDto;
    }

    @Override
    public List<CustomerDto> modelListToDtoList(List<Customer> customerList) {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer c : customerList
        ) {
            customerDtoList.add(modelToDto(c));
        }
        return customerDtoList;
    }
}
