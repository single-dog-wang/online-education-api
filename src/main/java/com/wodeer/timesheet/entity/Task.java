package com.wodeer.timesheet.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * task 实体类
 *
 * @author wuliming
 * @date 2019-07-01 10:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Task extends BaseEntity {
    /**
     * 关联用户id
     */
    private Integer userId;
    /**
     * 工作日志内容
     */
    private String workContent;
}
