package com.wodeer.timesheet.entity;

import lombok.Data;

import java.util.Date;

/**
 * 工作内容实体类
 * @author guoya
 */
@Data
public class Task {
    /**
     * 工作ID
     */
    private Integer id;
    /**
     * 关联用户id
     */
    private Integer userId;
    /**
     * 工作内容
     */
    private String workContent;
    /**
     * 工作类型
     */
    private String workType;
    /**
     * 记录创建时间（仅仅是记录作用）
     */
    private Date createTime;
    /**
     * 记录更新的时间（仅仅是记录的作用）
     */
    private Date updateTime;
}
