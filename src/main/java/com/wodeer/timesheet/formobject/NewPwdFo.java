package com.wodeer.timesheet.formobject;

import lombok.Data;

/**
 * 创建修改所有用户密码的表单对象
 *
 * @author guoya
 */
@Data
public class NewPwdFo {
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 新密码
     */
    private String password;
}
