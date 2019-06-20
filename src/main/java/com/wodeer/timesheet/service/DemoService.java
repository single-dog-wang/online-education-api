package com.wodeer.timesheet.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wodeer.timesheet.dao.DemoMapper;
import com.wodeer.timesheet.entity.Demo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author richard
 * @date 2019-06-19 15:18
 */
@Service
public class DemoService extends ServiceImpl<DemoMapper, Demo> {
    public List<Demo> queryDemo(String name, Integer maxId) {
        LambdaQueryWrapper<Demo> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.like(Demo::getDemoName, name)
                   .le(Demo::getId, maxId);
        return this.baseMapper.selectList(lambdaQuery);
    }

    public List<Demo> customerDemo(Integer id) {
        return this.baseMapper.customerQuery(id);
    }

    public IPage<Demo> queryByPage(int currentPage, int pageSize) {
        Page<Demo> page = new Page<>(currentPage, pageSize);
        return this.baseMapper.selectPage(page, null);
    }
}
