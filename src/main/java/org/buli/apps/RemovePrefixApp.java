package org.buli.apps;

import io.vavr.Tuple2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.buli.utils.common.StringUtils;
import org.buli.utils.file.FileUtils;

import java.io.File;
import java.util.List;

public class RemovePrefixApp {

    private static final Logger log = LogManager.getLogger(RemovePrefixApp.class);


    public static void main(String[] args) throws Exception {
        List<Tuple2<Integer, String>> fieldList = FileUtils.readFileByLine(new File("/Users/mustard/BaiduSync/Temp/had-field.txt"));
        List<Tuple2<Integer, String>> ddlList = FileUtils.readFileByLine(new File("/Users/mustard/BaiduSync/Temp/had-ddl.txt"));

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < fieldList.size(); i++) {
            Tuple2<Integer, String> fieldTuple = fieldList.get(i);
            Tuple2<Integer, String> ddlTuple = ddlList.get(i);

            String field = fieldTuple._2.replace(",","");
            String ddl = ddlTuple._2.replace(",","");

            if (!ddl.contains(field)) {
                log.error("error: " + i + ", field: " + field + ", ddl: " + ddl);
                return;
            }

            if (ddl.equals(field)) {
                stringBuilder.append(field);
            } else {
                stringBuilder.append(field).append(" as ").append(ddl);
            }

            stringBuilder.append(",\n");
        }

        FileUtils.writeFile("/Users/mustard/BaiduSync/Temp/had-sql.txt", stringBuilder.toString(), true);

        log.info("finish");
    }

}
