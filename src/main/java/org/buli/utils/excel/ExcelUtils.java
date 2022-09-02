package org.buli.utils.excel;

import com.alibaba.excel.EasyExcel;

public class ExcelUtils {

    public static void read(String filePath, int sheetNo, ExcelDataCallback<?> callback) {
        ExcelDataListener<ExcelData> dataListener = new ExcelDataListener(callback);

        EasyExcel.read(filePath, ExcelData.class, dataListener).sheet(sheetNo).doRead();
    }

}
