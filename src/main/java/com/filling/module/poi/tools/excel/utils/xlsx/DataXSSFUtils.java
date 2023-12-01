package com.filling.module.poi.tools.excel.utils.xlsx;

import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.tools.excel.*;
import com.filling.module.poi.tools.excel.utils.DataSheetUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/20 14:21
 * {@code @version:}:       1.0
 */
public class DataXSSFUtils {


    public static void parseSheet(XSSFWorkbook workbook, ISheetData sheetData){
        DataSheetUtils.parseSheet(workbook, sheetData, (cell, dataStyle, cellData) -> {
            DataStyleXSSFUtils.setCellStyle((XSSFCell) cell, dataStyle);
        }, (sheet, gridInfo) -> {
            if(ValueUtils.isNotBlank(gridInfo.getCellMerges())){
                setMergedRegions((XSSFSheet) sheet, gridInfo.getCellMerges());
            }
        });
    }


    /**
     * 合并单元格
     * @param sheet
     * @param cellMerges
     */
    public static void setMergedRegions(XSSFSheet sheet, List<Scope> cellMerges){
        for (Scope cellMerge : cellMerges) {
            for (int r = cellMerge.getR(); r <= cellMerge.getR() + cellMerge.getRs(); r++){
                for (int c = cellMerge.getC(); c <= cellMerge.getC() + cellMerge.getCs(); c++){
                    if(r == cellMerge.getR() && c == cellMerge.getC()){
                        continue;
                    }
                    XSSFRow row = sheet.getRow(r);
                    if(row == null){
                        row = sheet.createRow(r);
                    }
                    XSSFCell cell = row.getCell(c);
                    if(cell != null){
                        continue;
                    }
                    cell = row.createCell(c);
                    XSSFCellStyle cellStyle = cell.getRow().getSheet().getWorkbook().createCellStyle();
                    DataStyleXSSFUtils.synCellBorderStyle(cellStyle);
                    cell.setCellStyle(cellStyle);
                }
            }
            CellRangeAddress ra_w = new CellRangeAddress(cellMerge.getR(),
                    cellMerge.getR() + cellMerge.getRs(), cellMerge.getC(),
                    cellMerge.getC() + cellMerge.getCs());
            sheet.addMergedRegion(ra_w);
        }
    }
}
