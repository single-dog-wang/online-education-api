package com.wodeer.timesheet.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wuliming
 * @date 2019-07-08 9:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
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
    /**
     * 是否启用
     */
    private Integer isActive;
}
