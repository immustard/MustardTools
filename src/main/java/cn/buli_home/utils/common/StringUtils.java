package cn.buli_home.utils.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

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
    public static boolean isEmpty(String str) {
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
    public static String replaceBlank(String str) {
        return replace(str, "\\s*|\t|\r|\n|&nbsp;");
    }

    /**
     * 字符串替换
     *
     * @param str     待替换字符串
     * @param pattern 匹配规则
     */
    public static String replace(String str, String pattern) {
        if (isEmpty(str)) {
            return "";
        }

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);

        return m.replaceAll("");
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
            m.appendReplacement(sb, Matcher.quoteReplacement(value == null ? "" : value.toString()));
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
     * 将 Object 转换为 String
     * null或<null> (不区分大小写), 认定为空
     */
    public static String convert2String(Object obj) {
        if (obj == null) {
            return "";
        }

        if (obj instanceof String) {
            if (((String) obj).equalsIgnoreCase("NULL") || ((String) obj).equalsIgnoreCase("<null>")) {
                return "";
            }

            return (String) obj;
        } else {
            return obj.toString();
        }
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
        for (String s : split) {
            sb.append(lowerFirst(s));
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
            return "";
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
    public static Integer parseInt(String str) {
        String tmpStr = convert2String(str);

        if (tmpStr.equals("")) {
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
    public static Long parseLong(String str) {
        String tmpStr = convert2String(str);

        if (tmpStr.equals("")) {
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
    public static Double parseDouble(String str) {
        String tmpStr = convert2String(str);

        if (tmpStr.equals("")) {
            return 0.0;
        } else {
            Double n = 0.0;
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

        return "";
    }

    /**
     * 是否有特殊字符
     */
    public static boolean hasSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
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
        return "";
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
    private static int specifiedSymbolCount(String str, String symbol) {
        return (str.length() - str.replace(symbol, "").length()) / symbol.length();
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
