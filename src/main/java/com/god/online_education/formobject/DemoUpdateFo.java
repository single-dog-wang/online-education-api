package com.god.online_education.formobject;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 更新Demo记录用的表单对象
 *
 * @author richard
 * @date 2019-06-21 15:56
 */
@Data
public class DemoUpdateFo {
    /**
     * 名称
     */
    @NotEmpty
    @Length(max = 20)
    private String demoName;

    /**
     * 测试日期字段
     */
    @NotNull
    private Date theDate;
}
