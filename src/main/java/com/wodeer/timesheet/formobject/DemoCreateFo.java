package com.wodeer.timesheet.formobject;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 创建Demo记录的表单对象
 *
 * @author richard
 * @date 2019-06-21 15:56
 */
@Data
public class DemoCreateFo {
    /**
     * 名称
     */
    @NotEmpty
    @Length(max = 20)
    private String demoName;
}
