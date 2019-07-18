package com.wodeer.timesheet.controller;

import com.wodeer.timesheet.entity.User;
import com.wodeer.timesheet.service.TaskService;
import com.wodeer.timesheet.viewobject.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author guoya
 */
@RestController
public class TaskController {

    @Autowired
    TaskService taskService;
    /**
     * 工作日志列表
     */
    @GetMapping("/list")
    public List<TaskVo> queryAll(Integer isActive){
            return taskService.queryAll(isActive);
    }

    /**
     * 工作内容比例的分母
     */
    @GetMapping("/deno")
    public Long queryDeno(Integer isActive){
        return taskService.queryDeno(isActive);
    }

    /**
     * 工作内容比例的分子
     */
    @GetMapping("/mole")
    public List<Long> queryMole(Integer isActive){
        return taskService.queryMole(isActive);
    }

    /**
     * 工作日志内容
     */
    @GetMapping("/content")
    public List<TaskVo> queryContent(Integer isActive){
        return taskService.queryContent(isActive);
    }

    /**
     * 激活的用户
     */
    @GetMapping("/user")
    public List<User> queryUser(Integer isActive){
        return taskService.queryUser(isActive);
    }

}
