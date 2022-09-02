package org.buli.utils.excel;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class ExcelData {

    private String string;

    private Date date;

    private Double doubleData;

}
