package com.wodeer.timesheet.formobject;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

/**
 * 日志查询参数
 *
 * @author wuliming
 * @date 2019-07-03 11:09
 */
@Data
public class TaskSearchFo {
    /**
     * 用户id
     */
    @Positive
    private Integer userId;
    /**
     * 日志内容
     */
    private String keyContent;
    /**
     * 开始时间
     */
    @NotEmpty
    private String startTime;
    /**
     * 结束时间
     */
    @NotEmpty
    private String endTime;
    /**
     * 当前页
     */
    @Positive
    private Integer currentPage;
    /**
     * 每页多少条
     */
    @Positive
    private Integer pageSize;
}
