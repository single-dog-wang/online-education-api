package com.wodeer.timesheet.exception;

import com.wodeer.timesheet.model.ErrorInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author richard
 * @date 2019-02-28 13:16
 * API 运行时抛出的错误
 */
@Getter
@Setter
public class ApiException extends RuntimeException {
    private Integer code;

    public ApiException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(ErrorInfo errorInfo) {
        this(errorInfo.getCode(), errorInfo.getMsg());
    }

    public ApiException(ErrorInfo errorInfo, Throwable innerException) {
        this(errorInfo.getCode(), errorInfo.getMsg());
        this.initCause(innerException);
    }
}
