package com.example.HomeService_MVC.mapper.impel;

import com.example.HomeService_MVC.dto.comment.CommentDto;
import com.example.HomeService_MVC.mapper.interfaces.CommentMapper;
import com.example.HomeService_MVC.model.Comment;

public class CommentMapperDecorator implements CommentMapper {

    private final CommentMapper commentMapper;

    public CommentMapperDecorator(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public Comment dtoToModel(CommentDto commentDto) {
        Comment comment = commentMapper.dtoToModel(commentDto);
        comment.setId(commentDto.getId());
        comment.setComment(commentDto.getComment());
        comment.setStars(commentDto.getStars());
        return comment;
    }
}
