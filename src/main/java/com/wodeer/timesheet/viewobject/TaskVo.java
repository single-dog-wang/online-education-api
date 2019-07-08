package com.wodeer.timesheet.viewobject;

import com.wodeer.timesheet.entity.Task;
import lombok.Data;

import java.util.List;


/**
 * @author wuliming
 * @date 2019-07-01 10:32
 */
@Data
public class TaskVo<T> {
    private Integer id;
    private Integer userId;
    private String workContent;
    private String workType;
    /**
     * 日期数量
     */
    private Integer dateCount;
    /**
     * 日期列表
     */
    private List<T> dateList;

    public TaskVo(Task task) {
        this.id = task.getId();
        this.userId = task.getUserId();
        this.workContent = task.getWorkContent();
        this.workType = task.getWorkType();
    }
}
