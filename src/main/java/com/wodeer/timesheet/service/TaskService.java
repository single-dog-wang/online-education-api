package com.wodeer.timesheet.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wodeer.timesheet.constant.SystemConstant;
import com.wodeer.timesheet.dao.TaskDao;
import com.wodeer.timesheet.entity.Task;
import com.wodeer.timesheet.entity.TaskDate;
import com.wodeer.timesheet.exception.InvalidApiParameterException;
import com.wodeer.timesheet.formobject.TaskCreateFo;
import com.wodeer.timesheet.formobject.TaskSearchFo;
import com.wodeer.timesheet.formobject.TaskUpdateFo;
import com.wodeer.timesheet.viewobject.PageVo;
import com.wodeer.timesheet.viewobject.TaskVo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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

    public PageVo<TaskVo<TaskDate>> searchByPage(TaskSearchFo taskSearchFo) {

        LambdaQueryWrapper<Task> lambdaQuery = Wrappers.lambdaQuery(new Task());
        //判断输入的日志内容是否为空
        if ("".equals(taskSearchFo.getKeyContent())) {
            //空表示通过用户id获取所有日志内容
            lambdaQuery.eq(Task::getUserId, taskSearchFo.getUserId());
        } else {
            lambdaQuery.eq(Task::getUserId, taskSearchFo.getUserId())
                    .like(Task::getWorkContent, taskSearchFo.getKeyContent());
        }
        IPage<Task> pageObj = this.baseMapper.selectPage(new Page<>(taskSearchFo.getCurrentPage(), taskSearchFo.getPageSize()), lambdaQuery);

        PageVo<TaskVo<TaskDate>> result = new PageVo<>();

        result.setTotal(pageObj.getTotal());
        result.setPages(pageObj.getPages());
        //判断时间是否为空
        if ("".equals(taskSearchFo.getStartTime()) || "".equals(taskSearchFo.getEndTime())) {
            result.setRecords(setData(pageObj.getRecords(), null, null));
        } else {
            try {
                result.setRecords(setData(pageObj.getRecords(),
                        DateUtils.parseDate(taskSearchFo.getStartTime(), SystemConstant.DATETIME_PATTERN),
                        DateUtils.parseDate(taskSearchFo.getEndTime(), SystemConstant.DATETIME_PATTERN)));
            } catch (ParseException e) {
                throw new InvalidApiParameterException("日期格式错误");
            }
        }
        return result;
    }

    /**
     * 给每一个日志内容绑定对应所有时间
     *
     * @param taskList  日志内容列表
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 包含时间列表的日志列表
     */
    private List<TaskVo<TaskDate>> setData(List<Task> taskList, Date startTime, Date endTime) {
        List<TaskVo<TaskDate>> result = new ArrayList<>();
        //遍历 taskList
        for (Task task : taskList) {
            List<TaskDate> dateList;
            if (null != startTime && null != endTime) {
                //根据taskId和时间条件获取对应的日志列表
                dateList = taskDateService.taskDateList(task.getId(), startTime, endTime);
            } else {
                //获取当前用户的所有日志列表
                dateList = taskDateService.taskDateList(task.getId());
            }
            TaskVo<TaskDate> taskVo = new TaskVo<>(task);
            taskVo.setDateList(dateList);
            taskVo.setDateCount(dateList.size());
            result.add(taskVo);
        }
        return result;
    }

    public List<Task> associationSearch(Integer userId, String keyContent) {
        LambdaQueryWrapper<Task> lambdaQuery = Wrappers.lambdaQuery(new Task());
        lambdaQuery.eq(Task::getUserId, userId)
                .like(Task::getWorkContent, keyContent)
                .last("order by id desc");
        return this.baseMapper.selectList(lambdaQuery);
    }

    public PageVo<TaskVo<TaskDate>> taskList(Integer userId, Integer currentPage, Integer pageSize) {
        LambdaQueryWrapper<Task> lambdaQuery = Wrappers.lambdaQuery(new Task());
        lambdaQuery.eq(Task::getUserId, userId);
        IPage<Task> pageObj = this.baseMapper.selectPage(new Page<>(currentPage, pageSize), lambdaQuery);
        PageVo<TaskVo<TaskDate>> result = new PageVo<>();
        result.setPages(pageObj.getPages());
        result.setTotal(pageObj.getTotal());
        result.setRecords(setData(pageObj.getRecords(), null, null));
        return result;
    }

    public Integer deleteTask(Integer id) {
        //先删除该日志内容id对应的所有时间
        taskDateService.delete(id);
        return this.baseMapper.deleteById(id);

    }

    public boolean add(Integer userId, TaskCreateFo taskCreateFo) {
        //保存task
        Task task = new Task();
        task.setUserId(userId);
        task.setWorkContent(taskCreateFo.getContent());
        task.setCreateTime(new Date());
        task.setUpdateTime(new Date());
        this.baseMapper.insert(task);
        //保存时间
        TaskDate taskDate = new TaskDate();
        taskDate.setTaskId(task.getId());
        try {
            taskDate.setWorkDate(DateUtils.parseDate(taskCreateFo.getWorkDate(), SystemConstant.DATETIME_PATTERN));
        } catch (ParseException e) {
            deleteTask(task.getId());
            throw new InvalidApiParameterException("日期格式错误");
        }
        taskDate.setCreateTime(new Date());
        taskDate.setUpdateTime(new Date());
        return taskDateService.save(taskDate);
    }

    public boolean quickAdd(Integer id) {
        TaskDate taskDate = new TaskDate();
        taskDate.setTaskId(id);
        taskDate.setWorkDate(new Date());
        taskDate.setCreateTime(new Date());
        taskDate.setUpdateTime(new Date());
        return taskDateService.save(taskDate);
    }

    public Integer modify(TaskUpdateFo taskUpdateFo) {
        TaskDate taskDate = new TaskDate();
        taskDate.setId(taskUpdateFo.getTaskDateId());
        try {
            taskDate.setWorkDate(DateUtils.parseDate(taskUpdateFo.getWorkDate(), SystemConstant.DATETIME_PATTERN));
        } catch (ParseException e) {
            throw new InvalidApiParameterException("日期格式错误");
        }
        taskDate.setUpdateTime(new Date());
        taskDateService.updateById(taskDate);
        Task task = new Task();
        task.setId(taskUpdateFo.getTaskId());
        task.setWorkContent(taskUpdateFo.getContent());
        task.setUpdateTime(new Date());
        return this.baseMapper.updateById(task);
    }
}
