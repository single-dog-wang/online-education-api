package com.wodeer.timesheet.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wodeer.timesheet.entity.Task;
import com.wodeer.timesheet.entity.User;
import com.wodeer.timesheet.viewobject.TaskVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author guoya
 */
@Mapper
public interface TaskDao extends BaseMapper<Task> {
    /**
     * 查询所有的工作相关的内容
     * @param  isActive 是否被激活
     * @return List<TaskVo>
     */
    List<TaskVo> queryAll(Integer isActive);

    /**
     * 工作内容比例的分母
     * @param  isActive 是否被激活
     * @return Long
     */
    @Select("select count(*) from task_date td, task t , user u where u.id = t.user_id and t.id = td.task_id and u.is_active=#{isActive}")
    Long queryDeno(Integer isActive);

    /**
     * 工作内容比例的分子
     * @param  isActive 是否被激活
     * @return Long
     */
    @Select("select count(*) from task_date td, task t, user u where u.id = t.user_id and t.id = td.task_id and u.is_active=#{isActive} group by td.task_id")
    List<Long> queryMole(Integer isActive);

    /**
     * 工作内容内容
     * @param  isActive 是否被激活
     * @return List<TaskVo>
     */
    @Select("select t.work_content, t.work_type from  task t, user u where  u.id = t.user_id and  u.is_active=#{isActive}")
    List<TaskVo> queryContent(Integer isActive);

    /**
     * 激活的用户名单
     * @param  isActive 是否被激活
     * @return List<User>
     */
    @Select("select username from user where is_active = #{isActive}")
    List<User> queryUser(Integer isActive);

}
