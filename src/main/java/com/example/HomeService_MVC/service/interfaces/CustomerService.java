package com.example.HomeService_MVC.service.interfaces;


import com.example.HomeService_MVC.dto.user.CustomerSave;
import com.example.HomeService_MVC.dto.user.PasswordDTO;
import com.example.HomeService_MVC.model.Customer;

public interface CustomerService  {
    void save(CustomerSave customerSave);
    void updatePassword(Customer customer, PasswordDTO passwordDTO);
    Customer getById(Integer id);
    void updateBalance(Customer customer);
}
