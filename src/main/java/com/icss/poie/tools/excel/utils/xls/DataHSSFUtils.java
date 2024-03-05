package com.icss.poie.tools.excel.utils.xls;

import com.icss.poie.framework.common.tools.MapUtils;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.model.*;
import com.icss.poie.tools.excel.utils.DataToSheetUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;
import java.util.Map;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 13:50
 * {@code @version:}:       1.0
 */
public class DataHSSFUtils {

    public static void parseSheet(HSSFWorkbook workbook, ISheetData sheetData){
        DataToSheetUtils.parseSheet(workbook, sheetData, new DataToSheetUtils.CellDataRun() {
            @Override
            public void start(Sheet sheet, ISheetData sheetData) {

            }

            @Override
            public void doing(Cell cell, ICellData cellData, Map<String, Object> styleBaseMap) {
                DataStyle curDataStyle = MapUtils.get(styleBaseMap, StyleBase.KEY_CUR_DATA_STYLE_CACHE);
                DataStyleUtils.setCellStyle((HSSFCell) cell, curDataStyle);
            }

            @Override
            public void end(Sheet hsheet, ISheetData iSheetData) {
                if(hsheet == null){
                    return;
                }
                if(iSheetData == null){
                    return;
                }
                if(iSheetData.gridInfo() == null){
                    return;
                }
                if(ValueUtils.isNotBlank(iSheetData.gridInfo().getCellMerges())){
                    setMergedRegions((HSSFSheet) hsheet, iSheetData.gridInfo().getCellMerges());
                }
                if(ValueUtils.isNotBlank(iSheetData.gridInfo().getDataValidations())){
                    DataValidationUtils.setValidation((HSSFSheet) hsheet, iSheetData.gridInfo().getDataValidations());
                }
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
