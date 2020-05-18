package com.god.online_education.aop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 这里是把 拦截器 注册入容器
 *
 * @author richard
 * @date 2019-06-21 20:56
 */
@Configuration
public class HandlerRegistry implements WebMvcConfigurer {
//    @Autowired
//    AuthHandlerInterceptor authHandlerInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authHandlerInterceptor);
//    }
}
