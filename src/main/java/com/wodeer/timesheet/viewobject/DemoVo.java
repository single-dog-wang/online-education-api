package com.wodeer.timesheet.viewobject;

import com.wodeer.timesheet.entity.Demo;
import lombok.Data;

/**
 * @author richard
 * @date 2019-06-19 15:17
 */
@Data
public class DemoVo {
    private Integer id;
    private String demoName;

    public DemoVo(Demo demo) {
        this.id = demo.getId();
        this.demoName = demo.getDemoName();
    }
}
