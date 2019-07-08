package com.wodeer.timesheet.formobject;

import lombok.Data;

/**
 * 更新记录User用的表单对象
 *
 * @author guoya
 */
@Data
public class UserUpdateFo {
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 领导id
     */
    private Integer leaderId;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 是否启用
     */
    private Integer isActive;
}
