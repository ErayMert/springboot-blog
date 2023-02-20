package com.emert.blog.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionsConstant {

    public static final Boolean STATUS_FAILURE = false;

    public static final String EXCEPTION_TYPE_VALIDATION = "VALIDATION";

    public static final String EXCEPTION_TYPE_BUSINESS = "BUSINESS";

    public static final String EXCEPTION_TYPE_BUSINESS_RUNTIME = "BUSINESS_RUNTIME";

    public static final String EXCEPTION_TYPE_UNCATEGORIZED = "UNCATEGORIZED";

    public static final String EXCEPTION_TYPE_RUNTIME = "RUNTIME";

}
