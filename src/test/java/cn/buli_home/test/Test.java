package cn.buli_home.test;

import cn.buli_home.utils.thread.ThreadUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

/**
 * @author mustard
 * @version 1.0
 * Create by 2023-03-30
 */

@Slf4j
public class Test {

    public static void main(String[] args) {
        ExecutorService executor = ThreadUtils.newFixedExecutor(9, "mustard", true);
        for (int i = 0; i < 1000; i++) {
            executor.execute(() -> System.out.println(Thread.currentThread()));
        }
    }
}
