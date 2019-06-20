package com.wodeer.timesheet.exception;

import com.wodeer.timesheet.enums.CommonErrorEnum;

/**
 * @author richard
 * @date 2019-03-01 10:03
 * 参数验证异常，在检查参数方法中抛出
 */
public class InvalidParameterException extends ApiException {
    private InvalidParameterException(Integer code, String message) {
        super(code, message);
    }

    public InvalidParameterException(String message) {
        this(CommonErrorEnum.INVALID_PARAMETER.getCode(), String.format(CommonErrorEnum.INVALID_PARAMETER.getMsg(), message));
    }
}
