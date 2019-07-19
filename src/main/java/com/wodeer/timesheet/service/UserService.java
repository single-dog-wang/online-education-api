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

    public IPage<User> queryByPage(Integer currentPage, Integer pageSize, Integer isActive) {
        return this.baseMapper.selectPage(new Page<>(currentPage, pageSize),
                                          new LambdaQueryWrapper<User>().eq(User::getIsActive, isActive));
    }

    public List<User>  queryByUsername(String username, Integer isActive) {
          return this.baseMapper.selectList(new LambdaQueryWrapper<User>().like(User::getUsername, username).eq(User::getIsActive, isActive));
    }

    public IPage<User> queryByPageTotal(Integer currentPage, Integer pageSize, Integer isActive) {
        return this.baseMapper.selectPage(new Page<>(currentPage, pageSize),
                                          new LambdaQueryWrapper<User>().eq(User::getIsActive, isActive));
    }

    public IPage<User> queryByPageLeader(Integer currentPage, Integer pageSize, Integer isActive, Integer id) {
        return this.baseMapper.selectPage(new Page<>(currentPage, pageSize),
                new LambdaQueryWrapper<User>()
                         .eq(User::getIsActive, isActive).eq(User::getLeaderId, id));
    }

    public IPage<User> queryByPageTotalLeader(Integer currentPage, Integer pageSize, Integer isActive, String id) {
        return this.baseMapper.selectPage(new Page<>(currentPage, pageSize),
                new LambdaQueryWrapper<User>()
                        .eq(User::getIsActive, isActive).eq(User::getLeaderId, id));
       }

    public List<User> queryByUsernameLeader(String username, Integer isActive, String id) {
        return this.baseMapper.selectList(new LambdaQueryWrapper<User>().like(User::getUsername, username).eq(User::getIsActive, isActive).eq(User::getLeaderId, id));
    }
}
