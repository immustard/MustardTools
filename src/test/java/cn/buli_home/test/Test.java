package cn.buli_home.test;

import cn.buli_home.utils.file.FileUtils;
import io.vavr.Tuple2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

/**
 * @author mustard
 * @version 1.0
 * Create by 2023-03-30
 */

public class Test {
    private static final Logger log = LogManager.getLogger(Test.class);

    public static void main(String[] args) {
        System.out.println("start");
        try {
            List<Tuple2<Integer, String>> list = FileUtils.readFileByLine(new File("/Users/mustard/Downloads/test-2023-03-30.txt"));

            for (Tuple2<Integer, String> tuple : list) {
                System.out.println("第 " + tuple._1 + " 行: " + tuple._2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
