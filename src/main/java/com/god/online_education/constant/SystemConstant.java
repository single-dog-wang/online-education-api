package com.god.online_education.constant;

/**
 * 系统通用的常量定义
 *
 * @author richard
 * @date 2019-06-21 07:56
 */
@SuppressWarnings("unused")
public class SystemConstant {
    /**
     * 有些操作是匿名的，没有 token 的情况下，
     * 数据库中的 created_by 和 update_by 也不能为空，
     * 就用 0 来填充，代表是被系统添加或修改的。
     */
    public  static final Integer SYS_USER_ID = 0;

    /**
     * 月份格式
     */
    public static final String MONTH_PATTERN = "yyyy-MM";

    /**
     * 日期格式
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间时间格式
     */
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 数据分隔符
     */
    public static final String VALUE_SEPARATOR = ",";
}
