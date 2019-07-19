package com.wodeer.timesheet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wodeer.timesheet.entity.User;
import com.wodeer.timesheet.formobject.UserCreateFo;
import com.wodeer.timesheet.formobject.UserUpdateFo;
import com.wodeer.timesheet.model.ApiResult;
import com.wodeer.timesheet.service.UserService;
import com.wodeer.timesheet.util.Md5Util;
import com.wodeer.timesheet.viewobject.PageVo;
import com.wodeer.timesheet.viewobject.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guoya
 */
@RestController
@RequestMapping("/admin/employee")
public class UserController {
    @Autowired
    UserService userService;
    /*
     * IPage接口中有三个方法：
     * getTotal()   得到总条数
     * getPages()  总页数
     * List<T> getRecords()  得到我们分页查询对象的列表
     */

    /**
     * 分页查询所有用户
     * @param currentPage   当前页面
     * @param pageSize   当前页面的总条数
     * @return  ApiResult<List<UserVo>>
     */
    @GetMapping("/list")
    public ApiResult<List<UserVo>> queryByPage(Integer currentPage, Integer pageSize, Integer isActive){
        IPage<User> pageObj = userService.queryByPage(currentPage, pageSize, isActive);
        PageVo<UserVo> result = new PageVo<>();
        result.setRecords(pageObj.getRecords().stream().map(UserVo::new).collect(Collectors.toList()));
        return ApiResult.success(result.getRecords());
    }

    /**
     * 分页查询所有记录
     * @param currentPage   当前页面
     * @param pageSize   当前页面的总条数
     * @return  ApiResult<List<UserVo>>
     */
    @GetMapping("/total")
    public ApiResult<Long> queryByPageTotal(Integer currentPage, Integer pageSize, Integer isActive){
        IPage<User> pageObj = userService.queryByPageTotal(currentPage, pageSize, isActive);
        PageVo<UserVo> result = new PageVo<>();
        result.setTotal(pageObj.getTotal());
        return ApiResult.success(result.getTotal());
    }

    /**
     *关键字查询
     * @param username  用户名
     * @return  ApiResult<UserVo>
     */
    @GetMapping("/search")
    public ApiResult<List<UserVo>> queryByUsername(String username, Integer isActive){
        List<UserVo> userVos = new ArrayList<>();
        List<User> users = userService.queryByUsername(username, isActive);
        for (User user:users){
            UserVo userVO =new UserVo();
            BeanUtils.copyProperties(user,userVO);
            userVos.add(userVO);
        }
        return  ApiResult.success(userVos);
    }

    /**
     *新增用户
     * @param fo 用户表单对象
     * @return  ApiResult
     */
    @PostMapping("/add")
     public ApiResult createUser(@RequestBody UserCreateFo fo){
            User user = new User();
            BeanUtils.copyProperties(fo, user);
            user.setPassword(Md5Util.encryption(fo.getPassword(), "098123"));
            user.setIsActive(1);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            userService.save(user);
            return ApiResult.success();
      }


    /**
     * 用户编辑(修改)
     * @param fo   用户表单对象
     * @return ApiResult
     */
    @PostMapping("/update")
    public ApiResult updateUser(@RequestBody UserUpdateFo fo){
            User user = new User();
            BeanUtils.copyProperties(fo, user);
            userService.updateById(user);
            return ApiResult.success();
    }

    /**
     * 用户编辑(修改)
     * @param id  用户表id
     * @return ApiResult
     */
    @DeleteMapping("")
    public ApiResult deleteUser(Integer id, Integer isActive) {
            User user = new User();
            user.setId(id);
            user.setIsActive(isActive);
            userService.updateById(user);
            return ApiResult.success();
    }
}
