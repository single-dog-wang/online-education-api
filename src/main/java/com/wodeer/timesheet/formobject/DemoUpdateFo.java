package com.wodeer.timesheet.formobject;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author richard
 * @date 2019-06-19 15:56
 */
@Data
public class DemoUpdateFo {
    @NotEmpty
    @Length(max = 20)
    private String demoName;

    @NotNull
    private Date theDate;
}
