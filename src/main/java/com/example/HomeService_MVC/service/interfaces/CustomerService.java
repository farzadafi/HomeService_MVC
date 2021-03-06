package com.example.HomeService_MVC.service.interfaces;


import com.example.HomeService_MVC.dto.user.passwordChangeRequest;
import com.example.HomeService_MVC.model.Customer;

public interface CustomerService  {
    void save(Customer customer);
    void updatePassword(passwordChangeRequest passwordChangeRequest);
    void updateBalance(Customer customer);
    void updateEnable(Customer customer);
}
