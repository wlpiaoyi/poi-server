package com.icss.poie.tools.excel.utils.xls;

import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.ISheetData;
import com.icss.poie.tools.excel.Scope;
import com.icss.poie.tools.excel.utils.DataSheetUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 13:50
 * {@code @version:}:       1.0
 */
public class DataHSSFUtils {

    public static void parseSheet(HSSFWorkbook workbook, ISheetData sheetData){
        DataSheetUtils.parseSheet(workbook, sheetData, (cell, dataStyle, cellData) -> {
            DataStyleUtils.setCellStyle((HSSFCell) cell, dataStyle);
        }, (sheet, iSheetData) -> {
            if(sheet == null){
                return;
            }
            if(iSheetData == null){
                return;
            }
            if(iSheetData.gridInfo() == null){
                return;
            }
            if(ValueUtils.isNotBlank(iSheetData.gridInfo().getCellMerges())){
                setMergedRegions((HSSFSheet) sheet, iSheetData.gridInfo().getCellMerges());
            }
            if(ValueUtils.isNotBlank(iSheetData.gridInfo().getDataValidations())){
                DataValidationUtils.setValidation((HSSFSheet) sheet, iSheetData.gridInfo().getDataValidations());
            }

        });
    }


    /**
     * 合并单元格
     * @param sheet
     * @param cellMerges
     */
    public static void setMergedRegions(HSSFSheet sheet, List<Scope> cellMerges){
        for (Scope cellMerge : cellMerges) {
            for (int r = cellMerge.getR(); r <= cellMerge.getR() + cellMerge.getRs(); r++){
                for (int c = cellMerge.getC(); c <= cellMerge.getC() + cellMerge.getCs(); c++){
                    if(r == cellMerge.getR() && c == cellMerge.getC()){
                        continue;
                    }
                    HSSFRow row = sheet.getRow(r);
                    if(row == null){
                        row = sheet.createRow(r);
                    }
                    HSSFCell cell = row.getCell(c);
                    if(cell != null){
                        continue;
                    }
                    cell = row.createCell(c);
                    HSSFCellStyle cellStyle = cell.getRow().getSheet().getWorkbook().createCellStyle();
                    DataStyleUtils.synCellBorderStyle(cellStyle);
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
