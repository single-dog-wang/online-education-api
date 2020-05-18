package com.god.online_education.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.god.online_education.dao.DemoDao;
import com.god.online_education.entity.Demo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author richard
 * @date 2019-06-21 15:18
 */
@Service
public class DemoService extends ServiceImpl<DemoDao, Demo> {
    public IPage<Demo> findAll() {
        return this.baseMapper.selectPage(new Page<>(0, 5), null);
    }

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

    public void modify() {
        LambdaUpdateWrapper<Demo> lambdaUpdate = new LambdaUpdateWrapper<>();
        lambdaUpdate.eq(Demo::getId, 2)
                    .like(Demo::getDemoName, "B")
                    .set(Demo::getDemoName, "OK");
    }
}
