package com.god.online_education.viewobject;

import com.god.online_education.entity.Demo;
import lombok.Data;

import java.util.Date;

/**
 * Demo记录对应的视图对象
 *
 * @author richard
 * @date 2019-06-21 15:17
 */
@Data
public class DemoVo {
    private Integer id;
    private String demoName;
    private Date theDate;

    /**
     * 从实体对象转换的构造函数
     * @param demo 实体对象
     */
    public DemoVo(Demo demo) {
        this.id = demo.getId();
        this.demoName = demo.getDemoName();
        this.theDate = demo.getTheDate();
    }
}
