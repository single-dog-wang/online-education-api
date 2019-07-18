package com.wodeer.timesheet.viewobject;

import com.wodeer.timesheet.entity.Task;
import lombok.Data;

import java.util.Date;

/**
 * @author guoya
 */
@Data
public class TaskVo {
    /**
     * 工作ID
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 工作时间
     */
    private String workDate;
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
     * 从实体对象转换的构造函数
     * @param task 实体对象
     */
    public TaskVo(Task task) {
        this.id = task.getId();
        this.workContent = task.getWorkContent();
        this.workType = task.getWorkType();
        this.createTime = task.getCreateTime();
    }

    public TaskVo() {
    }
}
