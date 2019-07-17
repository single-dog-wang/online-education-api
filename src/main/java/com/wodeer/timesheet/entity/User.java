package com.wodeer.timesheet.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 * @author guoya
 */
@Data
public class User implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;
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
    /**
     * 记录创建时间（仅仅是记录作用）
     */
    private Date createTime;
    /**
     * 记录更新的时间（仅仅是记录的作用）
     */
    private Date updateTime;
}
