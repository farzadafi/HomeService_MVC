package com.example.HomeService_MVC.service.interfaces;


import com.example.HomeService_MVC.dto.user.PasswordDto;
import com.example.HomeService_MVC.model.Customer;

public interface CustomerService  {
    void save(Customer customer);
    void updatePassword(Customer customer, PasswordDto passwordDTO);
    Customer getById(Integer id);
    void updateBalance(Customer customer);
    void updateEnable(Customer customer);
}
