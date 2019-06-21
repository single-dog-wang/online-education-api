package com.wodeer.timesheet.entity;

import lombok.Data;

import java.util.Date;

/**
 * Demo 实体类
 *
 * @author richard
 * @date 2019-06-21 15:03
 */
@Data
public class Demo {
    /**
     * 自增长 id
     */
    private Integer id;
    /**
     * 名称
     */
    private String demoName;
    /**
     * 日期类型字段
     */
    private Date theDate;
    /**
     * 时间类型字段
     */
    private Date theTime;
}
