package com.wodeer.timesheet.exception;

import com.wodeer.timesheet.enums.CommonErrorEnum;

/**
 * @author richard
 * @date 2019-03-07 16:10
 * 客户端尝试访问不属于自己的数据
 */
public class DataAccessDeniedException extends ApiException {
    private DataAccessDeniedException(Integer code, String message) {
        super(code, message);
    }

    public DataAccessDeniedException(String message) {
        this(CommonErrorEnum.DATA_ACCESS_DENIED.getCode(), String.format(CommonErrorEnum.DATA_ACCESS_DENIED.getMsg(), message));
    }
}
