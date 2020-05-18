package com.god.online_education.aop;

import com.god.online_education.enums.CommonErrorEnum;
import com.god.online_education.config.MiscConfig;
import com.god.online_education.exception.*;
import com.god.online_education.model.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局错误处理
 *
 * @author richard
 * @date 2019-06-21 12:55
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private MiscConfig miscConfig;

    /**
     * 表单验证（RequestBody）错误处理
     *
     * @param ex 捕获的错误
     * @return 带错误提示的返回结果
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult handleFormValidation(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult()
                            .getFieldErrors()
                            .stream()
                            .map(err -> err.getField() + err.getDefaultMessage())
                            .collect(Collectors.joining("；"));

        return ApiResult.fail(CommonErrorEnum.FORM_VIOLATION, errorMsg);
    }

    /**
     * 缺少路径参数（PathVariable）错误处理
     *
     * @param ex 捕获的错误
     * @return 带错误提示的返回结果
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResult handleLackPathParam(MethodArgumentTypeMismatchException ex) {
        return ApiResult.fail(CommonErrorEnum.PATH_PARAM_LACK, ex.getName());
    }

    /**
     * 缺少请求参数（RequestVariable/QueryString）错误处理
     *
     * @param ex 捕获的错误
     * @return 带错误提示的返回结果
     */
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResult handleLackQueryParam(MissingServletRequestParameterException ex) {
        return ApiResult.fail(CommonErrorEnum.QUERY_PARAM_LACK, ex.getParameterName());
    }

    /**
     * 请求参数或请求参数验证（PathVariable & RequestVariable）错误处理
     *
     * @param ex 捕获的错误
     * @return 带错误提示的返回结果
     */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResult handleConstraintViolationException(ConstraintViolationException ex) {
        return ApiResult.fail(CommonErrorEnum.PARAM_VIOLATION, ex.getMessage());
    }

    /**
     * 验证参数异常
     *
     * @param ex 捕获的错误
     * @return 带错误提示的返回结果
     */
    @ResponseBody
    @ExceptionHandler(InvalidApiParameterException.class)
    public ApiResult handleInvalidParameterException(InvalidApiParameterException ex) {
        log.warn(ex.getMessage());
        return ApiResult.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 一般业务异常
     *
     * @param ex 捕获的错误
     * @return 带错误提示的返回结果
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ApiResult handleBusinessException(BusinessException ex) {
        log.info(ex.getMessage());
        return ApiResult.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 身份认证异常
     *
     * @param ex 捕获的错误
     * @return 带错误提示的返回结果
     */
    @ResponseBody
    @ExceptionHandler(AuthException.class)
    public ApiResult handleInvalidParameterException(AuthException ex) {
        log.warn(ex.getMessage());
        return ApiResult.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 数据访问权限异常
     *
     * @param ex 捕获的错误
     * @return 带错误提示的返回结果
     */
    @ResponseBody
    @ExceptionHandler(DataAccessDeniedException.class)
    public ApiResult handleDataAccessDeniedException(DataAccessDeniedException ex) {
        log.warn(ex.getMessage());
        return ApiResult.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 数据库出现主键冲突或者唯一索引冲突
     *
     * @param ex 捕获的错误
     * @return 带错误提示的返回结果
     */
    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    public ApiResult handleDataAccessDeniedException(DuplicateKeyException ex) {
        log.warn(CommonErrorEnum.DUPLICATE_DATA.getMsg(), ex);
        return ApiResult.fail(CommonErrorEnum.DUPLICATE_DATA);
    }

    /**
     * 代码中捕捉的错误
     *
     * @param ex 捕获的错误
     * @return 带错误提示的返回结果
     */
    @ResponseBody
    @ExceptionHandler(ApiException.class)
    public ApiResult handleDataAccessDeniedException(ApiException ex) {
        log.warn(ex.getMessage());
        return ApiResult.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 其他（未捕捉的）错误
     *
     * @param ex 捕获的错误
     * @return 带错误提示的返回结果
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ApiResult handleException(Exception ex) {
        log.error(CommonErrorEnum.UNKNOWN_ERROR.getMsg(), ex);
        if (miscConfig.getEnableOutputUnknownException()) {
            return ApiResult.fail(CommonErrorEnum.UNKNOWN_ERROR.getCode(), ex.getMessage());
        }
        return ApiResult.fail(CommonErrorEnum.UNKNOWN_ERROR);
    }
}
