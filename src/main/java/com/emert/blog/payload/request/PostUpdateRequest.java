package com.emert.blog.payload.request;

import com.emert.blog.payload.dto.CommentDto;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostUpdateRequest {

    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    private String content;

    private Set<CommentDto> comments;

    private Long categoryId;
}
