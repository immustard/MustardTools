package org.buli.apps;

import io.vavr.Tuple2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.buli.utils.file.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileApp {

    private static final Logger log = LogManager.getLogger(FileApp.class);


    public static void main(String[] args) throws Exception {
        List<Tuple2<Integer, String>> list = FileUtils.readFileByLine(new File("/Users/mustard/Downloads/can"));

        Set<String> set = new HashSet<>();
        List<String> resultList = new ArrayList<>();

        for (Tuple2<Integer, String> tuple : list) {
            if (set.contains(tuple._2)) {
                resultList.add(tuple._2);
            }

            set.add(tuple._2);
        }

        log.info(resultList);

        log.info("finish");
    }

}
