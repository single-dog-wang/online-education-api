package com.wodeer.timesheet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author richard
 * @date 2019-02-28 08:50
 */
@Getter
@AllArgsConstructor
public enum CacheEnum {
    // 登录验证码
    LOGIN_SMS_CODE("login:code:", 60L),
    // 用户登录身份凭证
    TOKEN("token:", 60*60*24L),
    // redis 保持连接心跳
    REDIS_HEARTBEAT("heartbeat:", 60*6L),
    ;

    private String prefix;
    private Long timeout;
}
