package com.wodeer.timesheet.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author richard
 * @date 2019-01-30 14:41
 * API 服务请求返回 http 主体信息统一结构
 */
@Getter
@Setter
public class ApiResult<T> {
    private static final String SUCCESS = "success";

    // 错误编码
    private Integer code;

    // 提示信息
    private String msg;

    // 具体内容
    private T data;

    // 返回带自定义对象的成功结果
    public static <T> ApiResult<T> success(T resultObject) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(0);
        result.setMsg(SUCCESS);
        result.setData(resultObject);
        return result;
    }

    // 返回简单的成功结果
    public static ApiResult success() {
        return success(null);
    }

    // 返回错误结果
    public static ApiResult fail(ErrorInfo errEnum) {
        ApiResult result = new ApiResult();
        result.setCode(errEnum.getCode());
        result.setMsg(errEnum.getMsg());
        return result;
    }

    // 指定错误消息
    public static ApiResult fail(Integer code, String msg) {
        ApiResult result = new ApiResult();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    // 错误消息需要更多定制
    public static ApiResult fail(ErrorInfo errEnum, Object... params) {
        ApiResult result = new ApiResult();
        result.setCode(errEnum.getCode());
        result.setMsg(String.format(errEnum.getMsg(), params));
        return result;
    }
}
