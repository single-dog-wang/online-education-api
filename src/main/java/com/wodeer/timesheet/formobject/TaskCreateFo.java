package com.wodeer.timesheet.formobject;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 创建日志记录的表单数据
 *
 * @author wuliming
 * @date 2019-07-03 17:14
 */
@Data
public class TaskCreateFo {
    /**
     * 日志内容
     */
    @NotEmpty
    private String content;
    /**
     * 日志时间
     */
    @NotNull
    private String workDate;
}
