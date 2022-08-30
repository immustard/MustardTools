package org.buli.utils.file;

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
     * @return
     */
    public static String generateHandleFileName(String name, boolean haveDate, boolean canExtend) {
        String path = FileConstant.RESULT_PATH_PREFIX + name;

        if (haveDate) {
            //当前日期时间方法 string类型
            SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMdd");
            String datetime = tempDate.format(new java.util.Date());
            path = path + "_" + datetime;
        }

        if (!canExtend) {
            return path + ".txt";
        }

        Path oPath = Paths.get(path + ".txt");
        boolean exists = Files.exists(oPath);

        if (exists) {
            int idx = 1;
            while (exists) {
                oPath = Paths.get(path + "_" + idx + ".txt");
                exists = Files.exists(oPath);
                idx += 1;
            }

            return oPath.toString();
        } else {
            return path + ".txt";
        }
    }

    public static String generateHandleFileName(String name, boolean canExtend) {
        return generateHandleFileName(name, true, canExtend);
    }

    public static boolean existsFolder(String path) {
        if (Objects.isNull(path)) {
            return false;
        }

        File folder = new File(path);

        return (folder.exists() && folder.isDirectory());
    }

    public static boolean existsFile(String path) {
        if (Objects.isNull(path)) {
            return false;
        }

        File file = new File(path);

        return (file.exists() && !file.isDirectory());
    }

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
