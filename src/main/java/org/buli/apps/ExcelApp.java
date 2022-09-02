package org.buli.apps;

import com.alibaba.fastjson2.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.buli.utils.excel.ExcelData;
import org.buli.utils.excel.ExcelDataCallback;
import org.buli.utils.excel.ExcelUtils;

import java.util.List;

public class ExcelApp {

    private static final Logger log = LogManager.getLogger(ExcelApp.class);

    public static void main(String[] args) throws Exception {
        ExcelUtils.read("/Users/mustard/Downloads/test.xlsx", 0, new ExcelDataCallback<ExcelData>() {
            @Override
            public void invoke(List<ExcelData> list) {
                for (ExcelData excelData : list) {
                    log.info(JSON.toJSONString(excelData));
                }
            }

            @Override
            public void finish() {

            }
        });
    }

}
