package com.wodeer.timesheet.aop;

import com.alibaba.fastjson.JSON;
import com.wodeer.timesheet.dto.TokenDto;
import com.wodeer.timesheet.enums.CacheEnum;
import com.wodeer.timesheet.exception.AuthException;
import com.wodeer.timesheet.util.ProfileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 身份认证拦截器
 *
 * @author richard
 * @date 2019-06-21 20:04
 */
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    private static final String TOKEN_PARAM_KEY = "x-access-token";
    private static final String ANONYMOUS_ACCESS_PREFIX = "/auth";
    private static final String DOWNLOAD_URL_PREFIX= "/file/download/";

    @Autowired
    private StringRedisTemplate redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 身份认证模块的请求不需要在这里截断做验证，直接放行
        if (request.getServletPath().startsWith(ANONYMOUS_ACCESS_PREFIX)) {
            return true;
        }

        // 大部分请求是把token放在header中
        String tokenKey = request.getHeader(TOKEN_PARAM_KEY);
        // 页面上的锚点连接<a>不好加header，就把token放在url参数中
        if (StringUtils.isBlank(tokenKey) && request.getServletPath().startsWith(DOWNLOAD_URL_PREFIX)) {
            tokenKey = request.getParameter(TOKEN_PARAM_KEY);
        }
        // 如果能从请求中获取到token
        if (StringUtils.isNotBlank(tokenKey)) {
            String cacheKey = CacheEnum.TOKEN.getPrefix() + tokenKey;
            String tokenValue = redis.opsForValue().get(cacheKey);
            // 如果进一步能从缓存中拿到token对应的数据，就验证成功。
            if (StringUtils.isNotBlank(tokenValue)) {
                TokenDto tokenDto = JSON.parseObject(tokenValue, TokenDto.class);
                ProfileUtil.setUserProfile(request, tokenDto);
                return true;
            } else {
                // 如果token不能从缓存中转换为身份数据。
                throw new AuthException("token错误或已过期");
            }
        } else {
            // 如果请求中获取不到token。
            throw new AuthException("未提供token");
        }
    }
}
