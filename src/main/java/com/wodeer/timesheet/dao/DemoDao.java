package com.wodeer.timesheet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wodeer.timesheet.entity.Demo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author richard
 * @date 2019-06-19 15:04
 */
public interface DemoDao extends BaseMapper<Demo> {
    List<Demo> customerQuery(@Param("id") Integer id);
}
