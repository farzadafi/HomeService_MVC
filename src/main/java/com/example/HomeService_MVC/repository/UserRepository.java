package com.example.HomeService_MVC.repository;

import com.example.HomeService_MVC.model.base.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(String email);
}
