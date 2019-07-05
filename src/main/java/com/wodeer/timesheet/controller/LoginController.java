package com.wodeer.timesheet.controller;

import com.wodeer.timesheet.entity.User;
import com.wodeer.timesheet.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guoya
 */
@RestController
@RequestMapping("/login")
public class LoginController{
    @Autowired
    LoginService loginService;

    /**
     * 根据username和password，查询用户的userType
     */
    @PostMapping("/findUserByUP")
    public User findUserByUserNameAndPassword(String username, String password) {
        return loginService.findUserByUserNameAndPassword(username, password);
    }
}
