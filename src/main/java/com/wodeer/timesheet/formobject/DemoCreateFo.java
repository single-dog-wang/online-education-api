package com.wodeer.timesheet.formobject;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author richard
 * @date 2019-06-19 15:56
 */
@Data
public class DemoCreateFo {
    @NotEmpty(message = "名称不能为空")
    @Size(max = 20, message = "名称过长")
    private String demoName;
}
