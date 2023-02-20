package com.emert.blog.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BlogAPIException extends RuntimeException{

    private final HttpStatus status;
    private final String message;

    public BlogAPIException(HttpStatus status, String message){
        super(message);
        this.message = message;
        this.status = status;
    }
}