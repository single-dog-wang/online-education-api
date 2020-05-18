package com.god.online_education.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 一些不好分类的配置项杂项
 *
 * @author richard
 * @date 2019-06-21 22:51
 */
@Data
@Component
@ConfigurationProperties("misc")
public class MiscConfig {
    /**
     * 是否输出未知错误的详情
     */
    private Boolean enableOutputUnknownException;
}
