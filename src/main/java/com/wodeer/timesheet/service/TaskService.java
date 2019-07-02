package com.wodeer.timesheet.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wodeer.timesheet.dao.TaskDao;
import com.wodeer.timesheet.entity.Task;
import com.wodeer.timesheet.entity.TaskDate;
import com.wodeer.timesheet.viewobject.PageVo;
import com.wodeer.timesheet.viewobject.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wuliming
 * @date 2019-07-01 10:32
 */
@Service
public class TaskService extends ServiceImpl<TaskDao, Task> {

    @Autowired
    private TaskDateService taskDateService;

    public PageVo<TaskVo<TaskDate>> searchByPage(Integer userId,
                                                 String keyContent,
                                                 Date startTime,
                                                 Date endTime,
                                                 Integer currentPage,
                                                 Integer pageSize) {
        LambdaQueryWrapper<Task> lambdaQuery = new LambdaQueryWrapper<>();
        //判断输入的日志内容是否为空
        if ("".equals(keyContent)) {
            //空表示通过用户id获取所有日志内容
            lambdaQuery.eq(Task::getUserId, userId);
        } else {
            lambdaQuery.eq(Task::getUserId, userId)
                    .like(Task::getWorkContent, keyContent);
        }
        IPage<Task> pageObj = this.baseMapper.selectPage(new Page<>(currentPage, pageSize), lambdaQuery);

        PageVo<TaskVo<TaskDate>> result = new PageVo<>();

        result.setTotal(pageObj.getTotal());
        result.setPages(pageObj.getPages());
        result.setRecords(setData(pageObj.getRecords(), startTime, endTime));

        return result;
    }

    private List<TaskVo<TaskDate>> setData(List<Task> taskList,
                                           Date startTime,
                                           Date endTime) {
        List<TaskVo<TaskDate>> result = new ArrayList<>();
        //遍历 taskList
        for (Task task : taskList) {
            //根据taskId和时间条件获取对应的日志时间列表
            List<TaskDate> dateList = taskDateService.taskDateList(task.getId(), startTime, endTime);
            TaskVo<TaskDate> taskVo = new TaskVo<>(task);
            taskVo.setDateList(dateList);
            taskVo.setDateCount(dateList.size());
            result.add(taskVo);
        }

        return result;
    }

    public List<Task> associationSearch(Integer userId, String keyContent) {
        LambdaQueryWrapper<Task> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.eq(Task::getUserId, userId)
                .like(Task::getWorkContent, keyContent)
                .last("order by id desc");
        return this.baseMapper.selectList(lambdaQuery);
    }
}
