package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.dto.comment.CommentDto;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.service.impel.CommentServiceImpel;
import com.example.HomeService_MVC.service.impel.CustomerServiceImpel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentServiceImpel commentServiceImpel;
    private final CustomerServiceImpel customerServiceImpel;

    public CommentController(CommentServiceImpel commentServiceImpel, CustomerServiceImpel customerServiceImpel) {
        this.commentServiceImpel = commentServiceImpel;
        this.customerServiceImpel = customerServiceImpel;
    }

    @PostMapping("/save/{orderId}")
    public ResponseEntity<String> save(@Valid @RequestBody CommentDto commentDto, @PathVariable("orderId") Integer orderId) {
        Customer customer = customerServiceImpel.getById(1);
        commentServiceImpel.placeAComment(customer,commentDto,orderId);
        return ResponseEntity.ok("OK");
    }
}
