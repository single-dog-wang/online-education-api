package com.wodeer.timesheet.service;

import com.wodeer.timesheet.dao.TaskDao;
import com.wodeer.timesheet.entity.User;
import com.wodeer.timesheet.viewobject.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guoya
 */
@Service
public class TaskService {

    @Autowired
    TaskDao taskDao;

    public List<TaskVo> queryAll(Integer isActive) {
       return taskDao.queryAll(isActive);
    }

    public Long queryDeno(Integer isActive) {
        return taskDao.queryDeno(isActive);
    }

    public List<Long> queryMole(Integer isActive) {
        return taskDao.queryMole(isActive);
    }

    public List<TaskVo> queryContent(Integer isActive) {
        return taskDao.queryContent(isActive);
    }

    public List<User> queryUser(Integer isActive) {
        return taskDao.queryUser(isActive);
    }
}
