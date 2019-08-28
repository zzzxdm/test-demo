package com.zzz.gateway.utils;

import org.apache.commons.lang3.RegExUtils;

/**
 * @author ZZZ
 * @version 1.0
 * @date 2019/8/28 0028
 */
public class StringUtil {

    /**
     * 替换空格
     * @param str
     * @return
     */
    public static String replaceSpace(String str) {
        return RegExUtils.replaceAll(str, "\\s", "");
    }
}
