package com.wodeer.timesheet.controller;

import com.wodeer.timesheet.entity.Task;
import com.wodeer.timesheet.entity.TaskDate;
import com.wodeer.timesheet.model.ApiResult;
import com.wodeer.timesheet.service.TaskService;
import com.wodeer.timesheet.viewobject.PageVo;
import com.wodeer.timesheet.viewobject.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 工作日志 controller
 *
 * @author wuliming
 * @date 2019-07-01 10:32
 */
@RestController
@RequestMapping(value = "/task", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TaskController {

    /**
     * the task service
     */
    @Autowired
    TaskService taskService;

    /**
     * 条件查询
     * 用户id不能为空，如果未选择默认为当前登录者用户id
     * 工作内容关键字可以为空，为空时查看以用户id和时间为条件的数据
     * 不为空时加上关键字查询条件
     *
     * @param userId      用户id
     * @param keyContent  工作内容关键字
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param currentPage 当前页
     * @param pageSize    总页数
     * @return the api result
     */
    @PostMapping("/search")
    public ApiResult<PageVo<TaskVo<TaskDate>>> search(@Valid @RequestParam Integer userId,
                                                      @Valid @RequestParam String keyContent,
                                                      @Valid @RequestParam String startTime,
                                                      @Valid @RequestParam String endTime,
                                                      @Valid @RequestParam Integer currentPage,
                                                      @Valid @RequestParam Integer pageSize) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return ApiResult.success(taskService.searchByPage(userId, keyContent, format.parse(startTime), format.parse(endTime), currentPage, pageSize));
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
