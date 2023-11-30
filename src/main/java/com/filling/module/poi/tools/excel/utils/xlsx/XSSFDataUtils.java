package com.filling.module.poi.tools.excel.utils.xlsx;

import com.filling.module.poi.tools.excel.*;
import com.filling.module.poi.tools.excel.utils.SheetDataUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/20 14:21
 * {@code @version:}:       1.0
 */
public class XSSFDataUtils {


    public static void parseData(ISheetData sheetData, XSSFSheet sheet,
                                 Class<? extends ICellData> cdClazz,
                                 Class<? extends ICellValue> cvClass){
        SheetDataUtils.parseData(sheetData, sheet, cdClazz, cvClass, (cell, curDataStyle) -> {
            StyleXSSFDataUtils.setDataStyle(curDataStyle, (XSSFCell) cell);
        });
    }




}
