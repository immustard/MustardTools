package cn.buli_home.utils.excel;

import com.alibaba.excel.EasyExcel;

public class ExcelUtils {

    public static void read(String filePath, int sheetNo, Class clazz, ExcelDataCallback<?> callback) {
        ExcelDataListener<ExcelData> dataListener = new ExcelDataListener(callback);

        EasyExcel.read(filePath, clazz, dataListener).sheet(sheetNo).doRead();
    }

}
