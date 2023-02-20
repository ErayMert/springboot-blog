package com.emert.blog.mapper;

import com.emert.blog.entity.Comment;
import com.emert.blog.payload.dto.CommentDto;
import com.emert.blog.payload.request.CommentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CommentMapper {
    Comment commentRequestToComment(CommentRequest request);
    CommentDto commentToCommentDto(Comment comment);
    List<CommentDto> commentsToCommentDtos(List<Comment> comments);
}
