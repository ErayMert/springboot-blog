package com.emert.blog.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RTBusinessException extends RuntimeException implements HasParametricExceptionMessage{

    private String code = "";
    private String[] messageParams;

    public RTBusinessException(String... messageParams){
        this.messageParams = messageParams;
    }

    public RTBusinessException messageParams(String... messageParams){
        this.messageParams = messageParams;
        return this;
    }

    public RTBusinessException(String message){
        super(message);
    }

    public RTBusinessException(String message, Throwable cause){
        super(message, cause);
    }

    public RTBusinessException(String code, String message){
        super(message);
        this.code = code;
    }
}
