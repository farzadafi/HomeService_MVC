package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.dto.user.PasswordDTO;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.model.enumoration.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.HomeService_MVC.repository.CustomerRepository;
import com.example.HomeService_MVC.service.interfaces.CustomerService;

@Service
public class CustomerServiceImpel implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomerServiceImpel(CustomerRepository customerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public void save(Customer customer) {
        customer.setBalance(50000L);
        customer.setRole(Role.ROLE_CUSTOMER);
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
    }

    @Override
    public void updatePassword(Customer customer, PasswordDTO passwordDTO) {
        customer.setPassword(passwordDTO.getSinglePassword());
        customerRepository.save(customer);
    }

    @Override
    public Customer getById(Integer id) {
        return customerRepository.getById(id);
    }

    @Override
    public void updateBalance(Customer customer) {
        customerRepository.save(customer);
    }
}
