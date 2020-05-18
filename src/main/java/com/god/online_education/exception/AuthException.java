package com.god.online_education.exception;

import com.god.online_education.enums.CommonErrorEnum;

/**
 * 身份认证错误
 *
 * @author richard
 * @date 2019-06-21 16:10
 */
public class AuthException extends ApiException {
    private AuthException(Integer code, String message) {
        super(code, message);
    }

    public AuthException(String message) {
        this(CommonErrorEnum.AUTH_FAILED.getCode(), String.format(CommonErrorEnum.AUTH_FAILED.getMsg(), message));
    }
}
