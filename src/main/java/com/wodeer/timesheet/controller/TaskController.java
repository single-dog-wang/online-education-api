package com.wodeer.timesheet.controller;

import com.wodeer.timesheet.entity.Task;
import com.wodeer.timesheet.entity.TaskDate;
import com.wodeer.timesheet.enums.CommonErrorEnum;
import com.wodeer.timesheet.formobject.TaskCreateFo;
import com.wodeer.timesheet.formobject.TaskSearchFo;
import com.wodeer.timesheet.formobject.TaskUpdateFo;
import com.wodeer.timesheet.model.ApiResult;
import com.wodeer.timesheet.service.TaskService;
import com.wodeer.timesheet.viewobject.PageVo;
import com.wodeer.timesheet.viewobject.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    /**
     * 分页查询列表
     *
     * @param currentPage 当前页
     * @param pageSize    每页多少条
     * @return the api result
     */
    @GetMapping("/list")
    public ApiResult<PageVo<TaskVo<TaskDate>>> taskList(Integer currentPage, Integer pageSize) {
        // 先获取用户id
        Integer userId = 8;
        return ApiResult.success(taskService.taskList(userId, currentPage, pageSize));
    }

    /**
     * 添加日志
     *
     * @param taskCreateFo 添加日志的内容
     * @return the api result
     */
    @PostMapping("/add")
    public ApiResult add(@Valid @RequestBody TaskCreateFo taskCreateFo) {
        Integer userId = 8;
        if (taskService.add(userId, taskCreateFo)) {
            return ApiResult.success();
        } else {
            return ApiResult.fail(CommonErrorEnum.SAVE_FAILURE);
        }
    }

    /**
     * 快速添加日志
     *
     * @param taskUpdateFo 接受工作内容id和时间
     * @return the adi result
     */
    @PostMapping("/quick-add")
    public ApiResult quickAdd(@Valid @RequestBody TaskUpdateFo taskUpdateFo) {
        if (taskService.quickAdd(taskUpdateFo.getTaskId(), taskUpdateFo.getWorkDate())) {
            return ApiResult.success();
        } else {
            return ApiResult.fail(CommonErrorEnum.SAVE_FAILURE);
        }
    }

    /**
     * 条件查询
     * 用户id不能为空，如果未选择默认为当前登录者用户id
     * 工作内容关键字可以为空，为空时查看以用户id和时间为条件的数据
     * 不为空时加上关键字查询条件
     *
     * @param taskSearchFo 日志查询参数
     * @return the api result
     */
    @PostMapping("/search")
    public ApiResult<PageVo<TaskVo<TaskDate>>> search(@Valid @RequestBody TaskSearchFo taskSearchFo) {
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

    /**
     * 更新日志
     *
     * @param taskUpdateFo 更新工作日志的内容
     * @return the api result
     */
    @PutMapping("")
    public ApiResult modify(@Valid @RequestBody TaskUpdateFo taskUpdateFo) {
        if(taskService.modify(taskUpdateFo) > 0){
            return ApiResult.success();
        }else {
            return ApiResult.fail(CommonErrorEnum.UPDATE_NOT_FOUND);
        }
    }

    /**
     * 根据日志内容id删除
     *
     * @param id 日志内容id
     * @return the api result
     */
    @DeleteMapping("")
    public ApiResult delete(Integer id) {
        if (taskService.deleteTask(id) > 0) {
            return ApiResult.success();
        } else {
            return ApiResult.fail(CommonErrorEnum.DELETE_NOT_FOUND);
        }
    }
}
