package com.wodeer.timesheet.formobject;

import lombok.Data;

import javax.validation.constraints.Positive;
import java.util.Date;

/**
 * Task更新用的表单数据
 *
 * @author wuliming
 * @date 2019-07-04 13:40
 */
@Data
public class TaskUpdateFo {
    /**
     * 工作内容id
     */
    @Positive
    private Integer taskId;
    /**
     * 工作时间id
     */
    private Integer taskDateId;
    /**
     * 工作内容
     */
    private String content;
    /**
     * 工作时间
     */
    private Date workDate;

    /**
     * 工作类型
     */
    private String workType;
}
