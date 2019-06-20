package com.wodeer.timesheet.job;

import com.wodeer.timesheet.enums.CacheEnum;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author richard
 * @date 2019-03-04 21:27
 * 为了保证和其他中间件保持连接，定期心跳连接
 */
@Component
public class HeartbeatJob {
    @Resource
    private StringRedisTemplate redis;

    // 每3分钟写一个心跳数据到redis
    @Scheduled(fixedDelay = 1000 * 60 * 3)
    void keepRedisConnection() {
        String cacheKey = CacheEnum.REDIS_HEARTBEAT.getPrefix() + SystemUtils.getHostName();
        redis.opsForValue().set(cacheKey, new Date().toString(), CacheEnum.REDIS_HEARTBEAT.getTimeout(),
                TimeUnit.SECONDS);
    }
}
