package com.wodeer.timesheet.formobject;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 创建日志记录的表单数据
 *
 * @author wuliming
 * @date 2019-07-03 17:14
 */
@Data
public class TaskCreateFo {
    /**
     * 工作内容
     */
    @NotEmpty
    private String content;
    /**
     * 工作时间
     */
    @NotNull
    private Date workDate;
    /**
     * 工作类型
     */
    @NotEmpty
    private String workType;
}
