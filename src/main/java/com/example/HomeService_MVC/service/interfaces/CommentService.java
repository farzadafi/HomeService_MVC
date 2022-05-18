package com.example.HomeService_MVC.service.interfaces;


import com.example.HomeService_MVC.dto.comment.CommentDto;
import com.example.HomeService_MVC.model.Customer;

public interface CommentService {
    void placeAComment(Customer customer,CommentDto commentDto, Integer orderId);
}
