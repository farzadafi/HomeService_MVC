package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.dto.user.CustomerSave;
import com.example.HomeService_MVC.dto.user.PasswordDTO;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.model.enumoration.Role;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import com.example.HomeService_MVC.repository.CustomerRepository;
import com.example.HomeService_MVC.service.interfaces.CustomerService;

@Service
public class CustomerServiceImpel implements CustomerService {

    private final CustomerRepository customerRepository;
    private final DozerBeanMapper mapper;

    public CustomerServiceImpel(CustomerRepository customerRepository, DozerBeanMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }


    @Override
    public void save(CustomerSave customerSave) {
        Customer customer = mapper.map(customerSave,Customer.class);
        customer.setPassword(customerSave.getPassword()[0]);
        customer.setBalance(50000L);
        customer.setRole(Role.CUSTOMER);
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
