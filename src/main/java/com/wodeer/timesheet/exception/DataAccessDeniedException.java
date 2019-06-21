package com.wodeer.timesheet.exception;

import com.wodeer.timesheet.enums.CommonErrorEnum;

/**
 * 客户端尝试访问不属于自己的数据
 *
 * @author richard
 * @date 2019-06-21 16:12
 */
public class DataAccessDeniedException extends ApiException {
    private DataAccessDeniedException(Integer code, String message) {
        super(code, message);
    }

    public DataAccessDeniedException(String message) {
        this(CommonErrorEnum.DATA_ACCESS_DENIED.getCode(), String.format(CommonErrorEnum.DATA_ACCESS_DENIED.getMsg(), message));
    }
}
