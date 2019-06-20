package com.wodeer.timesheet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wodeer.timesheet.entity.Demo;
import com.wodeer.timesheet.formobject.DemoCreateFo;
import com.wodeer.timesheet.model.ApiResult;
import com.wodeer.timesheet.service.DemoService;
import com.wodeer.timesheet.util.RequestValidationUtil;
import com.wodeer.timesheet.viewobject.DemoVo;
import com.wodeer.timesheet.viewobject.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author richard
 * @date 2019-06-19 15:11
 */
@RestController
@RequestMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DemoController {
    @Autowired
    DemoService demoService;

    @GetMapping("/list")
    public ApiResult<List<DemoVo>> demoList() {
        List<Demo> allDemoList = demoService.list();
        List<DemoVo> result = allDemoList.stream().map(DemoVo::new).collect(Collectors.toList());
        return ApiResult.success(result);
    }

    @GetMapping("/query")
    public ApiResult<List<DemoVo>> queryDemo(@RequestParam String name, @RequestParam Integer maxId) {
        List<Demo> allDemoList = demoService.queryDemo(name, maxId);
        List<DemoVo> result = allDemoList.stream().map(DemoVo::new).collect(Collectors.toList());
        return ApiResult.success(result);
    }

    @GetMapping("/custom-query")
    public ApiResult<List<DemoVo>> customQueryDemo(@RequestParam Integer id) {
        List<Demo> allDemoList = demoService.customerDemo(id);
        List<DemoVo> result = allDemoList.stream().map(DemoVo::new).collect(Collectors.toList());
        return ApiResult.success(result);
    }

    @GetMapping("/query-by-page")
    public ApiResult<PageVo<DemoVo>> queryByPage(@RequestParam Integer currentPage, @RequestParam Integer pageSize) {

        IPage<Demo> pageObj = demoService.queryByPage(currentPage, pageSize);

        PageVo<DemoVo> result = new PageVo<>();
        result.setTotal(pageObj.getTotal());
        result.setPages(pageObj.getPages());
        result.setRecords(pageObj.getRecords().stream().map(DemoVo::new).collect(Collectors.toList()));

        return ApiResult.success(result);
    }

    @PostMapping("")
    public ApiResult createDemo(@Valid @RequestBody DemoCreateFo fo, BindingResult validation) {
        // 先验证参数
        RequestValidationUtil.checkForm(validation);

        // 调用 service 方法
        Demo demo = new Demo();
        demo.setDemoName(fo.getDemoName());
        demoService.save(demo);

        // 返回
        return ApiResult.success();
    }


}
