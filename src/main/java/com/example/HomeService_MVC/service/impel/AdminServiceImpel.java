package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.dto.user.AdminDTO;
import com.example.HomeService_MVC.model.Admin;
import com.example.HomeService_MVC.repository.UserRepository;
import com.example.HomeService_MVC.service.interfaces.AdminService;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpel implements AdminService {

    private final DozerBeanMapper mapper;
    private final UserRepository userRepository;

    public AdminServiceImpel(DozerBeanMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }


    @Override
    public void updateAdmin(AdminDTO adminDTO) {
        Admin admin = mapper.map(adminDTO,Admin.class);
        userRepository.save(admin);
    }
}
