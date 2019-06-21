package com.wodeer.timesheet.exception;

import com.wodeer.timesheet.enums.CommonErrorEnum;

/**
 * 参数验证异常，在检查参数方法中抛出
 *
 * @author richard
 * @date 2019-06-21 10:03
 */
public class InvalidApiParameterException extends ApiException {
    private InvalidApiParameterException(Integer code, String message) {
        super(code, message);
    }

    public InvalidApiParameterException(String message) {
        this(CommonErrorEnum.INVALID_PARAMETER.getCode(), String.format(CommonErrorEnum.INVALID_PARAMETER.getMsg(), message));
    }
}
