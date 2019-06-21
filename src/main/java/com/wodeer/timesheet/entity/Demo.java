package com.wodeer.timesheet.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author richard
 * @date 2019-06-19 15:03
 */
@Data
public class Demo {
    private Integer id;
    private String demoName;
    private Date theDate;
    private Date theTime;
}
