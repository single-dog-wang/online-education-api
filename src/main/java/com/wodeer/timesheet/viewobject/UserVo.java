package com.wodeer.timesheet.viewobject;

import com.wodeer.timesheet.entity.User;
import lombok.Data;

import java.util.Date;

/**
 * 记录User对应的视图对象
 *
 * @author guoya
 */
@Data
public class UserVo {
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
     * 从实体对象转换的构造函数
     *
     * @param user 实体对象
     */
    public UserVo(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.leaderId = user.getLeaderId();
        this.userType = user.getUserType();
        this.isActive = user.getIsActive();
        this.createTime = user.getCreateTime();
    }

    public UserVo() {
    }
}
