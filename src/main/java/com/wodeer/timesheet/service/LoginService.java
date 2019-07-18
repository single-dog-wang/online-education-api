package com.wodeer.timesheet.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wodeer.timesheet.dao.LoginDao;
import com.wodeer.timesheet.entity.User;
import com.wodeer.timesheet.enums.CommonErrorEnum;
import com.wodeer.timesheet.model.ApiResult;
import com.wodeer.timesheet.util.Md5Util;
import com.wodeer.timesheet.viewobject.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;

/**
 * @author guoya
 */
@Service
public class LoginService extends ServiceImpl<LoginDao, User> {
    private  static final String SALT = "098123";

    @Autowired
    HttpServletResponse response;

    @Autowired
    StringRedisTemplate redis;

    /**
     * 根据username和password来查询userType
     */
    public ApiResult findUserByUserNameAndPassword(String username, String password) {
        User user = this.baseMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
                        .eq(User::getPassword, Md5Util.encryption(password, SALT))
        );
        if(user != null){
            UserVo userVo = new UserVo(user);
            String token = user.getId().toString();
            userVo.setToken(token);
            redis.opsForValue().set("token:"+token, token);
            return ApiResult.success(userVo);
        }else{
            return  ApiResult.fail(CommonErrorEnum. LOGIN_FAILURE);
        }
    }
}
