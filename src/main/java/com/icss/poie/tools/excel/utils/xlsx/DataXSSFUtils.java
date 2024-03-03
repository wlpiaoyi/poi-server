package com.icss.poie.tools.excel.utils.xlsx;

import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.model.ISheetData;
import com.icss.poie.tools.excel.model.Scope;
import com.icss.poie.tools.excel.utils.DataSheetUtils;
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

    /**
     *
     * @param workbook
     * @param sheetData
     * @return: void
     * @author: wlpia
     * @date: 2023/12/25 11:46
     */
    public static void parseSheet(XSSFWorkbook workbook, ISheetData sheetData){
        DataSheetUtils.parseSheet(workbook, sheetData, (cell, dataStyle, cellData) -> {
            DataStyleUtils.setCellStyle((XSSFCell) cell, dataStyle);
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
                setMergedRegions((XSSFSheet) sheet, iSheetData.gridInfo().getCellMerges());
            }
            if(ValueUtils.isNotBlank(iSheetData.gridInfo().getDataValidations())){
                DataValidationUtils.setValidation((XSSFSheet) sheet, iSheetData.gridInfo().getDataValidations());
            }
        });
    }


    /**
     * 合并单元格
     * @param sheet
     * @param cellMerges
     * @return: void
     * @author: wlpia
     * @date: 2023/12/25 11:45
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
