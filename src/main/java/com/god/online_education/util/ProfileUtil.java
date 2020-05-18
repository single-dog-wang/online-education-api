package com.god.online_education.util;

import com.god.online_education.dto.TokenDto;

import javax.servlet.http.HttpServletRequest;

/**
 * 从 Http Context 获取会话相关信息的工具类
 *
 * @author richard
 * @date 2019-06-21 22:29
 */
@SuppressWarnings("unused")
public final class ProfileUtil {
    private static final String ATTRIBUTE_USER_PROFILE = "USER_PROFILE";

    private static TokenDto getUserProfile(HttpServletRequest request) {
        return (TokenDto)request.getAttribute(ATTRIBUTE_USER_PROFILE);
    }

    public static Integer getUserId(HttpServletRequest request) {
        return ProfileUtil.getUserProfile(request).getUserId();
    }

    public static void setUserProfile(HttpServletRequest request, TokenDto token) {
        request.setAttribute(ATTRIBUTE_USER_PROFILE, token);
    }
}
