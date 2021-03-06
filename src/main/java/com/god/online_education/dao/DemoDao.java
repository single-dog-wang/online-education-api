package com.god.online_education.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.god.online_education.entity.Demo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Demo模块的数据访问类
 *
 * @author richard
 * @date 2019-06-21 15:04
 */
@Repository
public interface DemoDao extends BaseMapper<Demo> {
    /**
     * 这是一个自定义sql查询的例子
     *
     * @param id 例子参数
     * @return 实体列表
     */
    List<Demo> customerQuery(@Param("id") Integer id);
}
