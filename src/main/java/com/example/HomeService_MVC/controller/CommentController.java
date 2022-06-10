package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.core.SecurityUtil;
import com.example.HomeService_MVC.dto.comment.CommentDto;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.service.impel.CommentServiceImpel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/comment")
public class CommentController {

    private final CommentServiceImpel commentServiceImpel;

    public CommentController(CommentServiceImpel commentServiceImpel) {
        this.commentServiceImpel = commentServiceImpel;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/save/{orderId}")
    public String save(@Valid @RequestBody CommentDto commentDto,@PathVariable("orderId") Integer orderId) {
        Customer customer = (Customer) SecurityUtil.getCurrentUser();
        commentServiceImpel.placeAComment(customer,commentDto,orderId);
        return "OK";
    }
}
