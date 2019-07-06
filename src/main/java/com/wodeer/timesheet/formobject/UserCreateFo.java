package com.wodeer.timesheet.formobject;

import lombok.Data;
/**
 * 创建新增用户的表单对象
 *
 * @author guoya
 */
@Data
public class UserCreateFo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 领导id
     */
    private Integer leaderId;
    /**
     * 用户类型
     */
    private Integer userType;
}
