package com.wodeer.timesheet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author richard
 * @date 2019-03-04 22:51
 */
@Data
@Component
@ConfigurationProperties("misc")
public class MiscConfig {
    private Boolean enableOutputUnknownException;
}
