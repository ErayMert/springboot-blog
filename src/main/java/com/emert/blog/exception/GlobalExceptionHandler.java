package com.emert.blog.exception;

import com.emert.blog.constant.ExceptionsConstant;
import com.emert.blog.payload.base.BaseResponse;
import com.emert.blog.payload.base.ExceptionInfo;
import com.emert.blog.payload.base.ValidationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception exception){
        final ExceptionInfo exceptionInfo = this.getExceptionInfo(exception, ExceptionsConstant.EXCEPTION_TYPE_UNCATEGORIZED);
        return BaseResponse.builder()
                .success(ExceptionsConstant.STATUS_FAILURE)
                .error(exceptionInfo)
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public Object handleException(RuntimeException exception){
        final ExceptionInfo exceptionInfo = this.getExceptionInfo(exception, ExceptionsConstant.EXCEPTION_TYPE_RUNTIME);
        return BaseResponse.builder()
                .success(ExceptionsConstant.STATUS_FAILURE)
                .error(exceptionInfo)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RTBusinessException.class)
    public Object handleException(RTBusinessException exception){
        final ExceptionInfo exceptionInfo = this.getExceptionInfo(exception, ExceptionsConstant.EXCEPTION_TYPE_BUSINESS_RUNTIME);
        return BaseResponse.builder()
                .success(ExceptionsConstant.STATUS_FAILURE)
                .error(exceptionInfo)
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleException(ResourceNotFoundException exception){
        final ExceptionInfo exceptionInfo = this.getExceptionInfo(exception, ExceptionsConstant.EXCEPTION_TYPE_BUSINESS_RUNTIME);
        return BaseResponse.builder()
                .success(ExceptionsConstant.STATUS_FAILURE)
                .error(exceptionInfo)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleException(MethodArgumentNotValidException exception){
        final ExceptionInfo exceptionInfo = this.getExceptionInfo(exception, ExceptionsConstant.EXCEPTION_TYPE_VALIDATION);

        log.info("Exception Message : {}, Exception Type : {}, Exception Detail : {}",
                exceptionInfo.getMessage(),
                ExceptionsConstant.EXCEPTION_TYPE_VALIDATION,
                exceptionInfo.getDetailedMessage());

        exceptionInfo.setErrorCode("9999");
        exceptionInfo.setMessage("Validation Error");
        exceptionInfo.setDetailedMessage("Validation Error");

        List<ValidationInfo> validations = exception.getBindingResult().getAllErrors().stream()
                .map(objectError -> ValidationInfo.builder()
                        .type(objectError.getCode())
                        .message(objectError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return BaseResponse.builder()
                .success(ExceptionsConstant.STATUS_FAILURE)
                .error(exceptionInfo)
                .validations(validations)
                .build();
    }

    private ExceptionInfo getExceptionInfo(Exception exception, String exceptionType) {

        ExceptionInfo exceptionInfo = prepareExceptionInfo(exception, exceptionType);
        commonLoggingError(exception, exceptionType, exceptionInfo);
        exceptionInfo.setDetailedMessage("");

        return exceptionInfo;
    }

    private ExceptionInfo prepareExceptionInfo(Exception exception, String exceptionType) {
        ExceptionInfo exceptionInfo = ExceptionInfo.builder().build();

        if(exceptionType.equals(ExceptionsConstant.EXCEPTION_TYPE_UNCATEGORIZED)){
            exceptionInfo.setMessage("Beklenmedik bir hata oluştu. Daha fazla detay için uygulama loglarını kontrol edebilirsiniz.");
        }else{
            exceptionInfo.setMessage(exception.getMessage());
        }


        exceptionInfo.setSourceSystem("blog-app");
        exceptionInfo.setType(exceptionType);
        exceptionInfo.setDetailedMessage(this.prepareDetailsForExceptionMessage(exception));

        if(exception instanceof RTBusinessException rtBusinessException){
            exceptionInfo.setErrorCode(rtBusinessException.getCode());
        }

        return exceptionInfo;
    }

    private String prepareDetailsForExceptionMessage(Exception exception) {
        StringBuilder message = new StringBuilder();
        message.append("Exception Stack Trace: ").append(Arrays.toString(exception.getStackTrace()));
        Throwable cauese = exception.getCause();

        while (cauese != null){
            message.append(", Cause Message").append(cauese);
            message.append(", Cause Stack Trace").append(Arrays.toString(cauese.getStackTrace()));
            cauese = cauese.getCause();
        }

        return message.toString();
    }

    private void commonLoggingError(Exception exception, String exceptionType, ExceptionInfo exceptionInfo){
        log.info("Exception Message : {}, Exception Type : {}, Exception Detail : {}, Exception: {}",
                exceptionInfo.getMessage(),
                exceptionType,
                exceptionInfo.getDetailedMessage(),
                exception);
    }
}
