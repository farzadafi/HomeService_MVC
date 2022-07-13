package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.dto.user.DynamicSearchDto;
import com.example.HomeService_MVC.dto.user.PasswordDto;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.model.enumoration.Role;
import org.dozer.DozerBeanMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.HomeService_MVC.repository.CustomerRepository;
import com.example.HomeService_MVC.service.interfaces.CustomerService;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpel implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DozerBeanMapper mapper;

    public CustomerServiceImpel(CustomerRepository customerRepository, BCryptPasswordEncoder bCryptPasswordEncoder, DozerBeanMapper mapper) {
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = mapper;
    }

    @Override
    public void save(Customer customer) {
        customer.setBalance(50000L);
        customer.setRole(Role.ROLE_CUSTOMER);
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getConfPassword()));
        customerRepository.save(customer);
    }

    @Override
    public void updatePassword(Customer customer, PasswordDto passwordDTO) {
        customer.setPassword(passwordDTO.getSinglePassword());
        customerRepository.save(customer);
    }

    @Override
    public void updateBalance(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void updateEnable(Customer customer) {
        customerRepository.save(customer);
    }

    public List<Customer> filterCustomer(DynamicSearchDto dynamicSearch){
        Customer customer = mapper.map(dynamicSearch,Customer.class);
        return customerRepository.findAll(userSpecification(customer));
    }

    private Specification<Customer> userSpecification(Customer customer){
        return (userRoot, query, criteriaBuilder)
                -> {
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            criteriaQuery.select(userRoot);

            List<Predicate> predicates = new ArrayList<>();
            if(customer.getRole() != null )
                predicates.add(criteriaBuilder.equal(userRoot.get("role"),customer.getRole()));
            if(customer.getFirstName() != null && !customer.getFirstName().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("firstName"),customer.getFirstName()));
            if(customer.getLastName() != null && !customer.getLastName().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("lastName"),customer.getLastName()));
            if(customer.getEmail() != null && !customer.getEmail().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("email"),customer.getEmail()));

            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
