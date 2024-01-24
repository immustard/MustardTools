package cn.buli_home.utils.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
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
     * 检查是否为有效的数字<br>
     * 检查Double和Float是否为无限大，或者Not a Number<br>
     * 非数字类型和Null将返回false
     *
     * @param number 被检查类型
     * @return 检查结果，非数字类型和Null将返回false
     * 
     */
    public static boolean isValidNumber(Number number) {
        if (null == number) {
            return false;
        }
        if (number instanceof Double) {
            return (!((Double) number).isInfinite()) && (!((Double) number).isNaN());
        } else if (number instanceof Float) {
            return (!((Float) number).isInfinite()) && (!((Float) number).isNaN());
        }
        return true;
    }

    /**
     * 检查是否为有效的数字<br>
     * 检查double否为无限大，或者Not a Number（NaN）<br>
     *
     * @param number 被检查double
     * @return 检查结果
     * 
     */
    public static boolean isValid(double number) {
        return !(Double.isNaN(number) || Double.isInfinite(number));
    }

    /**
     * 检查是否为有效的数字<br>
     * 检查double否为无限大，或者Not a Number（NaN）<br>
     *
     * @param number 被检查double
     * @return 检查结果
     * 
     */
    public static boolean isValid(float number) {
        return !(Float.isNaN(number) || Float.isInfinite(number));
    }

    /**
     * 比较大小，值相等 返回true<br>
     * 此方法通过调用{@link Double#doubleToLongBits(double)}方法来判断是否相等<br>
     * 此方法判断值相等时忽略精度的，即0.00 == 0
     *
     * @param num1 数字1
     * @param num2 数字2
     * @return 是否相等
     * 
     */
    public static boolean equals(double num1, double num2) {
        return Double.doubleToLongBits(num1) == Double.doubleToLongBits(num2);
    }

    /**
     * 比较大小，值相等 返回true<br>
     * 此方法通过调用{@link Float#floatToIntBits(float)}方法来判断是否相等<br>
     * 此方法判断值相等时忽略精度的，即0.00 == 0
     *
     * @param num1 数字1
     * @param num2 数字2
     * @return 是否相等
     * 
     */
    public static boolean equals(float num1, float num2) {
        return Float.floatToIntBits(num1) == Float.floatToIntBits(num2);
    }

    /**
     * 比较大小，值相等 返回true<br>
     * 此方法修复传入long型数据由于没有本类型重载方法,导致数据精度丢失
     *
     * @param num1 数字1
     * @param num2 数字2
     * @return 是否相等
     * 
     */
    public static boolean equals(long num1, long num2) {
        return num1 == num2;
    }

    /**
     * 比较数字值是否相等，相等返回{@code true}<br>
     * 需要注意的是{@link BigDecimal}需要特殊处理<br>
     * BigDecimal使用compareTo方式判断，因为使用equals方法也判断小数位数，如2.0和2.00就不相等，<br>
     * 此方法判断值相等时忽略精度的，即0.00 == 0
     *
     * <ul>
     *     <li>如果用户提供两个Number都是{@link BigDecimal}，则通过调用{@link BigDecimal#compareTo(BigDecimal)}方法来判断是否相等</li>
     *     <li>其他情况调用{@link Number#equals(Object)}比较</li>
     * </ul>
     *
     * @param number1 数字1
     * @param number2 数字2
     * @return 是否相等
     * @see Objects#equals(Object, Object)
     * 
     */
    public static boolean equals(final Number number1, final Number number2) {
        if (number1 instanceof BigDecimal && number2 instanceof BigDecimal) {
            // BigDecimal使用compareTo方式判断，因为使用equals方法也判断小数位数，如2.0和2.00就不相等
            return equals((BigDecimal) number1, (BigDecimal) number2);
        }
        return Objects.equals(number1, number2);
    }

    /**
     * 比较大小，值相等 返回true<br>
     * 此方法通过调用{@link BigDecimal#compareTo(BigDecimal)}方法来判断是否相等<br>
     * 此方法判断值相等时忽略精度的，即0.00 == 0
     *
     * @param bigNum1 数字1
     * @param bigNum2 数字2
     * @return 是否相等
     */
    public static boolean equals(BigDecimal bigNum1, BigDecimal bigNum2) {
        //noinspection NumberEquality
        if (bigNum1 == bigNum2) {
            // 如果用户传入同一对象，省略compareTo以提高性能。
            return true;
        }
        if (bigNum1 == null || bigNum2 == null) {
            return false;
        }
        return 0 == bigNum1.compareTo(bigNum2);
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param v     值
     * @param scale 保留小数位数
     * @return 新值
     */
    public static BigDecimal round(double v, int scale) {
        return round(v, scale, RoundingMode.HALF_UP);
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param v     值
     * @param scale 保留小数位数
     * @return 新值
     */
    public static String roundStr(double v, int scale) {
        return round(v, scale).toPlainString();
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param numberStr 数字值的字符串表现形式
     * @param scale     保留小数位数
     * @return 新值
     */
    public static BigDecimal round(String numberStr, int scale) {
        return round(numberStr, scale, RoundingMode.HALF_UP);
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param number 数字值
     * @param scale  保留小数位数
     * @return 新值
     * 
     */
    public static BigDecimal round(BigDecimal number, int scale) {
        return round(number, scale, RoundingMode.HALF_UP);
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param numberStr 数字值的字符串表现形式
     * @param scale     保留小数位数
     * @return 新值
     * 
     */
    public static String roundStr(String numberStr, int scale) {
        return round(numberStr, scale).toPlainString();
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param v            值
     * @param scale        保留小数位数
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 新值
     */
    public static BigDecimal round(double v, int scale, RoundingMode roundingMode) {
        return round(Double.toString(v), scale, roundingMode);
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param v            值
     * @param scale        保留小数位数
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 新值
     * 
     */
    public static String roundStr(double v, int scale, RoundingMode roundingMode) {
        return round(v, scale, roundingMode).toPlainString();
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param numberStr    数字值的字符串表现形式
     * @param scale        保留小数位数，如果传入小于0，则默认0
     * @param roundingMode 保留小数的模式 {@link RoundingMode}，如果传入null则默认四舍五入
     * @return 新值
     */
    public static BigDecimal round(String numberStr, int scale, RoundingMode roundingMode) {
        if (StringUtils.isEmpty(numberStr)) {
            throw new RuntimeException("number cannot be null!");
        }
        if (scale < 0) {
            scale = 0;
        }
        return round(toBigDecimal(numberStr), scale, roundingMode);
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param number       数字值
     * @param scale        保留小数位数，如果传入小于0，则默认0
     * @param roundingMode 保留小数的模式 {@link RoundingMode}，如果传入null则默认四舍五入
     * @return 新值
     */
    public static BigDecimal round(BigDecimal number, int scale, RoundingMode roundingMode) {
        if (null == number) {
            number = BigDecimal.ZERO;
        }
        if (scale < 0) {
            scale = 0;
        }
        if (null == roundingMode) {
            roundingMode = RoundingMode.HALF_UP;
        }

        return number.setScale(scale, roundingMode);
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param numberStr    数字值的字符串表现形式
     * @param scale        保留小数位数
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 新值
     * 
     */
    public static String roundStr(String numberStr, int scale, RoundingMode roundingMode) {
        return round(numberStr, scale, roundingMode).toPlainString();
    }

    /**
     * 四舍六入五成双计算法
     * <p>
     * 四舍六入五成双是一种比较精确比较科学的计数保留法，是一种数字修约规则。
     * </p>
     *
     * <pre>
     * 算法规则:
     * 四舍六入五考虑，
     * 五后非零就进一，
     * 五后皆零看奇偶，
     * 五前为偶应舍去，
     * 五前为奇要进一。
     * </pre>
     *
     * @param number 需要科学计算的数据
     * @param scale  保留的小数位
     * @return 结果
     * 
     */
    public static BigDecimal roundHalfEven(Number number, int scale) {
        return roundHalfEven(toBigDecimal(number), scale);
    }

    /**
     * 四舍六入五成双计算法
     * <p>
     * 四舍六入五成双是一种比较精确比较科学的计数保留法，是一种数字修约规则。
     * </p>
     *
     * <pre>
     * 算法规则:
     * 四舍六入五考虑，
     * 五后非零就进一，
     * 五后皆零看奇偶，
     * 五前为偶应舍去，
     * 五前为奇要进一。
     * </pre>
     *
     * @param value 需要科学计算的数据
     * @param scale 保留的小数位
     * @return 结果
     * 
     */
    public static BigDecimal roundHalfEven(BigDecimal value, int scale) {
        return round(value, scale, RoundingMode.HALF_EVEN);
    }

    /**
     * 保留固定小数位数，舍去多余位数
     *
     * @param number 需要科学计算的数据
     * @param scale  保留的小数位
     * @return 结果
     * 
     */
    public static BigDecimal roundDown(Number number, int scale) {
        return roundDown(toBigDecimal(number), scale);
    }

    /**
     * 保留固定小数位数，舍去多余位数
     *
     * @param value 需要科学计算的数据
     * @param scale 保留的小数位
     * @return 结果
     * 
     */
    public static BigDecimal roundDown(BigDecimal value, int scale) {
        return round(value, scale, RoundingMode.DOWN);
    }

    /**
     * 数字转{@link BigDecimal}<br>
     * Float、Double等有精度问题，转换为字符串后再转换<br>
     * null转换为0
     *
     * @param number 数字
     * @return {@link BigDecimal}
     * 
     */
    public static BigDecimal toBigDecimal(Number number) {
        if (null == number) {
            return BigDecimal.ZERO;
        }
        if (!isValidNumber(number)) {
            throw new RuntimeException("Number is invalid!");
        }

        if (number instanceof BigDecimal) {
            return (BigDecimal) number;
        } else if (number instanceof Long) {
            return new BigDecimal((Long) number);
        } else if (number instanceof Integer) {
            return new BigDecimal((Integer) number);
        } else if (number instanceof BigInteger) {
            return new BigDecimal((BigInteger) number);
        }

        // Float、Double等有精度问题，转换为字符串后再转换
        return new BigDecimal(number.toString());
    }

    /**
     * 数字转{@link BigDecimal}<br>
     * null或""或空白符转换为0
     *
     * @param numberStr 数字字符串
     * @return {@link BigDecimal}
     * 
     */
    public static BigDecimal toBigDecimal(String numberStr) {
        if (StringUtils.isEmpty(numberStr)) {
            return BigDecimal.ZERO;
        }

        try {
            return new BigDecimal(numberStr);
        } catch (Exception ignore) {
            // 忽略解析错误
        }

        // 支持类似于 1,234.55 格式的数字
        final Number number = parseNumber(numberStr);
        return toBigDecimal(number);
    }

    /**
     * 数字转{@link BigInteger}<br>
     * null转换为0
     *
     * @param number 数字
     * @return {@link BigInteger}
     * 
     */
    public static BigInteger toBigInteger(Number number) {
        if (null == number) {
            return BigInteger.ZERO;
        }

        if (number instanceof BigInteger) {
            return (BigInteger) number;
        } else if (number instanceof Long) {
            return BigInteger.valueOf((Long) number);
        }

        if (!isValidNumber(number)) {
            throw new RuntimeException("Number is invalid!");
        }

        return toBigInteger(number.longValue());
    }

    /**
     * 数字转{@link BigInteger}<br>
     * null或""或空白符转换为0
     *
     * @param number 数字字符串
     * @return {@link BigInteger}
     * 
     */
    public static BigInteger toBigInteger(String number) {
        return StringUtils.isEmpty(number) ? BigInteger.ZERO : new BigInteger(number);
    }

    /**
     * 解析转换数字字符串为int型数字，规则如下：
     *
     * <pre>
     * 1、0x开头的视为16进制数字
     * 2、0开头的忽略开头的0
     * 3、其它情况按照10进制转换
     * 4、空串返回0
     * 5、.123形式返回0（按照小于0的小数对待）
     * 6、123.56截取小数点之前的数字，忽略小数部分
     * </pre>
     *
     * @param number 数字，支持0x开头、0开头和普通十进制
     * @return int
     * @throws NumberFormatException 数字格式异常
     * 
     */
    public static int parseInt(String number) throws NumberFormatException {
        if (StringUtils.isEmpty(number)) {
            return 0;
        }

        if (number.startsWith("0x") || number.startsWith("0X")) {
            // 0x04表示16进制数
            return Integer.parseInt(number.substring(2), 16);
        }

        if (number.contains("e") || number.contains("E")) {
            // 科学计数法忽略支持，科学计数法一般用于表示非常小和非常大的数字，这类数字转换为int后精度丢失，没有意义。
            throw new NumberFormatException("Unsupported int format: [{" + number + "}]");
        }

        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return parseNumber(number).intValue();
        }
    }

    /**
     * 解析转换数字字符串为long型数字，规则如下：
     *
     * <pre>
     * 1、0x开头的视为16进制数字
     * 2、0开头的忽略开头的0
     * 3、空串返回0
     * 4、其它情况按照10进制转换
     * 5、.123形式返回0（按照小于0的小数对待）
     * 6、123.56截取小数点之前的数字，忽略小数部分
     * </pre>
     *
     * @param number 数字，支持0x开头、0开头和普通十进制
     * @return long
     * 
     */
    public static long parseLong(String number) {
        if (StringUtils.isEmpty(number)) {
            return 0L;
        }

        if (number.startsWith("0x")) {
            // 0x04表示16进制数
            return Long.parseLong(number.substring(2), 16);
        }

        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            return parseNumber(number).longValue();
        }
    }

    /**
     * 解析转换数字字符串为long型数字，规则如下：
     *
     * <pre>
     * 1、0开头的忽略开头的0
     * 2、空串返回0
     * 3、其它情况按照10进制转换
     * 4、.123形式返回0.123（按照小于0的小数对待）
     * </pre>
     *
     * @param number 数字，支持0x开头、0开头和普通十进制
     * @return long
     * 
     */
    public static float parseFloat(String number) {
        if (StringUtils.isEmpty(number)) {
            return 0f;
        }

        try {
            return Float.parseFloat(number);
        } catch (NumberFormatException e) {
            return parseNumber(number).floatValue();
        }
    }

    /**
     * 解析转换数字字符串为long型数字，规则如下：
     *
     * <pre>
     * 1、0开头的忽略开头的0
     * 2、空串返回0
     * 3、其它情况按照10进制转换
     * 4、.123形式返回0.123（按照小于0的小数对待）
     * </pre>
     *
     * @param number 数字，支持0x开头、0开头和普通十进制
     * @return long
     * 
     */
    public static double parseDouble(String number) {
        if (StringUtils.isEmpty(number)) {
            return 0D;
        }

        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return parseNumber(number).doubleValue();
        }
    }

    /**
     * 将指定字符串转换为{@link Number} 对象<br>
     * 此方法不支持科学计数法
     *
     * <p>
     * 需要注意的是，在不同Locale下，数字的表示形式也是不同的，例如：<br>
     * 德国、荷兰、比利时、丹麦、意大利、罗马尼亚和欧洲大多地区使用`,`区分小数<br>
     * 也就是说，在这些国家地区，1.20表示120，而非1.2。
     * </p>
     *
     * @param numberStr Number字符串
     * @return Number对象
     * @throws NumberFormatException 包装了{@link ParseException}，当给定的数字字符串无法解析时抛出
     * 
     */
    public static Number parseNumber(String numberStr) throws NumberFormatException {
        if (numberStr.startsWith("0x") || numberStr.startsWith("0X")) {
            // 0x04表示16进制数
            return Long.parseLong(numberStr.substring(2), 16);
        }

        try {
            final NumberFormat format = NumberFormat.getInstance();
            if (format instanceof DecimalFormat) {
                // issue#1818@Github
                // 当字符串数字超出double的长度时，会导致截断，此处使用BigDecimal接收
                ((DecimalFormat) format).setParseBigDecimal(true);
            }
            return format.parse(numberStr);
        } catch (ParseException e) {
            final NumberFormatException nfe = new NumberFormatException(e.getMessage());
            nfe.initCause(e);
            throw nfe;
        }
    }

    /**
     * 解析转换数字字符串为 {@link java.lang.Integer } 规则如下：
     *
     * <pre>
     * 1、0x开头的视为16进制数字
     * 2、0开头的忽略开头的0
     * 3、其它情况按照10进制转换
     * 4、空串返回0
     * 5、.123形式返回0（按照小于0的小数对待）
     * 6、123.56截取小数点之前的数字，忽略小数部分
     * 7、解析失败返回默认值
     * </pre>
     *
     * @param numberStr    数字字符串，支持0x开头、0开头和普通十进制
     * @param defaultValue 如果解析失败, 将返回defaultValue, 允许null
     * @return Integer
     */
    public static Integer parseInt(String numberStr, Integer defaultValue) {
        if (StringUtils.isEmpty(numberStr)) {
            return defaultValue;
        }

        try {
            return parseInt(numberStr);
        } catch (NumberFormatException ignore) {

        }

        return defaultValue;
    }

    /**
     * 解析转换数字字符串为 {@link java.lang.Long } 规则如下：
     *
     * <pre>
     * 1、0x开头的视为16进制数字
     * 2、0开头的忽略开头的0
     * 3、其它情况按照10进制转换
     * 4、空串返回0
     * 5、.123形式返回0（按照小于0的小数对待）
     * 6、123.56截取小数点之前的数字，忽略小数部分
     * 7、解析失败返回默认值
     * </pre>
     *
     * @param numberStr    数字字符串，支持0x开头、0开头和普通十进制
     * @param defaultValue 如果解析失败, 将返回defaultValue, 允许null
     * @return Long
     */
    public static Long parseLong(String numberStr, Long defaultValue) {
        if (StringUtils.isEmpty(numberStr)) {
            return defaultValue;
        }

        try {
            return parseLong(numberStr);
        } catch (NumberFormatException ignore) {

        }

        return defaultValue;
    }

    /**
     * 解析转换数字字符串为 {@link java.lang.Float } 规则如下：
     *
     * <pre>
     * 1、0开头的忽略开头的0
     * 2、空串返回0
     * 3、其它情况按照10进制转换
     * 4、.123形式返回0.123（按照小于0的小数对待）
     * </pre>
     *
     * @param numberStr    数字字符串，支持0x开头、0开头和普通十进制
     * @param defaultValue 如果解析失败, 将返回defaultValue, 允许null
     * @return Float
     */
    public static Float parseFloat(String numberStr, Float defaultValue) {
        if (StringUtils.isEmpty(numberStr)) {
            return defaultValue;
        }

        try {
            return parseFloat(numberStr);
        } catch (NumberFormatException ignore) {

        }

        return defaultValue;
    }

    /**
     * 解析转换数字字符串为 {@link java.lang.Double } 规则如下：
     *
     * <pre>
     * 1、0开头的忽略开头的0
     * 2、空串返回0
     * 3、其它情况按照10进制转换
     * 4、.123形式返回0.123（按照小于0的小数对待）
     * </pre>
     *
     * @param numberStr    数字字符串，支持0x开头、0开头和普通十进制
     * @param defaultValue 如果解析失败, 将返回defaultValue, 允许null
     * @return Double
     */
    public static Double parseDouble(String numberStr, Double defaultValue) {
        if (StringUtils.isEmpty(numberStr)) {
            return defaultValue;
        }

        try {
            return parseDouble(numberStr);
        } catch (NumberFormatException ignore) {

        }

        return defaultValue;
    }

    /**
     * 将指定字符串转换为{@link Number }
     * 此方法不支持科学计数法
     *
     * @param numberStr    Number字符串
     * @param defaultValue 如果解析失败, 将返回defaultValue, 允许null
     * @return Number对象
     */
    public static Number parseNumber(String numberStr, Number defaultValue) {
        if (StringUtils.isEmpty(numberStr)) {
            return defaultValue;
        }

        try {
            return parseNumber(numberStr);
        } catch (NumberFormatException ignore) {

        }

        return defaultValue;
    }
}
