package cn.buli_home.utils.file;

import cn.buli_home.utils.common.StringUtils;
import cn.buli_home.utils.constant.StringConstant;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 文件持久化工具
 *
 * @author mustard
 * @version 1.0
 * Create by 2022/9/18
 */
@Slf4j
public class UserDefaultUtils {

    private final static String UD_FILE_NAME = "UserDefault.txt";

    private static String UD_FILE_PATH = FileConstant.USER_DEFAULT_PATH;

    private static String PATH = UD_FILE_PATH + UD_FILE_NAME;

    /**
     * 记录内容
     */
    public static void recordString(String key, String value) throws Exception {
        p_recordString(key, value);
    }

    /**
     * 记录内容
     */
    public static void record(Map<String, String> map) throws Exception {
        p_record(map);
    }

    /**
     * 读取文件内容
     */
    public static String loadRecordString(String key) throws Exception {
        return p_loadRecordString(key);
    }

    /**
     * 设置文件路径
     *
     * @param filePath
     */
    public static void setFilePath(String filePath) {
        p_setFilePath(filePath);
    }

    /**
     * 清除键值对
     *
     * @param key 键
     */
    public static void clean(String key) {
        p_clean(key);
    }

    /**
     * 清除所有值
     */
    public static void cleanAll() {
        p_cleanAll();
    }

    /**
     * 持久化字符串
     */
    private static void p_recordString(String key, String value) throws Exception {
        if (Objects.isNull(key)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put(key, value);

        record(map);
    }

    /**
     * 持久化字符串 (键值对)
     */
    private static void p_record(Map<String, String> map) throws Exception {
        if (Objects.isNull(map)) {
            return;
        }

        String content = FileUtils.readFile(PATH);
        JSONObject jsonObject = JSON.parseObject(content);

        if (Objects.isNull(jsonObject)) {
            jsonObject = new JSONObject();
        }

        map.forEach(jsonObject::put);

        FileUtils.writeFile(PATH, jsonObject.toJSONString(), FileWriteType.OVERWRITE);
    }

    private static String p_loadRecordString(String key) throws Exception {
        if (Objects.isNull(key)) {
            return StringConstant.EMPTY;
        }

        String content = FileUtils.readFile(PATH);
        JSONObject jsonObject = JSON.parseObject(content);

        return Objects.isNull(jsonObject) ? StringConstant.EMPTY : jsonObject.getString(key);
    }

    private static void p_setFilePath(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return;
        }

        UD_FILE_PATH = filePath;

        if (!filePath.endsWith(StringConstant.BACKSLASH) && !filePath.endsWith("/")) {
            PATH = UD_FILE_PATH + File.separator + UD_FILE_NAME;
        } else {
            PATH = UD_FILE_PATH + UD_FILE_NAME;
        }
    }

    private static void p_clean(String key) {
        try {
            recordString(key, StringConstant.EMPTY);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private static void p_cleanAll() {
        try {
            FileUtils.writeFile(PATH, StringConstant.EMPTY, FileWriteType.OVERWRITE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
