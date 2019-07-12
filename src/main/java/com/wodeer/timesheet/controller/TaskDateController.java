package com.wodeer.timesheet.controller;

import com.wodeer.timesheet.entity.TaskDate;
import com.wodeer.timesheet.enums.CommonErrorEnum;
import com.wodeer.timesheet.model.ApiResult;
import com.wodeer.timesheet.service.TaskDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuliming
 * @date 2019-07-10 11:08
 */
@Validated
@RestController
@RequestMapping(value = "/task/date", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TaskDateController {

    @Autowired
    private TaskDateService taskDateService;

    @PutMapping("")
    public ApiResult update(@RequestBody TaskDate taskDate) {
        if (taskDateService.updateById(taskDate)) {
            return ApiResult.success();
        } else {
            return ApiResult.fail(CommonErrorEnum.UPDATE_NOT_FOUND);
        }
    }

    @DeleteMapping("")
    public ApiResult delete(Integer id) {
        if (taskDateService.removeById(id)) {
            return ApiResult.success();
        } else {
            return ApiResult.fail(CommonErrorEnum.DELETE_NOT_FOUND);
        }
    }
}
