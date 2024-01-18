package cn.buli_home.utils.excel;

import com.alibaba.excel.EasyExcel;

import java.io.InputStream;

public class ExcelUtils {

    public static void read(String filePath, int sheetNo, Class clazz, ExcelDataCallback<?> callback) {
        ExcelDataListener<ExcelData> dataListener = new ExcelDataListener(callback);

        EasyExcel.read(filePath, clazz, dataListener).sheet(sheetNo).doRead();
    }

    public static void read(InputStream in, int sheetNo, Class clazz, ExcelDataCallback<?> callback) {
        ExcelDataListener<ExcelData> dataListener = new ExcelDataListener(callback);

        EasyExcel.read(in, clazz, dataListener).sheet(sheetNo).doRead();
    }

}
