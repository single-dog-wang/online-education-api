package com.wodeer.timesheet.model;

/**
 * 错误信息模型
 *
 * @author richard
 * @date 2019-06-21 23:48
 */
public interface ErrorInfo {
    /**
     * 返回错误编码
     * @return 错误编码
     */
    Integer getCode();

    /**
     * 返回错误消息
     * @return 错误编码
     */
    String getMsg();
}
