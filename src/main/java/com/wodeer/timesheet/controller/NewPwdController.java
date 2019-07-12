package com.wodeer.timesheet.controller;

import com.wodeer.timesheet.entity.User;
import com.wodeer.timesheet.formobject.NewPwdFo;
import com.wodeer.timesheet.model.ApiResult;
import com.wodeer.timesheet.service.NewPwdService;
import com.wodeer.timesheet.util.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 修改密码
 * @author guoya
 */
@RestController
public class NewPwdController {

    @Autowired
    NewPwdService newPwdService;
    /**
     * 所有用户修改密码
     * @param fo   用户对象
     * @return ApiResult
     */
    @PostMapping("/newPassword")
    public ApiResult updatePwd(@RequestBody NewPwdFo fo){
        User user = new User();
        BeanUtils.copyProperties(fo, user);
        user.setPassword(MD5Util.encryption(fo.getPassword(), "098123"));
        newPwdService.updateById(user);
        return ApiResult.success();
    }
}
