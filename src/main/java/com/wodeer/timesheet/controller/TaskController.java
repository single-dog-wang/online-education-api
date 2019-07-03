package com.wodeer.timesheet.controller;

import com.wodeer.timesheet.entity.Task;
import com.wodeer.timesheet.entity.TaskDate;
import com.wodeer.timesheet.formobject.TaskSearchFo;
import com.wodeer.timesheet.model.ApiResult;
import com.wodeer.timesheet.service.TaskService;
import com.wodeer.timesheet.viewobject.PageVo;
import com.wodeer.timesheet.viewobject.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

/**
 * 工作日志 controller
 *
 * @author wuliming
 * @date 2019-07-01 10:32
 */
@Validated
@RestController
@RequestMapping(value = "/task", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TaskController {

    /**
     * the task service
     */
    @Autowired
    TaskService taskService;

    @GetMapping("/list")
    public ApiResult<PageVo<TaskVo<TaskDate>>> taskList(Integer currentPage, Integer pageSize) {
        // 先从token中获取用户id
        Integer userId = 8;
        return ApiResult.success(taskService.taskList(userId, currentPage, pageSize));
    }

    /**
     * 条件查询
     * 用户id不能为空，如果未选择默认为当前登录者用户id
     * 工作内容关键字可以为空，为空时查看以用户id和时间为条件的数据
     *                       不为空时加上关键字查询条件
     *
     * @param taskSearchFo 日志查询参数
     * @return the api result
     */
    @PostMapping("/search")
    public ApiResult<PageVo<TaskVo<TaskDate>>> search(@Valid @RequestBody TaskSearchFo taskSearchFo) throws ParseException {
        return ApiResult.success(taskService.searchByPage(taskSearchFo));
    }

    /**
     * 联想查询
     *
     * @param userId     用户id
     * @param keyContent 输入的日志内容
     * @return the api result
     */
    @GetMapping("/association-search")
    public ApiResult<List<Task>> associationSearch(Integer userId,
                                                   String keyContent) {
        return ApiResult.success(taskService.associationSearch(userId, keyContent));
    }
}
