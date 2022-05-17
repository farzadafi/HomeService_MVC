package com.example.HomeService_MVC.repository;

import com.example.HomeService_MVC.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpertRepository extends JpaRepository<Expert,Integer> {
    List<Expert> findAllByAcceptedFalse();
}
