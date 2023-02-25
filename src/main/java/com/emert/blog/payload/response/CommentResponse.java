package com.emert.blog.payload.response;

import lombok.Data;


@Data
public class CommentResponse {
    private String name;
    private String email;
    private String body;
}