package com.god.online_education.exception;

import com.god.online_education.model.ErrorInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * API 运行时抛出的错误
 *
 * @author richard
 * @date 2019-06-21 13:16
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

//    private ApiException(ErrorInfo errorInfo, Throwable innerException) {
//        this(errorInfo.getCode(), errorInfo.getMsg());
//        this.initCause(innerException);
//    }
}
