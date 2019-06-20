package com.wodeer.timesheet.aop;

import com.wodeer.timesheet.model.ApiResult;
import com.wodeer.timesheet.config.MiscConfig;
import com.wodeer.timesheet.enums.CommonErrorEnum;
import com.wodeer.timesheet.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author richard
 * @date 2019-02-27 12:55
 * 全局错误处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @Resource
    private MiscConfig miscConfig;

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ApiResult handleException(Exception ex) {
        // 一般业务异常
        if (ex instanceof BusinessException) {
            BusinessException exBiz = (BusinessException)ex;
            log.info(exBiz.getMessage());
            return ApiResult.fail(exBiz.getCode(), exBiz.getMessage());
        }
        // 验证参数异常
        if (ex instanceof InvalidParameterException) {
            InvalidParameterException exParam = (InvalidParameterException)ex;
            log.warn(exParam.getMessage());
            return ApiResult.fail(exParam.getCode(), exParam.getMessage());
        }
        // 身份认证异常
        if (ex instanceof AuthException) {
            AuthException exParam = (AuthException)ex;
            log.warn(exParam.getMessage());
            return ApiResult.fail(exParam.getCode(), exParam.getMessage());
        }
        // 数据访问权限异常
        if (ex instanceof DataAccessDeniedException) {
            DataAccessDeniedException exParam = (DataAccessDeniedException)ex;
            log.warn(exParam.getMessage());
            return ApiResult.fail(exParam.getCode(), exParam.getMessage());
        }
        // 数据库出现主键冲突或者唯一索引冲突
        if (ex instanceof DuplicateKeyException) {
            log.warn(CommonErrorEnum.DUPLICATE_DATA.getMsg(), ex);
            return ApiResult.fail(CommonErrorEnum.DUPLICATE_DATA);
        }
        // 代码中捕捉的错误
        if (ex instanceof ApiException) {
            ApiException apiException = (ApiException)ex;
            log.error(apiException.getMessage(), ex);
            return ApiResult.fail(apiException.getCode(), apiException.getMessage());
        }
        // 未捕捉的错误
        log.error(CommonErrorEnum.UNKNOWN_ERROR.getMsg(), ex);
        if (miscConfig.getEnableOutputUnknownException()) {
            return ApiResult.fail(CommonErrorEnum.UNKNOWN_ERROR.getCode(), ex.getMessage());
        }
        return ApiResult.fail(CommonErrorEnum.UNKNOWN_ERROR);
    }
}
