package com.wodeer.timesheet.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wodeer.timesheet.dao.TaskDateDao;
import com.wodeer.timesheet.entity.TaskDate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author wuliming
 * @date 2019-07-02 14:39
 */
@Service
public class TaskDateService extends ServiceImpl<TaskDateDao, TaskDate> {

    public List<TaskDate> taskDateList(Integer taskId, Date startTime, Date endTime) {
        LambdaQueryWrapper<TaskDate> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.eq(TaskDate::getTaskId, taskId)
                .ge(TaskDate::getWorkDate, startTime)
                .le(TaskDate::getWorkDate, endTime);
        return this.baseMapper.selectList(lambdaQuery);
    }

    public List<TaskDate> taskDateList(Integer taskId) {
        LambdaQueryWrapper<TaskDate> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.eq(TaskDate::getTaskId, taskId);
        return this.baseMapper.selectList(lambdaQuery);
    }
}
