package com.wodeer.timesheet.entity;

import lombok.Data;

/**
 * 接受表格数据的实体类
 * @author guoya
 */
@Data
public class ScoreInfo {
    /**
     * 学号
     */
    private String stuName;
    /**
     * 课程号
     */
    private String className;
    /**
     * 平时成绩
     */
    private String rscore;
    /**
     * 上机成绩
     */
    private String lscore;
}
