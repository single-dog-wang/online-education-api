package com.wodeer.timesheet.exception;

import com.wodeer.timesheet.enums.CommonErrorEnum;

/**
 * @author richard
 * @date 2019-03-07 16:10
 * 身份认证错误
 */
public class AuthException extends ApiException {
    private AuthException(Integer code, String message) {
        super(code, message);
    }

    public AuthException(String message) {
        this(CommonErrorEnum.AUTH_FAILED.getCode(), String.format(CommonErrorEnum.AUTH_FAILED.getMsg(), message));
    }
}
