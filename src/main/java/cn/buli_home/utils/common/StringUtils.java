package cn.buli_home.utils.common;

import cn.buli_home.utils.constant.RegexConstant;
import cn.buli_home.utils.constant.StringConstant;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 判断是否为空
     */
    public static boolean isEmpty(CharSequence str) {
        return Objects.isNull(str) || str.length() == 0;
    }

    /**
     * 判断是否为空(去空格)
     */
    public static boolean isEmptyWithoutBlank(String str) {
        return isEmpty(replaceBlank(str));
    }

    /**
     * 去空白符
     */
    public static String replaceBlank(CharSequence str) {
        return replace(str.toString(), "\\s*|\t|\r|\n|&nbsp;");
    }

    /**
     * 字符串替换
     *
     * @param str     待替换字符串
     * @param pattern 匹配规则
     */
    public static String replace(String str, String pattern) {
        if (isEmpty(str)) {
            return StringConstant.EMPTY;
        }

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);

        return m.replaceAll(StringConstant.EMPTY);
    }

    /**
     * 替换渲染模板:
     * e.g. template: "My name is ${name} and I am ${age} years old."
     * params: {"name": "mustard", "age": 18}
     * result: My name is mustard and I am 18 years old.
     *
     * @param template 模板
     * @param params   替换内容
     */
    public static String replaceTemplate(String template, Map<String, Object> params) {
        return replaceTemplate(template, params, null);
    }

    /**
     * 替换渲染模板:
     * e.g. template: "My name is ${name} and I am ${age} years old."
     * params: {"name": "mustard", "age": 18}
     * result: My name is mustard and I am 18 years old.
     *
     * @param template 模板
     * @param params   替换内容
     */
    public static String replaceTemplate(String template, Map<String, Object> params, List<String> excludes) {
        if (Objects.isNull(template) || Objects.isNull(params)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        Matcher m = Pattern.compile("\\$\\{\\w+\\}").matcher(template);
        while (m.find()) {
            String param = m.group();
            String content = param.substring(2, param.length() - 1);

            if (Objects.nonNull(excludes) && excludes.contains(content)) {
                continue;
            }

            Object value = params.get(content);
            m.appendReplacement(sb, Matcher.quoteReplacement(value == null ? StringConstant.EMPTY : value.toString()));
        }
        m.appendTail(sb);

        return sb.toString();
    }

    /**
     * 替换最后一个对应字符
     *
     * @param str 待替换字符串
     * @param c   匹配字符
     * @param r   特换字符
     * @return 替换后字符串
     */
    public static String replaceLastChar(String str, char c, char r) {
        int lastIdx = str.lastIndexOf(c);
        if (lastIdx >= 0) {
            StringBuilder sb = new StringBuilder(str);
            sb.setCharAt(lastIdx, r);
            return sb.toString();
        }
        return str;
    }

    /**
     * 替换指定字符串的指定区间内字符为指定字符串，字符串只重复一次<br>
     * 此方法使用{@link String#codePoints()}完成拆分替换
     *
     * @param str          字符串
     * @param startInclude 开始位置（包含）
     * @param endExclude   结束位置（不包含）
     * @param replacedStr  被替换的字符串
     * @return 替换后的字符串
     */
    public static String replace(CharSequence str, int startInclude, int endExclude, CharSequence replacedStr) {
        if (isEmpty(str)) {
            return convert2String(str);
        }
        final String originalStr = convert2String(str);
        int[] strCodePoints = originalStr.codePoints().toArray();
        final int strLength = strCodePoints.length;
        if (startInclude > strLength) {
            return originalStr;
        }
        if (endExclude > strLength) {
            endExclude = strLength;
        }
        if (startInclude > endExclude) {
            // 如果起始位置大于结束位置，不替换
            return originalStr;
        }

        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < startInclude; i++) {
            stringBuilder.append(new String(strCodePoints, i, 1));
        }
        stringBuilder.append(replacedStr);
        for (int i = endExclude; i < strLength; i++) {
            stringBuilder.append(new String(strCodePoints, i, 1));
        }
        return stringBuilder.toString();
    }
    
    /**
     * 替换指定字符串的指定区间内字符为"*"
     * 俗称：脱敏功能，后面其他功能，可以见：DesensitizedUtil(脱敏工具类)
     *
     * <pre>
     * CharSequenceUtil.hide(null,*,*)=null
     * CharSequenceUtil.hide("",0,*)=""
     * CharSequenceUtil.hide("jackduan@163.com",-1,4)   ****duan@163.com
     * CharSequenceUtil.hide("jackduan@163.com",2,3)    ja*kduan@163.com
     * CharSequenceUtil.hide("jackduan@163.com",3,2)    jackduan@163.com
     * CharSequenceUtil.hide("jackduan@163.com",16,16)  jackduan@163.com
     * CharSequenceUtil.hide("jackduan@163.com",16,17)  jackduan@163.com
     * </pre>
     *
     * @param str          字符串
     * @param startInclude 开始位置（包含）
     * @param endExclude   结束位置（不包含）
     * @return 替换后的字符串
     */
    public static String hide(CharSequence str, int startInclude, int endExclude) {
        return replace(str, startInclude, endExclude, "*");
    }
    
    /**
     * 将 Object 转换为 String
     * null或<null> (不区分大小写), 认定为空
     */
    public static String convert2String(Object obj) {
        if (obj == null) {
            return StringConstant.EMPTY;
        }

        if (obj instanceof String) {
            if (((String) obj).equalsIgnoreCase(StringConstant.NULL) || ((String) obj).equalsIgnoreCase("<null>")) {
                return StringConstant.EMPTY;
            }

            return (String) obj;
        } else {
            return obj.toString();
        }
    }

    /**
     * 调用 convert2String 方法，null会返回{@code null}
     */
    public static String convert2StringOrNull(Object obj) {
        return Objects.isNull(obj) ? null : convert2String(obj);
    }

    /**
     * 大写第一个字母
     */
    public static String upperFirst(String str) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(str)) {
            return org.apache.commons.lang3.StringUtils.EMPTY;
        }

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (!Character.isLetter(c)) {
                continue;
            }

            chars[i] = p_upperChar(c);

            break;
        }

        return String.valueOf(chars);
    }

    /**
     * 小写第一个字母
     */
    public static String lowerFirst(String str) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(str)) {
            return org.apache.commons.lang3.StringUtils.EMPTY;
        }

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (!Character.isLetter(c)) {
                continue;
            }

            chars[i] = p_lowerChar(c);

            break;
        }

        return String.valueOf(chars);
    }

    /**
     * 驼峰转下划线
     */
    public static String camel2Underline(String str) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(str)) {
            return org.apache.commons.lang3.StringUtils.EMPTY;
        }

        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                char ch_ucase = p_lowerChar(ch);
                if (i > 0) {
                    buf.append('_');
                }
                buf.append(ch_ucase);
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * 下划线转驼峰
     */
    public static String underline2Camel(String str) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(str)) {
            return org.apache.commons.lang3.StringUtils.EMPTY;
        }

        String[] split = str.split("_");
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < split.length; i++) {
            String s = split[i];

            if (i == 0) {
                sb.append(s);
            } else {
                sb.append(upperFirst(s));
            }

        }
        return sb.toString();
    }

    /**
     * 去掉字符串指定的前缀
     *
     * @param str    字符串名称
     * @param prefix 前缀数组
     */
    public static String removePrefix(String str, String[] prefix) {
        if (Objects.isNull(str) || str.length() == 0) {
            return StringConstant.EMPTY;
        } else {
            if (null != prefix) {
                String[] prefixArray = prefix;

                for (int i = 0; i < prefix.length; ++i) {
                    String pf = prefixArray[i];
                    if (str.toLowerCase().matches("^" + pf.toLowerCase() + ".*")) {
                        //截取前缀后面的字符串
                        return str.substring(pf.length());
                    }
                }
            }

            return str;
        }
    }

    /**
     * 解析成 Integer
     */
    @Deprecated
    public static Integer parseInt(String str) {
        String tmpStr = convert2String(str);

        if (tmpStr.equals(StringConstant.EMPTY)) {
            return 0;
        } else {
            int n = 0;
            try {
                n = (int) Double.parseDouble(tmpStr);
            } catch (Exception e) {
                return 0;
            }
            return n;
        }
    }

    /**
     * 解析成 Long
     */
    @Deprecated
    public static Long parseLong(String str) {
        String tmpStr = convert2String(str);

        if (tmpStr.equals(StringConstant.EMPTY)) {
            return 0L;
        } else {
            long n = 0;
            try {
                n = Long.parseLong(tmpStr);
            } catch (Exception e) {
                return 0L;
            }
            return n;
        }
    }

    /**
     * 解析成 Double
     */
    @Deprecated
    public static Double parseDouble(String str) {
        String tmpStr = convert2String(str);

        if (tmpStr.equals(StringConstant.EMPTY)) {
            return 0.0;
        } else {
            double n = 0.0;
            try {
                n = Double.parseDouble(tmpStr);
            } catch (Exception e) {
                return 0.0;
            }
            return n;
        }
    }

    /**
     * 解析成 Boolean
     */
    public static Boolean parseBoolean(String str) {
        if (isEmpty(str)) {
            return false;
        }

        return str.equalsIgnoreCase("true")
                || str.equalsIgnoreCase("yes")
                || str.equalsIgnoreCase("success");
    }

    /**
     * 分隔字符串, 之后取第n位子字符串
     * 🌰: getSplitAtIdx("a,b,c", ",", 1) -> "b"
     */
    public static String getSplitAtIdx(String str, String regex, int idx) {
        String string = convert2String(str);
        String[] array = string.split(regex);

        if (array.length > idx) {
            return array[idx];
        }

        return StringConstant.EMPTY;
    }

    /**
     * 是否有特殊字符
     */
    public static boolean hasSpecialChar(String str) {
        Pattern p = Pattern.compile(RegexConstant.SPECIAL_CHAR);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 将对象转为字符串<br>
     *
     * <pre>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj 对象
     * @return 字符串
     */
    public static String utf8Str(Object obj) {
        return str(obj, StandardCharsets.UTF_8);
    }

    /**
     * 将对象转为字符串
     *
     * <pre>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj         对象
     * @param charsetName 字符集
     * @return 字符串
     * @deprecated 请使用 {@link #str(Object, Charset)}
     */
    @Deprecated
    public static String str(Object obj, String charsetName) {
        return str(obj, Charset.forName(charsetName));
    }

    /**
     * 将对象转为字符串
     * <pre>
     * 	 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 	 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj     对象
     * @param charset 字符集
     * @return 字符串
     */
    public static String str(Object obj, Charset charset) {
        if (null == obj) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return str((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return str((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return str((ByteBuffer) obj, charset);
        } else if (ArrayUtils.isArray(obj)) {
            return ArrayUtils.toString(obj);
        }

        return obj.toString();
    }

    /**
     * 将byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String str(byte[] bytes, String charset) {
        return str(bytes, Charset.forName(charset));
    }

    /**
     * 解码字节码
     *
     * @param data    字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 解码后的字符串
     */
    public static String str(byte[] data, Charset charset) {
        if (data == null) {
            return null;
        }

        if (null == charset) {
            return new String(data);
        }
        return new String(data, charset);
    }

    /**
     * 将Byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String str(Byte[] bytes, String charset) {
        return str(bytes, Charset.forName(charset));
    }

    /**
     * 解码字节码
     *
     * @param data    字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 解码后的字符串
     */
    public static String str(Byte[] data, Charset charset) {
        if (data == null) {
            return null;
        }

        byte[] bytes = new byte[data.length];
        Byte dataByte;
        for (int i = 0; i < data.length; i++) {
            dataByte = data[i];
            bytes[i] = (null == dataByte) ? -1 : dataByte;
        }

        return str(bytes, charset);
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     *
     * @param data    数据
     * @param charset 字符集，如果为空使用当前系统字符集
     * @return 字符串
     */
    public static String str(ByteBuffer data, String charset) {
        if (data == null) {
            return null;
        }

        return str(data, Charset.forName(charset));
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     *
     * @param data    数据
     * @param charset 字符集，如果为空使用当前系统字符集
     * @return 字符串
     */
    public static String str(ByteBuffer data, Charset charset) {
        if (null == charset) {
            charset = Charset.defaultCharset();
        }
        return charset.decode(data).toString();
    }

    /**
     * 字节长度
     */
    public static int bytesLength(String str) {
        return convert2String(str).getBytes().length;
    }

    /**
     * base64
     */
    public static String base64(String str) {
        return Base64.encodeBase64String(convert2String(str).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * md5
     */
    public static String md5(String str) {
        return hash(str, "MD5");
    }

    /**
     * sah1
     */
    public static String sha1(String str) {
        return hash(str, "SHA-1");
    }

    /**
     * sha256
     */
    public static String sha256(String str) {
        return hash(str, "SHA-256");
    }

    /**
     * sha512
     */
    public static String sha512(String str) {
        return hash(str, "SHA-512");
    }

    /**
     * hash 值
     *
     * @param str  字符串
     * @param type 类型: (🌰: MD5, SHA-1, ...)
     */
    public static String hash(String str, String type) {
        try {
            MessageDigest digest = MessageDigest.getInstance(type);
            return Hex.encodeHexString(digest.digest(convert2String(str).getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return StringConstant.EMPTY;
    }

    public static Boolean containsAnyChar(String str, char... chars) {
        if (!isEmpty(str)) {
            int len = str.length();
            for (int i = 0; i < len; i++) {
                if (ArrayUtils.contains(chars, str.charAt(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 查找指定字符串是否包含指定字符串列表中的任意一个字符串
     *
     * @param str      指定字符串
     * @param testStrs 需要检查的字符串数组
     * @return 是否包含任意一个字符串
     * 
     */
    public static boolean containsAny(CharSequence str, CharSequence... testStrs) {
        return null != getContainsStr(str, testStrs);
    }

    /**
     * 查找指定字符串是否包含指定字符串列表中的任意一个字符串，如果包含返回找到的第一个字符串
     *
     * @param str      指定字符串
     * @param testStrs 需要检查的字符串数组
     * @return 被包含的第一个字符串
     * 
     */
    public static String getContainsStr(CharSequence str, CharSequence... testStrs) {
        if (isEmpty(str) || ArrayUtils.isEmpty(testStrs)) {
            return null;
        }
        for (CharSequence checkStr : testStrs) {
            if (null != checkStr && str.toString().contains(checkStr)) {
                return checkStr.toString();
            }
        }
        return null;
    }

    /**
     * 字符串是否包含该正则
     *
     * @param str   待匹配字符串
     * @param regex 正则表达式
     */
    public static Boolean hasRegex(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).find();
    }

    /**
     * 字符串是否只包含该正则
     *
     * @param str   待匹配字符串
     * @param regex 正则表达式
     */
    public static Boolean onlyRegex(String str, String regex) {
        return Pattern.matches(regex, str);
    }

    /**
     * 查找满足正则匹配条件的第一个字符串
     *
     * @param str   待匹配字符串
     * @param regex 正则表达式
     */
    public static String findRegexFirst(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 查找满足正则匹配条件的所有字符串
     *
     * @param str   待匹配字符串
     * @param regex 正则表达式
     */
    public static List<String> findRegexList(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }

        return list;
    }

    /**
     * 获取字符串中指定字符串数量
     *
     * @param str    待匹配字符串
     * @param symbol 指定字符串
     */
    public static int specifiedSymbolCount(String str, String symbol) {
        return (str.length() - str.replace(symbol, StringConstant.EMPTY).length()) / symbol.length();
    }

    /**
     * 是否以指定字符串开头
     * 如果给定的字符串和开头字符串都为null则返回true，否则任意一个值为null返回false
     *
     * @param str            待匹配字符串
     * @param prefix         开头
     * @param isIgnoreCase   是否忽略大小写
     * @param isIgnoreEquals 是否忽略字符串相等的情况
     */
    public static boolean startWith(CharSequence str, CharSequence prefix, boolean isIgnoreCase, boolean isIgnoreEquals) {
        if (Objects.isNull(str) || Objects.isNull(prefix)) {
            return !isIgnoreEquals;
        }

        String s = str.toString();
        String p = prefix.toString();

        boolean isStartWith = s.regionMatches(isIgnoreCase, 0, p, 0, prefix.length());

        if (isStartWith) {
            return !isIgnoreEquals || s.equalsIgnoreCase(p);
        }

        return false;
    }

    /**
     * 是否以指定字符串开头，忽略大小写
     *
     * @param str    被监测字符串
     * @param prefix 开头字符串
     */
    public static boolean startWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return startWith(str, prefix, true, false);
    }

    private static char p_upperChar(char c) {
        if (c >= 97) {
            c -= 32;
        }
        return c;
    }

    private static char p_lowerChar(char c) {
        if (c < 97) {
            c += 32;
        }
        return c;
    }
}
