package cn.buli_home.utils.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * {@link InputStream} 向 {@link OutputStream} 拷贝
 *
 * @author looly
 * 
 */
public class StreamCopier extends IoCopier<InputStream, OutputStream> {

    /**
     * 构造
     */
    public StreamCopier() {
        this(IOUtils.DEFAULT_BUFFER_SIZE);
    }

    /**
     * 构造
     *
     * @param bufferSize 缓存大小
     */
    public StreamCopier(int bufferSize) {
        this(bufferSize, -1);
    }

    /**
     * 构造
     *
     * @param bufferSize 缓存大小
     * @param count      拷贝总数
     */
    public StreamCopier(int bufferSize, long count) {
        super(bufferSize, count);
    }

    public long copy(InputStream source, OutputStream target) {
		if (Objects.isNull(source)) {
			throw new RuntimeException("InputStream is null!");
		}
		if (Objects.isNull(target)) {
			throw new RuntimeException("OutputStream is null!");
		}

        final long size;
        try {
            size = doCopy(source, target, new byte[bufferSize(this.count)]);
            target.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return size;
    }

    /**
     * 执行拷贝，如果限制最大长度，则按照最大长度读取，否则一直读取直到遇到-1
     *
     * @param source {@link InputStream}
     * @param target {@link OutputStream}
     * @param buffer 缓存
     * @return 拷贝总长度
     * @throws IOException IO异常
     */
    private long doCopy(InputStream source, OutputStream target, byte[] buffer) throws IOException {
        long numToRead = this.count > 0 ? this.count : Long.MAX_VALUE;
        long total = 0;

        int read;
        while (numToRead > 0) {
            read = source.read(buffer, 0, bufferSize(numToRead));
            if (read < 0) {
                // 提前读取到末尾
                break;
            }
            target.write(buffer, 0, read);
            if (flushEveryBuffer) {
                target.flush();
            }

            numToRead -= read;
            total += read;
        }

        return total;
    }
}
