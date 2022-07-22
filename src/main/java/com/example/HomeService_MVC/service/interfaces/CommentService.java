package com.example.HomeService_MVC.service.interfaces;


import com.example.HomeService_MVC.model.Comment;

public interface CommentService {
    void placeAComment(Comment comment, Integer orderId);
}
