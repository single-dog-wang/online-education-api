package com.wodeer.timesheet.model;

import lombok.Getter;
import lombok.Setter;

/**
 * API 服务请求返回 http 主体信息统一结构
 *
 * @param <T> 返回的视图对象类型（vo）
 * @author richard
 * @date 2019-06-21 14:41
 */
@Getter
@Setter
public class ApiResult<T> {
    private static final String SUCCESS = "success";
    /**
     * 返回结果编码，如果正常返回0，否则返回错误编码
     */
    private Integer code;

    /**
     * 提示信息，当code != 0 时，返回错误消息
     */
    private String msg;

    /**
     * Api运行成功时，返回的业务数据。
     * 当 code != 0 时，这个字段返回为null
     */
    private T data;

    /**
     * 返回带自定义对象的成功结果
     *
     * @param <T>          返回的视图对象类型（vo）
     * @param resultObject 返回的视图对象实例
     * @return the api result
     */
    public static <T> ApiResult<T> success(T resultObject) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(0);
        result.setMsg(SUCCESS);
        result.setData(resultObject);
        return result;
    }

    /**
     * 返回简单的成功结果
     *
     * @return the api result
     */
    public static ApiResult success() {
        return success(null);
    }

    /**
     * 返回错误结果
     *
     * @param errEnum 预先定义好的错误错误枚举
     * @return 带错误提示的返回结果
     */
    public static ApiResult fail(ErrorInfo errEnum) {
        ApiResult result = new ApiResult();
        result.setCode(errEnum.getCode());
        result.setMsg(errEnum.getMsg());
        return result;
    }

    /**
     * 指定错误消息
     *
     * @param code 错误编码
     * @param msg  错误消息
     * @return 带错误提示的返回结果
     */
    public static ApiResult fail(Integer code, String msg) {
        ApiResult result = new ApiResult();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 错误消息需要更多定制
     *
     * @param errEnum 预先定义好的错误错误枚举
     * @param params  用来替换预先定义好错误消息中的占位符
     * @return 带错误提示的返回结果
     */
    public static ApiResult fail(ErrorInfo errEnum, Object... params) {
        ApiResult result = new ApiResult();
        result.setCode(errEnum.getCode());
        result.setMsg(String.format(errEnum.getMsg(), params));
        return result;
    }
}
