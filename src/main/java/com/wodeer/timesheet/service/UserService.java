package com.wodeer.timesheet.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wodeer.timesheet.dao.UserDao;
import com.wodeer.timesheet.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guoya
 */
@Service
public class UserService extends ServiceImpl<UserDao, User> {

    public IPage<User> queryByPage(Integer currentPage, Integer pageSize) {
        return this.baseMapper.selectPage(new Page<>(currentPage, pageSize), null);
    }

    public List<User>  queryByUsername(String username) {
          return this.baseMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }
}
