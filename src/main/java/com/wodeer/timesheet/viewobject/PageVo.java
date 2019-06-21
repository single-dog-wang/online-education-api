package com.wodeer.timesheet.viewobject;

import lombok.Data;

import java.util.List;

/**
 * 通用的返回分页视图对象(view object)结构
 *
 * @param <T> 要返回的列表记录类型
 * @author richard
 * @date 2019-06-21 14:28
 */
@Data
public class PageVo<T> {
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 总页数
     */
    private Long pages;
    /**
     * 当前页数据
     */
    private List<T> records;
}
