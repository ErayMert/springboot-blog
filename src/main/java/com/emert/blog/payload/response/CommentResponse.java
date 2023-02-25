package com.emert.blog.payload.response;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private String name;
    private String email;
    private String body;
}