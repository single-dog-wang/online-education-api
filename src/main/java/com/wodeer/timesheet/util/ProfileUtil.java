package com.wodeer.timesheet.util;

import com.wodeer.timesheet.dto.TokenDto;

import javax.servlet.http.HttpServletRequest;

/**
 * @author richard
 * @date 2019-03-03 22:29
 */
public final class ProfileUtil {
    private static final String ATTRIBUTE_USER_PROFILE = "USER_PROFILE";

    public static TokenDto getUserProfile(HttpServletRequest request) {
        return (TokenDto)request.getAttribute(ATTRIBUTE_USER_PROFILE);
    }

    public static Integer getUserId(HttpServletRequest request) {
        return ProfileUtil.getUserProfile(request).getUserId();
    }

    public static void setUserProfile(HttpServletRequest request, TokenDto token) {
        request.setAttribute(ATTRIBUTE_USER_PROFILE, token);
    }
}
