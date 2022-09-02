package org.buli.utils.excel;


import java.util.List;

public interface ExcelDataCallback<T> {

    void invoke(List<T> list);

    void finish();

}
