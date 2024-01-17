package cn.buli_home.utils.file;

import cn.buli_home.utils.constant.StringConstant;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class CommonFileUtils {

    /**
     * 生成txt文件名
     *
     * @param name      文件名
     * @param haveDate  是否有日期
     * @param canExtend 如果重名是否拓展
     */
    public static String generateHandleFileName(String name, boolean haveDate, boolean canExtend) {
        String path = FileConstant.RESULT_PATH_PREFIX + name;

        if (haveDate) {
            //当前日期时间方法 string类型
            SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMdd");
            String datetime = tempDate.format(new java.util.Date());
            path = path + StringConstant.UNDERLINE + datetime;
        }

        if (!canExtend) {
            return path + ".txt";
        }

        Path oPath = Paths.get(path + ".txt");
        boolean exists = Files.exists(oPath);

        if (exists) {
            int idx = 1;
            while (exists) {
                oPath = Paths.get(path + StringConstant.UNDERLINE + idx + ".txt");
                exists = Files.exists(oPath);
                idx += 1;
            }

            return oPath.toString();
        } else {
            return path + ".txt";
        }
    }

    /**
     * 生成txt文件名 (默认有日期)
     *
     * @param name      文件名
     * @param canExtend 如果重名是否拓展
     */
    public static String generateHandleFileName(String name, boolean canExtend) {
        return generateHandleFileName(name, true, canExtend);
    }

    /**
     * 文件夹是否存在
     */
    public static boolean existsFolder(String path) {
        if (Objects.isNull(path)) {
            return false;
        }

        File folder = new File(path);

        return (folder.exists() && folder.isDirectory());
    }

    /**
     * 文件是否存在
     */
    public static boolean existsFile(String path) {
        if (Objects.isNull(path)) {
            return false;
        }

        File file = new File(path);

        return (file.exists() && !file.isDirectory());
    }

    /**
     * 创建文件夹
     */
    public static boolean createFolder(String path) {
        if (Objects.isNull(path)) {
            return false;
        }

        boolean exists = existsFolder(path);

        if (!exists) {
            File folder = new File(path);

            return folder.mkdirs();
        }

        return true;
    }

    /**
     * 创建文件
     * @param path 文件地址
     * @param overwrite 是否覆盖
     */
    public static boolean createFile(String path, boolean overwrite) throws IOException {
        if (Objects.isNull(path)) {
            return false;
        }

        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            if (overwrite) {
                return file.createNewFile();
            }
            return true;
        }

        return file.createNewFile();
    }

}
