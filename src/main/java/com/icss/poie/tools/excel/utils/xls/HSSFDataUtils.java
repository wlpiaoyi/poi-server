package com.icss.poie.tools.excel.utils.xls;

import com.icss.poie.tools.excel.model.*;
import com.icss.poie.tools.excel.utils.SheetToDataUtils;
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
        SheetToDataUtils.parseToData(sheetData, sheet, cdClazz, cvClass, (cellData, cell, styleBaseMap) -> {
            DataStyle curDataStyle = new DataStyle();
            StyleDataUtils.setDataStyle(curDataStyle, (HSSFCell) cell);
            styleBaseMap.put(StyleBase.KEY_CUR_DATA_STYLE_CACHE, curDataStyle);
        }, (isheetData, hsheet)->{
            isheetData.gridInfo().setDataValidations(new ArrayList<>());
            ValidationDataUtils.setData((HSSFSheet) hsheet, isheetData.gridInfo().getDataValidations());
        });
    }
}
