package com.emert.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private String title;
    private String description;
    private String content;
    private Set<CommentResponse> comments;
    private CategoryResponse category;
}