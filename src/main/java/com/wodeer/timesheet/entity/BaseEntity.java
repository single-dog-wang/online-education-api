package com.wodeer.timesheet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wodeer.timesheet.constant.SystemConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体基类
 *
 * @author wuliming
 * @date 2019-07-01 10:32
 */
@Data
class BaseEntity implements Serializable {

    private static final long serialVersionUID = 7387442803249541892L;
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 改记录创建时间
     */
    private Date createTime;
    /**
     * 改记录更新时间
     */
    @JsonFormat(pattern = SystemConstant.DATETIME_PATTERN)
    private Date updateTime;
}
