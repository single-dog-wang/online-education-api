package com.god.online_education.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

/**
 * 讲师管理模块
 * @author guoya
 * @version 1.0
 * @date 2020/5/18 7:19
 */
@Data
public class EduTeacher {
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;
    private String name;
    private String intro;
    private String career;
    private Integer level;
    private String avatar;
    private Integer sort;
    private Boolean isDeleted;
    private Date gmtCreate;
    private Date gmtModified;
}
