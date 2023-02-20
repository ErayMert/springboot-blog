package com.emert.blog.exception;

public interface HasParametricExceptionMessage {
    String[] getMessageParams();

    void setMessageParams(String... params);
}
