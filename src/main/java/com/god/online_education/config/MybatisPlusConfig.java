package com.god.online_education.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus 的配置项
 *
 * @author richard
 * @date 2019-06-21 14:48
 */
@Configuration
@MapperScan("com.god.online_education.dao")
public class MybatisPlusConfig {
    /**
     * 开启分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}