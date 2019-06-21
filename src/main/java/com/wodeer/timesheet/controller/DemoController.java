package com.wodeer.timesheet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wodeer.timesheet.constant.SystemConstant;
import com.wodeer.timesheet.entity.Demo;
import com.wodeer.timesheet.exception.InvalidApiParameterException;
import com.wodeer.timesheet.formobject.DemoCreateFo;
import com.wodeer.timesheet.formobject.DemoUpdateFo;
import com.wodeer.timesheet.model.ApiResult;
import com.wodeer.timesheet.service.DemoService;
import com.wodeer.timesheet.util.ProfileUtil;
import com.wodeer.timesheet.viewobject.DemoVo;
import com.wodeer.timesheet.viewobject.PageVo;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author richard
 * @date 2019-06-19 15:11
 */
@Validated
@RestController
@RequestMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DemoController {
    @Autowired
    DemoService demoService;
    @Autowired
    HttpServletRequest request;

    // 返回所有数据
    @GetMapping("/list")
    public ApiResult<List<DemoVo>> demoList() {
        List<Demo> allDemoList = demoService.list();
        List<DemoVo> result = allDemoList.stream().map(DemoVo::new).collect(Collectors.toList());
        return ApiResult.success(result);
    }

    // 普通条件查询
    @GetMapping("/query")
    public ApiResult<List<DemoVo>> queryDemo(@Valid @RequestParam @Length(max = 20) String name,
                                             @Valid @RequestParam @Positive Integer maxId) {
        List<Demo> allDemoList = demoService.queryDemo(name, maxId);
        List<DemoVo> result = allDemoList.stream().map(DemoVo::new).collect(Collectors.toList());
        return ApiResult.success(result);
    }

    // 自定义SQL查询
    @GetMapping("/custom-query")
    public ApiResult<List<DemoVo>> customQueryDemo(@Valid @RequestParam @Positive Integer id) {
        List<Demo> allDemoList = demoService.customerDemo(id);
        List<DemoVo> result = allDemoList.stream().map(DemoVo::new).collect(Collectors.toList());
        return ApiResult.success(result);
    }

    // 分页查询
    @GetMapping("/query-by-page")
    public ApiResult<PageVo<DemoVo>> queryByPage(@Valid @RequestParam @Positive Integer currentPage,
                                                 @Valid @RequestParam @Positive Integer pageSize) {

        IPage<Demo> pageObj = demoService.queryByPage(currentPage, pageSize);

        PageVo<DemoVo> result = new PageVo<>();
        result.setTotal(pageObj.getTotal());
        result.setPages(pageObj.getPages());
        result.setRecords(pageObj.getRecords().stream().map(DemoVo::new).collect(Collectors.toList()));

        return ApiResult.success(result);
    }

    // 新增
    @PostMapping("")
    public ApiResult createDemo(@Valid @RequestBody DemoCreateFo fo) {
        // 调用 service 方法
        Demo demo = new Demo();
        demo.setDemoName(fo.getDemoName());
        demoService.save(demo);

        // 返回
        return ApiResult.success();
    }

    // 更新(按照id更新)
    @PutMapping("/{id}")
    public ApiResult createDemo(@Valid @PathVariable @Positive Integer id,
                                @Valid @RequestBody DemoUpdateFo fo) {
        // 调用 service 方法
        Demo demo = new Demo();
        demo.setId(id);
        demo.setDemoName(fo.getDemoName());
        demoService.updateById(demo);

        // 返回
        return ApiResult.success();
    }

    // 更新(指定条件)
    @PutMapping("/modify")
    public ApiResult createDemo() {
        // 调用 service 方法
        demoService.modify();

        // 返回
        return ApiResult.success();
    }

    // 日期格式参数验证
    @GetMapping("/date-param/{demoDate}")
    public ApiResult<Long> dateParam(@PathVariable String demoDate) {
        Date theDate;
        long result;
        try {
            theDate = DateUtils.parseDate(demoDate, SystemConstant.DATE_PATTERN);
        } catch (ParseException e) {
            throw new InvalidApiParameterException("排班日期格式错误");
        }
        if (theDate.after(new Date())) {
            result = ProfileUtil.getUserId(request).longValue();
        } else {
            result = theDate.getTime();
        }
        return ApiResult.success(result);
    }
}
