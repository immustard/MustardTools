package cn.buli_home.test;

import cn.buli_home.utils.common.StringFormatter;
import cn.buli_home.utils.file.FileUtils;
import cn.buli_home.utils.thread.ThreadUtils;
import io.vavr.Tuple2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author mustard
 * @version 1.0
 * Create by 2023-03-30
 */

public class Test {
    private static final Logger log = LogManager.getLogger(Test.class);

    public static void main(String[] args) {
        ExecutorService executor = ThreadUtils.newFixedExecutor(9,"mustard",true);
        for (int i = 0; i < 1000; i++) {
            executor.execute(() -> System.out.println(Thread.currentThread()));
        }
    }
}
