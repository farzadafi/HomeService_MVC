package com.example.HomeService_MVC.repository;

import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.base.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ExpertRepository extends JpaRepository<Expert,Integer>, JpaSpecificationExecutor<Expert> {
    List<Expert> findAllByAcceptedFalse();
    Optional<Expert> findExpertByEmail(String expertEmail);
    @NonNull
    List<Expert> findAll(Specification<Expert> specification);

}
