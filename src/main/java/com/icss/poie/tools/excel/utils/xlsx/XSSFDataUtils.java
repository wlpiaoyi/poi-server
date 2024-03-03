package com.icss.poie.tools.excel.utils.xlsx;

import com.icss.poie.tools.excel.model.*;
import com.icss.poie.tools.excel.utils.SheetDataUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.Iterator;

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
