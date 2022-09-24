package cn.buli_home.utils.date;

import org.apache.commons.lang3.time.StopWatch;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class StopWatchUtils {

    private static final HashMap<String, StopWatch> cacheMap = new HashMap<>();

    private static final String DEFAULT_KEY = "STOP_WATCH_DEFAULT_KEY_$#@$&";

    public static void start() {
        start(DEFAULT_KEY);
    }

    public static void start(String key) {
        p_start(p_getStopWatch(key));
    }

    public static Long stop(TimeUnit timeUnit) {
        return stop(DEFAULT_KEY, timeUnit);
    }

    public static Long stop(String key, TimeUnit timeUnit) {
        StopWatch stopWatch = p_getStopWatch(key);

        Long time = p_stop(stopWatch, timeUnit);

        if (DEFAULT_KEY.equals(key)) {
            stopWatch.reset();
        } else {
            cacheMap.remove(key);
        }

        return time;
    }

    public static Long suspend(TimeUnit timeUnit) {
        return suspend(DEFAULT_KEY, timeUnit);
    }

    public static Long suspend(String key, TimeUnit timeUnit) {
        return p_suspend(p_getStopWatch(key), timeUnit);
    }

    public static Boolean resume() {
        return resume(DEFAULT_KEY);
    }

    public static Boolean resume(String key) {
        return p_resume(p_getStopWatch(key));
    }

    public static Long getTime(TimeUnit timeUnit) {
        return getTime(DEFAULT_KEY, timeUnit);
    }

    public static Long getTime(String key, TimeUnit timeUnit) {
        return p_getTime(p_getStopWatch(key), timeUnit);
    }

    private static StopWatch p_getStopWatch(String key) {
        StopWatch stopWatch = cacheMap.get(key);

        if (Objects.isNull(stopWatch)) {
            stopWatch = new StopWatch();
            cacheMap.put(key, stopWatch);
        }

        return stopWatch;
    }

    public static void p_start(StopWatch stopWatch) {
        StopWatch tSW = stopWatch;
        if (Objects.isNull(stopWatch)) {
            tSW = p_getStopWatch(DEFAULT_KEY);
            tSW.reset();
        }
        tSW.start();
    }

    public static Long p_suspend(StopWatch stopWatch, TimeUnit timeUnit) {
        if (Objects.nonNull(stopWatch)) {
            stopWatch.suspend();

            return stopWatch.getTime(timeUnit);
        }
        return 0L;
    }

    public static Boolean p_resume(StopWatch stopWatch) {
        if (Objects.isNull(stopWatch)) {
            return false;
        }

        if (stopWatch.isSuspended()) {
            stopWatch.resume();
        }

        return true;
    }

    private static Long p_stop(StopWatch stopWatch, TimeUnit timeUnit) {
        if (Objects.nonNull(stopWatch)) {
            stopWatch.stop();

            return stopWatch.getTime(timeUnit);
        }
        return 0L;
    }

    private static Long p_getTime(StopWatch stopWatch, TimeUnit timeUnit) {
        return Objects.nonNull(stopWatch) ? stopWatch.getTime(timeUnit) : 0L;
    }

}
