package com.wodeer.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author richard
 * @date 2019-03-03 18:25
 * 服务端用户身份凭证存储的数据结构
 */
@Data
@AllArgsConstructor
public class TokenDto {
    private Integer userId;
    private Integer orgId;
    private Integer staffId;
}
