package com.icss.poie.tools.excel.utils.xls;

import com.icss.poie.tools.excel.ICellData;
import com.icss.poie.tools.excel.ICellValue;
import com.icss.poie.tools.excel.ISheetData;
import com.icss.poie.tools.excel.utils.SheetDataUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.ArrayList;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 13:51
 * {@code @version:}:       1.0
 */
public class HSSFDataUtils {

    public static void parseData(ISheetData sheetData, HSSFSheet sheet,
                                 Class<? extends ICellData> cdClazz,
                                 Class<? extends ICellValue> cvClass){
        SheetDataUtils.parseData(sheetData, sheet, cdClazz, cvClass, (cell, curDataStyle) -> {
            StyleDataUtils.setDataStyle(curDataStyle, (HSSFCell) cell);
        }, (hsheet, isheetData)->{
            isheetData.gridInfo().setDataValidations(new ArrayList<>());
            ValidationDataUtils.setData((HSSFSheet) hsheet, isheetData.gridInfo().getDataValidations());
        });
    }
}
