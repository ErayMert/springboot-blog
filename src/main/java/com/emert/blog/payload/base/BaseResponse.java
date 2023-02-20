package com.emert.blog.payload.base;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class BaseResponse<T> {
    private T data;
    @Builder.Default
    private boolean success = true;
    private ExceptionInfo error;
    private List<ValidationInfo> validations;


    public BaseResponse(){

    }
    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public BaseResponse(T data, boolean success, ExceptionInfo error, List<ValidationInfo> validations) {
        this.data = data;
        this.success = success;
        this.error = error;
        this.validations = validations;
    }
}
