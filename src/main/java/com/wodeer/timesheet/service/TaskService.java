package com.wodeer.timesheet.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wodeer.timesheet.dao.TaskDao;
import com.wodeer.timesheet.entity.Task;
import com.wodeer.timesheet.entity.TaskDate;
import com.wodeer.timesheet.entity.User;
import com.wodeer.timesheet.formobject.TaskCreateFo;
import com.wodeer.timesheet.formobject.TaskSearchFo;
import com.wodeer.timesheet.formobject.TaskUpdateFo;
import com.wodeer.timesheet.viewobject.PageVo;
import com.wodeer.timesheet.viewobject.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author wuliming
 * @date 2019-07-01 10:32
 */
@Service
public class TaskService extends ServiceImpl<TaskDao, Task> {

    private static final String REDIS_KEY = "com.wodeer.timesheet.redis";

    @Autowired
    private TaskDateService taskDateService;

    @Autowired
    private RedisTemplate jsonRedisTemplate;

    @Autowired
    private HttpServletRequest request;

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
        if (null == (taskSearchFo.getStartTime()) || null == (taskSearchFo.getEndTime())) {
            result.setRecords(setData(pageObj.getRecords(), null, null));
        } else {
            result.setRecords(setData(pageObj.getRecords(), taskSearchFo.getStartTime(), taskSearchFo.getEndTime()));
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

    public PageVo<TaskVo<TaskDate>> taskList(Integer currentPage, Integer pageSize) {
        LambdaQueryWrapper<Task> lambdaQuery = Wrappers.lambdaQuery(new Task());
        lambdaQuery.eq(Task::getUserId, getUser().getId());
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

    public boolean add(TaskCreateFo taskCreateFo) {
        //保存task
        Task task = new Task();
        task.setUserId(getUser().getId());
        task.setWorkContent(taskCreateFo.getContent());
        task.setWorkType(taskCreateFo.getWorkType());
        task.setCreateTime(new Date());
        task.setUpdateTime(new Date());
        this.baseMapper.insert(task);
        // 判断时间是否保存成功，成功返回true 失败先删掉该日志内容再返回false
        if (saveTaskDate(task.getId(), taskCreateFo.getWorkDate())) {
            return true;
        } else {
            this.baseMapper.deleteById(task.getId());
            return false;
        }
    }

    public boolean quickAdd(Integer taskId, Date workDate) {
        return saveTaskDate(taskId, workDate);
    }


    public Integer modify(TaskUpdateFo taskUpdateFo) {
        Task task = new Task();
        task.setId(taskUpdateFo.getTaskId());
        task.setWorkContent(taskUpdateFo.getContent());
        task.setWorkType(taskUpdateFo.getWorkType());
        task.setUpdateTime(new Date());
        return this.baseMapper.updateById(task);
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
                //获取当前内容所有时间
                dateList = taskDateService.taskDateList(task.getId());
            }
            TaskVo<TaskDate> taskVo = new TaskVo<>(task);
            if (dateList.size() > 0) {
                taskVo.setDateList(dateList);
            }
            taskVo.setDateCount(dateList.size());
            result.add(taskVo);
        }
        return result;
    }

    /**
     * 保存时间
     *
     * @param taskId   工作内容id
     * @param workDate 工作时间
     * @return true or false
     */
    private boolean saveTaskDate(Integer taskId, Date workDate) {
        TaskDate taskDate = new TaskDate();
        taskDate.setTaskId(taskId);
        taskDate.setWorkDate(workDate);
        taskDate.setCreateTime(new Date());
        taskDate.setUpdateTime(new Date());
        return taskDateService.save(taskDate);
    }

    /**
     * 通过cookie获取用户信息
     *
     * @return 登录用户信息
     */
    @SuppressWarnings("unchecked")
    private User getUser() {
        Cookie[] cookies = request.getCookies();
        String token = "";
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }
        LinkedHashMap result = (LinkedHashMap) jsonRedisTemplate.opsForHash().get(REDIS_KEY, token);
        User user = new User();
        if (result != null) {
            user.setId((Integer) result.get("id"));
            user.setUsername((String) result.get("username"));
            user.setUserType((Integer) result.get("userType"));
            user.setLeaderId((Integer) result.get("leaderId"));
            user.setIsActive((Integer) result.get("isActive"));
        }
        return user;
    }
}
