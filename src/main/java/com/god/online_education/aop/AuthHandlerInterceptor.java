package com.god.online_education.aop;

import com.alibaba.fastjson.JSON;
import com.god.online_education.dto.TokenDto;
import com.god.online_education.util.ProfileUtil;
import com.god.online_education.enums.CacheEnum;
import com.god.online_education.exception.AuthException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 身份认证拦截器
 * @author richard
 * @date 2019-06-21 20:04
 */
//@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    private static final String TOKEN_PARAM_KEY = "userId";
    private static final String ANONYMOUS_ACCESS_PREFIX = "/login/findUserByUP";
    private static final String ANONYMOUS_ACCESS_PREFIX2 = "/exportTask";
    private static final String DOWNLOAD_URL_PREFIX= "/file/download/";

    @Autowired
    private StringRedisTemplate redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 身份认证模块的请求不需要在这里截断做验证，直接放行
        if (request.getServletPath().startsWith(ANONYMOUS_ACCESS_PREFIX) || request.getServletPath().startsWith(ANONYMOUS_ACCESS_PREFIX2) ) {
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
