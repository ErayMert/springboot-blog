package com.emert.blog.payload.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
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