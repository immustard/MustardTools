package cn.buli_home.utils.file;

import cn.buli_home.utils.common.StringUtils;
import cn.buli_home.utils.constant.CharConstant;
import cn.buli_home.utils.constant.StringConstant;
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
import java.util.Objects;

public class FileUtils {

    /**
     * 缓存区大小
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * 文件打开模式
     */
    private static final String FILE_OPEN_MODE = "rw";

    /**
     * 按行读取文件
     */
    public static List<Tuple2<Integer, String>> readFileByLine(File file) throws Exception {
        List<Tuple2<Integer, String>> resultList = new ArrayList<>();

        // 分配一个新的字节缓冲区。
        ByteBuffer rebuff = ByteBuffer.allocate(1024);
        // 创建path文件的文件字节输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        // 获取通道
        FileChannel fileChannel = fileInputStream.getChannel();
        // 当前行数
        int lineIdx = 0;
        // 换行符，目前需手动指定
        int CF = "\n".getBytes()[0];
        // 临时数组
        byte[] temp = new byte[0];
        // 循环读取通道中的数据并放入rbuf中
        while (fileChannel.read(rebuff) != -1) {
            // 创建与 rebuff 容量一样大的数组
            byte[] rbyte = new byte[rebuff.position()];
            // 读/写指针position指到缓冲区头部,并设置了最大读取长度
            rebuff.flip();
            // 将rebuff中的数据传输到rbyte中
            rebuff.get(rbyte);
            // 每行的起始位下标，相当于当前所读取到的byte数组
            int startNum = 0;
            // 循环读取rbyte，判断是否有换行符
            for (int i = 0; i < rbyte.length; i++) {
                // 当存在换行符时
                if (rbyte[i] == CF) {
                    // 创建临时数组用于保存整行数据
                    byte[] line = new byte[temp.length + i - startNum];
                    // 将上次读取剩下的部分存入line
                    System.arraycopy(temp, 0, line, 0, temp.length);
                    // 将读取到的当前rbyte中的数据追加到line
                    System.arraycopy(rbyte, startNum, line, temp.length, i - startNum);
                    // 更新下一行起始位置
                    startNum = i + 1;
                    // 初始化temp数组
                    temp = new byte[0];
                    // 处理数据,此时line即为要处理的一整行数据
                    String lineStr = new String(line, StandardCharsets.UTF_8);
                    resultList.add(Tuple.of(lineIdx, lineStr));
                    lineIdx++;
                }
            }
            // 说明rbyte最后还剩不完整的一行
            if (startNum < rbyte.length) {
                byte[] temp2 = new byte[temp.length + rbyte.length - startNum];
                System.arraycopy(temp, 0, temp2, 0, temp.length);
                System.arraycopy(rbyte, startNum, temp2, temp.length, rbyte.length - startNum);
                temp = temp2;
            }
            rebuff.clear();
        }
        // 兼容最后一行没有换行的情况
        if (temp.length > 0) {
            // 处理数据,此时line即为要处理的一整行数据
            String lineStr = new String(temp, StandardCharsets.UTF_8);
            resultList.add(Tuple.of(lineIdx, lineStr));
        }
        // 关闭通道
        fileChannel.close();

        return resultList;
    }

    /**
     * 写入内容到指定文件
     *
     * @param path      文件路径
     * @param content   写入内容
     * @param overwrite 是否覆盖
     */
    @Deprecated
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
     * 写入内容到指定文件
     *
     * @param path      文件路径
     * @param content   写入内容
     * @param writeType 写模式
     */
    public static void writeFile(String path, String content, FileWriteType writeType) throws Exception {
        Path oPath = Paths.get(path);

        if (writeType == FileWriteType.ONCE) {
            if (Files.exists(oPath)) {
                return;
            }
        }

        if (writeType == FileWriteType.OVERWRITE) {
            Files.delete(oPath);
        }

        RandomAccessFile raFile = new RandomAccessFile(path, FILE_OPEN_MODE);
        FileChannel channel = raFile.getChannel();

        // 如果是追加新行, 且文件不为空
        String writeContent = content;
        if (writeType == FileWriteType.APPEND_NEWLINE && channel.size() != 0) {
            writeContent = StringConstant.CRLF.concat(content);
        }
        byte[] bytes = writeContent.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        channel.position(channel.size());

        // 先清空一下缓存区
        buffer.clear();

        buffer.put(bytes);
        buffer.flip();

        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }

        channel.close();
        raFile.close();
    }

    /**
     * 写入内容到指定文件
     *
     * @param file      文件
     * @param content   写入内容
     * @param overwrite 是否覆盖
     */
    @Deprecated
    public static void writeFile(File file, String content, boolean overwrite) throws Exception {
        if (Objects.isNull(file)) {
            return;
        }

        FileWriter writer = null;

        try {
            writer = new FileWriter(file, !overwrite);
            writer.append(content);
            writer.flush();
        } finally {
            if (Objects.nonNull(writer)) {
                writer.close();
            }
        }
    }

    /**
     * 写入内容到指定文件
     *
     * @param file      文件
     * @param content   写入内容
     * @param writeType 写模式
     */
    public static void writeFile(File file, String content, FileWriteType writeType) throws Exception {
        writeFile(file.getPath(), content, writeType);
    }

    /**
     * 读取文件内容
     */
    public static String readFile(String path) throws Exception {
        boolean exists = CommonFileUtils.existsFile(path);
        if (!exists) {
            throw new FileNotFoundException();
        }

        //创建FileChannel
        RandomAccessFile raFile = new RandomAccessFile(path, FILE_OPEN_MODE);
        FileChannel channel = raFile.getChannel();

        //创建Buffer
        ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);

        //读取数据到buffer中
        StringBuilder stringBuilder = new StringBuilder();

        int bytesRead = channel.read(buf);
        while (bytesRead != -1) {
            buf.flip();
            while (buf.hasRemaining()) {
                char tChar = (char) buf.get();
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if ((tChar) != CharConstant.CR) {
                    stringBuilder.append(tChar);
                }
            }
            buf.clear();
            bytesRead = channel.read(buf);
        }
        channel.close();
        raFile.close();

        return stringBuilder.toString();
    }

    /**
     * 创建文件夹
     */
    public static void createFolder(String path) throws IOException {
        if (folderExists(path)) {
            return;
        }

        Path iPath = Paths.get(path);
        Files.createDirectories(iPath);
    }

    public static boolean folderExists(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }

    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static boolean renameFile(String oldPath, String newPath) {
        File file = new File(oldPath);

        if (!file.exists()) {
            return false;
        }

        return file.renameTo(new File(newPath));
    }

    /**
     * 获取文件扩展名 (不带`.`)
     */
    public static String extName(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return StringConstant.EMPTY;
        }
    }
}
