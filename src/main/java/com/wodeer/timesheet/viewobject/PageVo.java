package com.wodeer.timesheet.viewobject;

import lombok.Data;

import java.util.List;

/**
 * @author richard
 * @date 2019-06-20 14:28
 */
@Data
public class PageVo<T> {
    // 总记录数
    private Long total;
    // 总页数
    private Long pages;
    // 当前页数据
    private List<T> records;
}
