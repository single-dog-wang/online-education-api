package com.wodeer.timesheet.exception;

import com.wodeer.timesheet.model.ErrorInfo;

/**
 * @author richard
 * @date 2019-03-02 14:15
 * 代码中主动抛出的一般业务异常
 */
public class BusinessException extends ApiException {
    public BusinessException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
