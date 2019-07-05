package com.wodeer.timesheet.formobject;

import lombok.Data;

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
    private String workDate;
}
