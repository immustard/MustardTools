package cn.buli_home.utils.common;

import static cn.buli_home.utils.common.StringUtils.*;

/**
 *
 *
 * @author mustard
 * @version 1.0
 * Create by 2022/9/26
 */
public class ObjectUtils {
    public static int parseInt(Object obj) {
        return StringUtils.parseInt(convert2String(obj));
    }


    public static long parseLong(Object obj) {
        return StringUtils.parseLong(convert2String(obj));
    }

    public static Double parseDouble(Object obj) {
        return StringUtils.parseDouble(convert2String(obj));
    }

    public static boolean parseBoolean(Object obj) {
        return StringUtils.parseBoolean(convert2String(obj));
    }

}
