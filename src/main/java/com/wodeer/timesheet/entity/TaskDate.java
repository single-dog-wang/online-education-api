package com.wodeer.timesheet.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 工作日志时间实体类
 *
 * @author wuliming
 * @date 2019-07-01 10:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskDate extends BaseEntity {
    /**
     * 对应日志表id
     */
    private Integer taskId;
    /**
     * 工作日志时间
     */
    private Date workDate;
}
