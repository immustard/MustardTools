package cn.buli_home.utils.thread;

import java.util.concurrent.*;

/**
 * 多线程工具
 *
 * @author mustard
 */
public class ThreadUtils {

    /**
     * 新建线程池
     * <pre>
     *    1. 初始线程数为corePoolSize指定的大小
     *    2. 没有最大线程数限制
     *    3. 默认使用LinkedBlockingQueue，默认队列大小为1024
     * </pre>
     *
     * @param corePoolSize 同事执行的线程数
     */
    public static ExecutorService newExecutor(int corePoolSize) {
        ExecutorBuilder builder = ExecutorBuilder.create();
        if (corePoolSize > 0) {
            builder.setCorePoolSize(corePoolSize);
        }
        return builder.build();
    }

    /**
     * 获得一个新的线程池，默认的策略如下：
     * <pre>
     *    1. 初始线程数为 0
     *    2. 最大线程数为Integer.MAX_VALUE
     *    3. 使用SynchronousQueue
     *    4. 任务直接提交给线程而不保持它们
     * </pre>
     */
    public static ExecutorService newExecutor() {
        return ExecutorBuilder.create().useSynchronousQueue().build();
    }

    /**
     * 获得一个新的线程池，只有单个线程，策略如下：
     * <pre>
     *    1. 初始线程数为 1
     *    2. 最大线程数为 1
     *    3. 默认使用LinkedBlockingQueue，默认队列大小为1024
     *    4. 同时只允许一个线程工作，剩余放入队列等待，等待数超过1024报错
     * </pre>
     */
    public static ExecutorService newSingleExecutor() {
        return ExecutorBuilder.create()
                .setCorePoolSize(1)
                .setMaxPoolSize(1)
                .setKeepAliveTime(0)
                .buildFinalizable();
    }

    /**
     * 获得一个新的线程池<br>
     * 如果maximumPoolSize &gt;= corePoolSize，在没有新任务加入的情况下，多出的线程将最多保留60s
     *
     * @param corePoolSize    初始线程池大小
     * @param maximumPoolSize 最大线程池大小
     */
    public static ThreadPoolExecutor newExecutor(int corePoolSize, int maximumPoolSize) {
        return ExecutorBuilder.create()
                .setCorePoolSize(corePoolSize)
                .setMaxPoolSize(maximumPoolSize)
                .build();
    }

    /**
     * 获得一个新的线程池，并指定最大任务队列大小<br>
     * 如果maximumPoolSize &gt;= corePoolSize，在没有新任务加入的情况下，多出的线程将最多保留60s
     *
     * @param corePoolSize     初始线程池大小
     * @param maximumPoolSize  最大线程池大小
     * @param maximumQueueSize 最大任务队列大小
     */
    public static ExecutorService newExecutor(int corePoolSize, int maximumPoolSize, int maximumQueueSize) {
        return ExecutorBuilder.create()
                .setCorePoolSize(corePoolSize)
                .setMaxPoolSize(maximumPoolSize)
                .setWorkQueue(new LinkedBlockingQueue<>(maximumQueueSize))
                .build();
    }

    /**
     * 获取一个新的线程池，默认的策略如下<br>
     * <pre>
     *     1. 核心线程数与最大线程数为nThreads指定的大小
     *     2. 默认使用LinkedBlockingQueue，默认队列大小为1024
     *     3. 如果isBlocked为{@code true}，当执行拒绝策略的时候会处于阻塞状态，直到能添加到队列中或者被{@link Thread#interrupt()}中断
     * </pre>
     *
     * @param nThreads         线程池大小
     * @param threadNamePrefix 线程名称前缀
     * @param isBlocked        是否使用{@link BlockPolicy}策略
     * @return ExecutorService
     */
    public static ExecutorService newFixedExecutor(int nThreads, String threadNamePrefix, boolean isBlocked) {
        return newFixedExecutor(nThreads, 1024, threadNamePrefix, isBlocked);
    }

    /**
     * 获取一个新的线程池，默认的策略如下<br>
     * <pre>
     *     1. 核心线程数与最大线程数为nThreads指定的大小
     *     2. 默认使用LinkedBlockingQueue
     *     3. 如果isBlocked为{@code true}，当执行拒绝策略的时候会处于阻塞状态，直到能添加到队列中或者被{@link Thread#interrupt()}中断
     * </pre>
     *
     * @param nThreads         线程池大小
     * @param maximumQueueSize 队列大小
     * @param threadNamePrefix 线程名称前缀
     * @param isBlocked        是否使用{@link BlockPolicy}策略
     * @return ExecutorService
     *
     */
    public static ExecutorService newFixedExecutor(int nThreads, int maximumQueueSize, String threadNamePrefix, boolean isBlocked) {
        return newFixedExecutor(nThreads, maximumQueueSize, threadNamePrefix,
                (isBlocked ? RejectPolicy.BLOCK : RejectPolicy.ABORT).getValue());
    }

    /**
     * 获得一个新的线程池，默认策略如下<br>
     * <pre>
     *     1. 核心线程数与最大线程数为nThreads指定的大小
     *     2. 默认使用LinkedBlockingQueue
     * </pre>
     *
     * @param nThreads         线程池大小
     * @param maximumQueueSize 队列大小
     * @param threadNamePrefix 线程名称前缀
     * @param handler          拒绝策略
     * @return ExecutorService
     *
     */
    public static ExecutorService newFixedExecutor(int nThreads,
                                                   int maximumQueueSize,
                                                   String threadNamePrefix,
                                                   RejectedExecutionHandler handler) {
        return ExecutorBuilder.create()
                .setCorePoolSize(nThreads).setMaxPoolSize(nThreads)
                .setWorkQueue(new LinkedBlockingQueue<>(maximumQueueSize))
                .setThreadFactory(createThreadFactory(threadNamePrefix))
                .setHandler(handler)
                .build();
    }

    /**
     * 创建自定义线程名称前缀的{@link ThreadFactory}
     *
     * @param threadNamePrefix 线程名称前缀
     * @return {@link ThreadFactory}
     * @see ThreadFactoryBuilder#build()
     * 
     */
    public static ThreadFactory createThreadFactory(String threadNamePrefix) {
        return ThreadFactoryBuilder.create().setNamePrefix(threadNamePrefix).build();
    }
}
