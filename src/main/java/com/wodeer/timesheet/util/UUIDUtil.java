package com.wodeer.timesheet.util;

import java.util.UUID;

/**
 * @author guoya
 * 随机生成32位的字母和数字组成的数
 */
public class UUIDUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
