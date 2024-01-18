package cn.buli_home.utils.common;

import java.util.Objects;

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
        return NumberUtils.parseInt(convert2String(obj));
    }


    public static long parseLong(Object obj) {
        return NumberUtils.parseLong(convert2String(obj));
    }

    public static Double parseDouble(Object obj) {
        return NumberUtils.parseDouble(convert2String(obj));
    }

    public static boolean parseBoolean(Object obj) {
        return StringUtils.parseBoolean(convert2String(obj));
    }

    /**
     * 如果给定对象为{@code null}返回默认值
     *
     * <pre>
     * ObjectUtil.defaultIfNull(null, null)      = null
     * ObjectUtil.defaultIfNull(null, "")        = ""
     * ObjectUtil.defaultIfNull(null, "zz")      = "zz"
     * ObjectUtil.defaultIfNull("abc", *)        = "abc"
     * ObjectUtil.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param <T>          对象类型
     * @param object       被检查对象，可能为{@code null}
     * @param defaultValue 被检查对象为{@code null}返回的默认值，可以为{@code null}
     * @return 被检查对象为{@code null}返回默认值，否则返回原值
     */
    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return Objects.isNull(object) ? defaultValue : object;
    }
}
