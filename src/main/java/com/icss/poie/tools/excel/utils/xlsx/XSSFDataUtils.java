package com.icss.poie.tools.excel.utils.xlsx;

import com.icss.poie.tools.excel.model.*;
import com.icss.poie.tools.excel.utils.SheetToDataUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.Map;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/20 14:21
 * {@code @version:}:       1.0
 */
public class XSSFDataUtils {


    public static void parseData(ISheetDataEx sheetData, XSSFSheet sheet,
                                 Class<? extends ICellData> cdClazz,
                                 Class<? extends ICellValue> cvClass){
        SheetToDataUtils.parseToData(sheetData, sheet, cdClazz, cvClass, new SheetToDataUtils.CellDataRun() {
            @Override
            public void start(ISheetDataEx sheetData, Sheet sheet) {

            }

            @Override
            public void doing(ICellData cellData, Cell cell, Map<String, StyleBase> styleBaseMap) {
                DataStyle curDataStyle = new DataStyle();
                BorderStyle curBorderStyle = new BorderStyle();
                StyleDataUtils.setDataStyle(curDataStyle, (XSSFCell) cell);
                BorderDataUtils.setBorder((XSSFCell) cell, curBorderStyle);
                styleBaseMap.put(StyleBase.KEY_CUR_DATA_STYLE_CACHE, curDataStyle);
                styleBaseMap.put(StyleBase.KEY_CUR_BORDER_DATA_CACHE, curBorderStyle);
            }

            @Override
            public void end(ISheetDataEx isheetData, Sheet xsheet) {
                isheetData.gridInfo().setDataValidations(new ArrayList<>());
                ValidationDataUtils.setData((XSSFSheet) xsheet, isheetData.gridInfo().getDataValidations());
            }
        });
//        SheetToDataUtils.parseToData(sheetData, sheet, cdClazz, cvClass, (cell, curDataStyle) -> {
//            StyleDataUtils.setDataStyle(curDataStyle, (XSSFCell) cell);
//        }, (xsheet, isheetData) ->{
//            isheetData.gridInfo().setDataValidations(new ArrayList<>());
//            ValidationDataUtils.setData((XSSFSheet) xsheet, isheetData.gridInfo().getDataValidations());
//
//        });
    }



//    static void setBorder(IGridInfo gridInfo, XSSFCell cell, curDataStyle{
//        Iterator<Row> rowIterator = sheet.rowIterator();
//        while (rowIterator.hasNext()) {
//            Row row = rowIterator.next();
//            Iterator<Cell> cellIterator = row.cellIterator();
//            while (cellIterator.hasNext()) {
//                Point point = new Point();
//                Cell cell = cellIterator.next();
//                point.setC(cell.getColumnIndex());
//                point.setR(row.getRowNum());
//                BorderDataUtils.setBorder();
//            }
//        }
//
//    }


}
