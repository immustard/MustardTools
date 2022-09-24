package cn.buli_home.utils.common;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 判断是否为空
     */
    public static boolean isEmpty(String str) {
        if (Objects.isNull(str) || str.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为空(去空格)
     */
    public static boolean isEmptyWithoutBlank(String str) {
        return isEmpty(replaceBlank(str));
    }

    public static String replaceBlank(String str) {
        if(isEmpty(str)){
            return "";
        }

        Pattern p = Pattern.compile("\\s*|\t|\r|\n|&nbsp;");
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    public static String convert2String(Object obj) {
        if (Objects.isNull(obj)) {
            return "";
        }

        return obj.toString();
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
     * @param str 字符串名称
     * @param prefix 前缀数组
     * @return
     */
    public static String removePrefix(String str, String[] prefix) {
        if (Objects.isNull(str) || str.length() == 0) {
            return "";
        } else {
            if (null != prefix) {
                String[] prefixArray = prefix;

                for(int i = 0; i < prefix.length; ++i) {
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

    public static int parseInt(String str) {
        if (Objects.isNull(str) || str.length() == 0) {
            return 0;
        }

        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static long parseLong(String str) {
        if (Objects.isNull(str) || str.length() == 0) {
            return 0L;
        }

        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public static boolean parseBoolean(String str) {
        if (Objects.isNull(str) || str.length() == 0) {
            return false;
        }

        return str.equalsIgnoreCase("true") || str.equalsIgnoreCase("yes");
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
