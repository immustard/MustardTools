package org.buli.utils.common;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.buli.utils.file.FileConstant;
import org.buli.utils.file.FileUtils;

import java.util.Objects;

public class StringUtils {

    final static private String UD_FILE_NAME = "UserDefault.txt";

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
     * 持久化字符串
     */
    public static void recordString(String key, String value) throws Exception {
        if (Objects.isNull(key)) { return; }

        String path = FileConstant.USER_DEFAULT_PATH + UD_FILE_NAME;

        String content = FileUtils.readFile(path);
        JSONObject jsonObject = JSON.parseObject(content);

        if (Objects.isNull(jsonObject)) {
            jsonObject = new JSONObject();
        }

        jsonObject.put(key, value);

        FileUtils.writeFile(FileConstant.USER_DEFAULT_PATH + UD_FILE_NAME, jsonObject.toJSONString(), true);
    }

    public static String loadRecordString(String key) {
        if (Objects.isNull(key)) { return ""; }

        String path = FileConstant.USER_DEFAULT_PATH + UD_FILE_NAME;

        String content = FileUtils.readFile(path);
        JSONObject jsonObject = JSON.parseObject(content);

        return null == jsonObject ? "" : jsonObject.getString(key);
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
