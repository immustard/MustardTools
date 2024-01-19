package cn.buli_home.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExcelUtils {

    private static final int WRITE_BATCH_COUNT = 3000;

    public static <T> void read(String filePath, int sheetNo, Class<T> clazz, ExcelDataCallback<T> callback) {
        read(filePath, sheetNo, clazz, 1, callback);
    }

    public static <T> void read(InputStream in, int sheetNo, Class<T> clazz, ExcelDataCallback<T> callback) {
        read(in, sheetNo, clazz, 1, callback);
    }

    public static <T> void read(String filePath, int sheetNo, Class<T> clazz, int headRowCount, ExcelDataCallback<T> callback) {
        ExcelDataListener<T> dataListener = new ExcelDataListener<>(callback);

        EasyExcel.read(filePath, clazz, dataListener).sheet(sheetNo).headRowNumber(headRowCount).doRead();
    }

    public static <T> void read(InputStream in, int sheetNo, Class<T> clazz, int headRowCount, ExcelDataCallback<T> callback) {
        ExcelDataListener<T> dataListener = new ExcelDataListener<>(callback);

        EasyExcel.read(in, clazz, dataListener).sheet(sheetNo).headRowNumber(headRowCount).doRead();
    }

    public static <T> List<T> readSync(String filePath, int sheetNo, Class<T> clazz) {
        return readSync(filePath, sheetNo, clazz, 1);
    }

    public static <T> List<T> readSync(InputStream in, int sheetNo, Class<T> clazz) {
        return readSync(in, sheetNo, clazz, 1);
    }

    public static <T> List<T> readSync(String filePath, int sheetNo, Class<T> clazz, int headRowCount) {
        ExcelDataListener<T> dataListener = new ExcelDataListener<>();

        return EasyExcel.read(filePath, clazz, dataListener).sheet(sheetNo).headRowNumber(headRowCount).doReadSync();
    }

    public static <T> List<T> readSync(InputStream in, int sheetNo, Class<T> clazz, int headRowCount) {
        ExcelDataListener<T> dataListener = new ExcelDataListener<>();

        return EasyExcel.read(in, clazz, dataListener).sheet(sheetNo).headRowNumber(headRowCount).doReadSync();
    }

    public static <T> void fillOnce(String filePath, String templatePath, int sheetNo, List<T> data) {
        EasyExcel.write(filePath).withTemplate(templatePath).sheet(sheetNo).doFill(data);
    }

    public static <T> void fill(String filePath, String templatePath, int sheetNo, List<T> data) {
        try (ExcelWriter excelWriter = EasyExcel.write(filePath).withTemplate(templatePath).build()) {
            p_fill(excelWriter, sheetNo, data);
        }
    }

    public static <T> void fillOnce(OutputStream os, InputStream templateIS, int sheetNo, List<T> data) {
        EasyExcel.write(os).withTemplate(templateIS).sheet(sheetNo).doFill(data);
    }

    public static <T> void fill(OutputStream os, InputStream templateIS, int sheetNo, List<T> data) {
        try (ExcelWriter excelWriter = EasyExcel.write(os).withTemplate(templateIS).build()) {
            p_fill(excelWriter, sheetNo, data);
        }
    }

    private static <T> void p_fill(ExcelWriter excelWriter, int sheetNo, List<T> data) {
        WriteSheet sheet = EasyExcel.writerSheet(sheetNo).build();
        int limit = (data.size() + WRITE_BATCH_COUNT - 1) / WRITE_BATCH_COUNT;

        Stream.iterate(0, n -> n+1).limit(limit).forEach(i -> {
            List<T> writeList = data.stream().skip(i * WRITE_BATCH_COUNT).limit(WRITE_BATCH_COUNT).collect(Collectors.toList());
            excelWriter.fill(writeList, sheet);
        });
    }
}
