package com.wodeer.timesheet.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wodeer.timesheet.dao.LoginDao;
import com.wodeer.timesheet.entity.User;
import com.wodeer.timesheet.model.ApiResult;
import com.wodeer.timesheet.util.Md5Util;
import com.wodeer.timesheet.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author guoya
 */
@Service
public class LoginService extends ServiceImpl<LoginDao, User> {
    private static final String REDIS_KEY = "com.wodeer.timesheet.redis";
    private  static final String SALT = "098123";

    @Autowired
    HttpServletResponse response;

    @Autowired
    RedisTemplate jsonRedisTemplate;

    /**
     * 根据username和password来查询userType
     */
    @SuppressWarnings("unchecked")
    public ApiResult<User> findUserByUserNameAndPassword(String username, String password) {
        User user = this.baseMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
                        .eq(User::getPassword, Md5Util.encryption(password, SALT))
        );
        String token = UUIDUtil.getUuid();
            jsonRedisTemplate.opsForHash().put(REDIS_KEY, token, user);
            Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(36000);
            cookie.setPath("/");
            response.addCookie(cookie);

            return ApiResult.success(user);
    }
}
