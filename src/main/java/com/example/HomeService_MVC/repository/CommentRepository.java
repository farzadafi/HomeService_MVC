package com.example.HomeService_MVC.repository;

import com.example.HomeService_MVC.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
