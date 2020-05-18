package com.god.online_education.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 服务端用户身份凭证存储的数据结构
 *
 * @author richard
 * @date 2019-06-21 18:25
 */
@Data
@AllArgsConstructor
public class TokenDto {
    private Integer userId;
    private Integer orgId;
    private Integer staffId;
}
