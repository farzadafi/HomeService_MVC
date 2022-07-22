package com.example.HomeService_MVC.mapper.interfaces;

import com.example.HomeService_MVC.dto.comment.CommentDto;
import com.example.HomeService_MVC.model.Comment;
import org.mapstruct.factory.Mappers;

public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment dtoToModel(CommentDto commentDto);
}
