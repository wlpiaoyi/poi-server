package com.icss.poie.tools.excel.utils.xls;

import com.icss.poie.tools.excel.model.*;
import com.icss.poie.tools.excel.utils.SheetToDataUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Map;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 13:51
 * {@code @version:}:       1.0
 */
public class HSSFDataUtils {


    public static void parseData(ISheetData sheetData, HSSFSheet sheet,
                                 Class<? extends ICellData> cdClazz,
                                 Class<? extends ICellValue> cvClass) {
        SheetToDataUtils.parseToData(sheetData, sheet, cdClazz, cvClass, new SheetToDataUtils.CellDataRun() {
            @Override
            public void start(ISheetData sheetData, Sheet sheet) {

            }

            @Override
            public void doing(ICellData cellData, Cell cell, Map<String, StyleBase> styleBaseMap) {
                DataStyle curDataStyle = new DataStyle();
                BorderStyle curBorderStyle = new BorderStyle();
                StyleDataUtils.setDataStyle(curDataStyle, (HSSFCell) cell);
                BorderDataUtils.setBorder((HSSFCell) cell, curBorderStyle);
                styleBaseMap.put(StyleBase.KEY_CUR_DATA_STYLE_CACHE, curDataStyle);
                styleBaseMap.put(StyleBase.KEY_CUR_BORDER_DATA_CACHE, curBorderStyle);
            }

            @Override
            public void end(ISheetData isheetData, Sheet xsheet) {
                isheetData.gridInfo().setDataValidations(new ArrayList<>());
                ValidationDataUtils.setData((HSSFSheet) xsheet, isheetData.gridInfo().getDataValidations());
            }
        });
    }
//    public static void parseData(ISheetData sheetData, HSSFSheet sheet,
//                                 Class<? extends ICellData> cdClazz,
//                                 Class<? extends ICellValue> cvClass){
//        SheetToDataUtils.parseToData(sheetData, sheet, cdClazz, cvClass, new SheetToDataUtils.CellDataRun() {
//            @Override
//            public void start(ISheetData sheetData, Sheet sheet) {
//
//            }
//
//            @Override
//            public void doing(ICellData cellData, Cell cell, Map<String, StyleBase> styleBaseMap) {
//                DataStyle curDataStyle = new DataStyle();
//                StyleDataUtils.setDataStyle(curDataStyle, (HSSFCell) cell);
//                styleBaseMap.put(StyleBase.KEY_CUR_DATA_STYLE_CACHE, curDataStyle);
//            }
//
//            @Override
//            public void end(ISheetData isheetData, Sheet hsheet) {
//                isheetData.gridInfo().setDataValidations(new ArrayList<>());
//                ValidationDataUtils.setData((HSSFSheet) hsheet, isheetData.gridInfo().getDataValidations());
//            }
//        });
//    }
}
