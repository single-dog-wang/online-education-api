package com.wodeer.timesheet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wodeer.timesheet.entity.User;
import com.wodeer.timesheet.formobject.UserCreateFo;
import com.wodeer.timesheet.formobject.UserUpdateFo;
import com.wodeer.timesheet.model.ApiResult;
import com.wodeer.timesheet.service.UserService;
import com.wodeer.timesheet.util.MD5Util;
import com.wodeer.timesheet.viewobject.PageVo;
import com.wodeer.timesheet.viewobject.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 参数一：currentPage 当前页面
     * 参数二：pageSize    当前页面的总条数
     * 返回值：UserVo的列表
     */
    @GetMapping("/list")
    public ApiResult<List<UserVo>> queryByPage(Integer currentPage, Integer pageSize){
        IPage<User> pageObj = userService.queryByPage(currentPage, pageSize);
        //抽离分页查询得到的三个结果
        PageVo<UserVo> result = new PageVo<>();
        result.setTotal(pageObj.getTotal());
        result.setPages(pageObj.getPages());
        result.setRecords(pageObj.getRecords().stream().map(UserVo::new).collect(Collectors.toList()));
        return ApiResult.success(result.getRecords());
    }

    /**
     * 关键字查询
     * 参数：username  用户名
     * 返回值：UserVo  用户对象
     */
    @GetMapping("/search")
    public ApiResult<UserVo> queryByUsername(String username){
        UserVo userVo = new UserVo();
        User user = userService.queryByUsername(username);
        BeanUtils.copyProperties(user, userVo);
        return  ApiResult.success(userVo);
    }

    /**
     * 新增用户
     * 参数一：username  用户名
     * 参数二：password  密码
     * 参数三：leaderId  领导id
     * 参数四：userType  角色 0 普通用户 1 领导 2管理员（唯一）
     * 参数五: isActive  是否激活  1表示激活  0表示未激活
     * 返回值：无
     */
    @PostMapping("/add")
     public ApiResult createUser(@RequestBody UserCreateFo fo){
         User user = new User();
         BeanUtils.copyProperties(fo, user);
         user.setPassword(MD5Util.encryption(fo.getPassword(), "098123"));
         user.setIsActive(1);
         user.setCreateTime(new Date());
         user.setUpdateTime(new Date());
         userService.save(user);
        return ApiResult.success();
      }


    /**
     * 用户编辑(修改)
     * 参数一：username  用户名
     * 参数二：leaderId  领导id
     * 参数三：userType  角色 0 普通用户 1 领导 2管理员（唯一）
     * 参数四: isActive  是否激活  1表示激活  0表示未激活
     *  返回值：无
     */
    @PutMapping("/{id}")
    public ApiResult updateUser(@RequestBody UserUpdateFo fo, @PathVariable Integer id){
        User user = new User();
        BeanUtils.copyProperties(fo, user);
        user.setId(id);
        userService.updateById(user);
        return ApiResult.success();
    }
}
