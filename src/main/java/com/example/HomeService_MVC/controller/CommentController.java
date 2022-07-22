package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.dto.comment.CommentDto;
import com.example.HomeService_MVC.mapper.interfaces.CommentMapper;
import com.example.HomeService_MVC.model.Comment;
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
        Comment comment = CommentMapper.INSTANCE.dtoToModel(commentDto);
        commentServiceImpel.placeAComment(comment,orderId);
        return "OK";
    }
}
