package com.wodeer.timesheet.exception;

import com.wodeer.timesheet.model.ErrorInfo;

/**
 * 代码中主动抛出的一般业务异常
 *
 * @author richard
 * @date 2019-06-21 14:15
 */
public class BusinessException extends ApiException {
    public BusinessException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
