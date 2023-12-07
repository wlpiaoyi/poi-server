package com.icss.poie.tools.excel.utils.xlsx;

import com.icss.poie.tools.excel.ICellData;
import com.icss.poie.tools.excel.ISheetData;
import com.icss.poie.tools.excel.utils.SheetDataUtils;
import com.icss.poie.tools.excel.ICellValue;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;

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
            StyleDataUtils.setDataStyle(curDataStyle, (XSSFCell) cell);
        }, (xsheet, isheetData) ->{
            isheetData.gridInfo().setDataValidations(new ArrayList<>());
            ValidationDataUtils.setData((XSSFSheet) xsheet, isheetData.gridInfo().getDataValidations());
        });
    }




}
