package cn.buli_home.utils.file;

import cn.buli_home.utils.common.HexUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class IOUtils {

    /**
     * 默认缓存大小 8192
     */
    public static final int DEFAULT_BUFFER_SIZE = 2 << 12;

    /**
     * 从流中读取前64个byte并转换为16进制，字母部分使用大写
     *
     * @param in {@link InputStream}
     * @return 16进制字符串
     */
    public static String readHex64Upper(InputStream in) throws RuntimeException {
        return readHex(in, 64, false);
    }

    /**
     * 从流中读取前8192个byte并转换为16进制，字母部分使用大写
     *
     * @param in {@link InputStream}
     * @return 16进制字符串
     */
    public static String readHex8192Upper(InputStream in) throws RuntimeException {
        try {
            int i = in.available();
            return readHex(in, Math.min(8192, in.available()), false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取16进制字符串
     *
     * @param in          {@link InputStream}
     * @param length      长度
     * @param toLowerCase true 传换成小写格式 ， false 传换成大写格式
     * @return 16进制字符串
     */
    public static String readHex(InputStream in, int length, boolean toLowerCase) throws RuntimeException {
        return HexUtils.encodeHexStr(readBytes(in, length), toLowerCase);
    }

    /**
     * 读取指定长度的byte数组，不关闭流
     *
     * @param in     {@link InputStream}，为{@code null}返回{@code null}
     * @param length 长度，小于等于0返回空byte数组
     * @return bytes
     */
    public static byte[] readBytes(InputStream in, int length) throws RuntimeException {
        if (null == in) {
            return null;
        }
        if (length <= 0) {
            return new byte[0];
        }

        final FastByteArrayOutputStream out = new FastByteArrayOutputStream(length);
        copy(in, out, DEFAULT_BUFFER_SIZE, length);
        return out.toByteArray();
    }

    /**
     * 拷贝流，拷贝后不关闭流
     *
     * @param in             输入流
     * @param out            输出流
     * @param bufferSize     缓存大小
     * @param count          总拷贝长度
     * @return 传输的byte数
     * @throws RuntimeException IO异常
     */
    public static long copy(InputStream in, OutputStream out, int bufferSize, long count) throws RuntimeException {
        return new StreamCopier(bufferSize, count).copy(in, out);
    }
}
