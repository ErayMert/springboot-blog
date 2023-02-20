package com.emert.blog.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlogConstant {

    public static final String BEARER_TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_NAME = "Authorization";

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public  static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
    
    public static final String[] AUTH_SWAGGER_WHITE_LIST = {
            "/v3/api-docs/**",
            "/v2/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/swagger-ui/index.html",
            "/webjars/**"
    };
}