package cn.buli_home.utils.common;

import java.util.Objects;

/**
 * 数字工具类
 *
 * @author mustard
 * @version 1.0
 * Create by 2022/9/29
 */
public class NumberUtils {

    /**
     * 数字转为布尔 (非零即真)
     */
    public static boolean parseBoolean(Number number) {
        if (Objects.isNull(number)) {
            return false;
        }

        return !number.equals(0);
    }



}
