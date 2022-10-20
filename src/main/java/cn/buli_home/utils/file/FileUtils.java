package cn.buli_home.utils.file;

import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    /**
     * 按行读取文件
     */
    public static List<Tuple2<Integer, String>> readFileByLine(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String temp = null;
        int line = 1;

        List<Tuple2<Integer, String>> resultList = new ArrayList<>();

        while ((temp = reader.readLine()) != null) {
            resultList.add(Tuple.of(line, temp));
            line++;
        }

        if (reader != null) {
            reader.close();
        }

        return resultList;
    }

    /**
     * 写入内容到指定文件
     * @param path 文件路径
     * @param content 写入内容
     * @param overwrite 是否覆盖
     */
    public static void writeFile(String path, String content, boolean overwrite) throws Exception {
        Path oPath = Paths.get(path);
        boolean exists = Files.exists(oPath);

        if (exists) {
            if (!overwrite) {
                return;
            }
            Files.delete(oPath);
        }

        RandomAccessFile stream = new RandomAccessFile(path, "rw");
        FileChannel channel = stream.getChannel();

        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        channel.write(buffer);

        channel.close();
        stream.close();
    }

    /**
     * 读取文件内容
     */
    public static String readFile(String path) {
        boolean exists = CommonFileUtils.existsFile(path);
        if (!exists) { return ""; }

        File file = new File(path);
        Reader reader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    stringBuilder.append((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    /**
     * 创建文件夹
     */
    public static void createFolder(String path) throws IOException {
        Path iPath = Paths.get(path);
        boolean exists = Files.exists(iPath);

        if (exists) {
            return ;
        }

        Files.createDirectories(iPath);
    }
}
