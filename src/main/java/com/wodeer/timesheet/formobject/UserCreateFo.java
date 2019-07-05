package com.wodeer.timesheet.formobject;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 创建Demo记录的表单对象
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
//    /**
//     * 是否启用
//     */
//    private Integer isActive;
}
