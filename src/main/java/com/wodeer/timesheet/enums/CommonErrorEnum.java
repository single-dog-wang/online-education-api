package com.wodeer.timesheet.enums;

import com.wodeer.timesheet.model.ErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author richard
 * @date 2019-02-27 23:48
 * 公共业务错误
 */
@Getter
@AllArgsConstructor
public enum CommonErrorEnum implements ErrorInfo {
    // 系统公用
    UNKNOWN_ERROR(100000, "未知错误"),
    AUTH_FAILED(110403, "身份认证失败，%s。"),
    FORM_VIOLATION(110001, "表单参数错误：%s。"),
    PATH_PARAM_LACK(110002, "缺少路径参数：%s。"),
    QUERY_PARAM_LACK(110003, "缺少请求参数：%s。"),
    PARAM_VIOLATION(110004, "请求参数错误：%s。"),

    INVALID_PARAMETER(110006, "请求参数有误：%s。"),
    DATA_ACCESS_DENIED(110007, "访问数据权限错误：%s。"),
    SYS_DATA_LOST(110008, "系统数据丢失：%s。"),

    // 数据库相关
    DUPLICATE_DATA(110101, "有重复数据冲突。"),
//    UPDATE_NOT_FOUND(11010?, "要更新的数据未找到"),
//    DELETE_NOT_FOUND(11010?, "要删除的数据未找到"),

    // 短信相关
    SMS_CODE_LIMIT(110201, "请不要重复操作，%d秒内只能获取一次验证码。"),
    SMS_CODE_EXPIRED(110202, "验证码已过期，请重新申请。"),
    SMS_CODE_WRONG(110203, "验证码错误。"),
    SMS_SEND_ERROR(110204, "发送短信出错。"),
    SMS_SEND_FAILED(110204, "发送短信未成功：%s，%s。"),

    // 文件相关
    FILE_UPLOAD_IO(110301, "文件上传失败。"),
    ;

    private Integer code;
    private String msg;
}
