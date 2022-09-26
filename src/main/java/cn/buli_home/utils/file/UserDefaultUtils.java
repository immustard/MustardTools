package cn.buli_home.utils.file;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import cn.buli_home.utils.common.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class UserDefaultUtils {

    protected final Logger log = LogManager.getLogger(this.getClass());

    private final String UD_FILE_NAME = "UserDefault.txt";

    private String UD_FILE_PATH = FileConstant.USER_DEFAULT_PATH;

    private String PATH = UD_FILE_PATH + UD_FILE_NAME;

    /**
     * 记录内容
     */
    public static void recordString(String key, String value) throws Exception {
        getInstance().p_recordString(key, value);
    }

    /**
     * 记录内容
     */
    public static void record(Map<String, String> map) throws Exception {
        getInstance().p_record(map);
    }

    /**
     * 读取文件内容
     */
    public static String loadRecordString(String key) {
        return getInstance().p_loadRecordString(key);
    }

    /**
     * 设置文件路径
     * @param filePath
     */
    public static void setFilePath(String filePath) {
        getInstance().p_setFilePath(filePath);
    }

    /**
     * 清除键值对
     * @param key 键
     */
    public static void clean(String key) {
        getInstance().p_clean(key);
    }

    /**
     * 清除所有值
     */
    public static void cleanAll() {
        getInstance().p_cleanAll();
    }

    /**
     * 持久化字符串
     */
    private void p_recordString(String key, String value) throws Exception {
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
    private void p_record(Map<String, String> map) throws Exception {
        if (Objects.isNull(map)) {
            return;
        }

        String content = FileUtils.readFile(PATH);
        JSONObject jsonObject = JSON.parseObject(content);

        if (Objects.isNull(jsonObject)) {
            jsonObject = new JSONObject();
        }

        map.forEach(jsonObject::put);

        FileUtils.writeFile(PATH, jsonObject.toJSONString(), true);
    }

    private String p_loadRecordString(String key) {
        if (Objects.isNull(key)) {
            return "";
        }

        String content = FileUtils.readFile(PATH);
        JSONObject jsonObject = JSON.parseObject(content);

        return null == jsonObject ? "" : jsonObject.getString(key);
    }

    private void p_setFilePath(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return;
        }

        UD_FILE_PATH = filePath;

        if (!filePath.endsWith("\\") && !filePath.endsWith("/")) {
            PATH = UD_FILE_PATH + File.separator + UD_FILE_NAME;
        } else {
            PATH = UD_FILE_PATH + UD_FILE_NAME;
        }
    }

    private void p_clean(String key) {
        try {
            recordString(key, "");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void p_cleanAll() {
        try {
            FileUtils.writeFile(PATH, "", true);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    private static volatile UserDefaultUtils INSTANCE = null;

    private UserDefaultUtils() {
    }

    private static UserDefaultUtils getInstance() {
        if (null == INSTANCE) {
            synchronized (UserDefaultUtils.class) {
                if (null == INSTANCE) {
                    INSTANCE = new UserDefaultUtils();
                }
            }
        }
        return INSTANCE;
    }
}
