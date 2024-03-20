package com.icss.poie.tools.excel.utils.xls;

import com.icss.poie.framework.common.tools.MapUtils;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.model.*;
import com.icss.poie.tools.excel.utils.DataToSheetUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
    /**
     * <p><b>{@code @description:}</b>
     * 将数据转成Excel
     * </p>
     *
     * <p><b>@param</b> <b>workbook</b>
     * {@link HSSFWorkbook}
     * </p>
     *
     * <p><b>@param</b> <b>sheetData</b>
     * {@link ISheetDataEx}
     * </p>
     *
     * <p><b>{@code @date:}</b>2023/12/25 11:46</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    public static void parseSheet(HSSFWorkbook workbook, ISheetDataEx sheetData){
        DataToSheetUtils.parseSheet(workbook, sheetData, new DataToSheetUtils.CellDataRun() {
            @Override
            public void start(Sheet sheet, ISheetDataEx sheetData) {

            }

            @Override
            public void doing(Cell cell, ICellData cellData, Map<String, Object> styleBaseMap) {
                DataStyle curDataStyle = MapUtils.get(styleBaseMap, StyleBase.KEY_CUR_DATA_STYLE_CACHE);
                BorderStyle curBorderStyle = MapUtils.get(styleBaseMap, StyleBase.KEY_CUR_BORDER_DATA_CACHE);
                ICacheMap cacheMap = MapUtils.get(styleBaseMap, StyleBase.KEY_CACHE_MAP_CACHE);
                String ccskey = "blank";
                if(curBorderStyle != null){
                    ccskey = curBorderStyle.toString();
                }
                CellStyle cellStyle = null;
                if(curDataStyle != null){
                    cellStyle = MapUtils.get(curDataStyle.getCacheCellStyle(), ccskey);
                    if(cellStyle == null){
                        cellStyle = cell.getRow().getSheet().getWorkbook().createCellStyle();
                        DataStyleUtils.setCellStyle((HSSFCell) cell, ((HSSFCellStyle) cellStyle), curDataStyle, cacheMap);
                        curDataStyle.getCacheCellStyle().put(ccskey, cellStyle);
                    }
                }
                if(curBorderStyle != null && cellStyle != null){
                    DataBorderUtils.setBorder(curBorderStyle, ((HSSFCellStyle) cellStyle), sheetData.cacheMap());
                }else if(cellStyle != null){
                    DataBorderUtils.setBorderDefault((HSSFCellStyle) cellStyle);
                }
                cell.setCellStyle(cellStyle);
            }

            @Override
            public void end(Sheet xsheet, ISheetDataEx iSheetData) {
                if(xsheet == null){
                    return;
                }
                if(iSheetData == null){
                    return;
                }
                if(iSheetData.gridInfo() == null){
                    return;
                }
                if(ValueUtils.isNotBlank(iSheetData.gridInfo().getCellMerges())){
                    setMergedRegions((HSSFSheet) xsheet, iSheetData.gridInfo().getCellMerges());
                }
                if(ValueUtils.isNotBlank(iSheetData.gridInfo().getDataValidations())){
                    DataValidationUtils.setValidation((HSSFSheet) xsheet, iSheetData.gridInfo().getDataValidations());
                }
            }
        });
    }

    /**
     * <p><b>{@code @description:}</b>
     * 合并单元格
     * </p>
     *
     * <p><b>@param</b> <b>sheet</b>
     * {@link HSSFSheet}
     * </p>
     *
     * <p><b>@param</b> <b>cellMerges</b>
     * {@link List<Scope>}
     * </p>
     *
     * <p><b>{@code @date:}</b>2023/12/25 11:45</p>
     * <p><b>{@code @author:}</b>wlpia</p>
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
                    cell.setCellStyle(cellStyle);
                }
            }
            CellRangeAddress ra_w = new CellRangeAddress(cellMerge.getR(),
                    cellMerge.getR() + cellMerge.getRs(), cellMerge.getC(),
                    cellMerge.getC() + cellMerge.getCs());
            sheet.addMergedRegion(ra_w);
        }
    }
    
//    public static void parseSheet(HSSFWorkbook workbook, ISheetData sheetData){
//        DataToSheetUtils.parseSheet(workbook, sheetData, new DataToSheetUtils.CellDataRun() {
//            @Override
//            public void start(Sheet sheet, ISheetData sheetData) {
//
//            }
//
//            @Override
//            public void doing(Cell cell, ICellData cellData, Map<String, Object> styleBaseMap) {
//                DataStyle curDataStyle = MapUtils.get(styleBaseMap, StyleBase.KEY_CUR_DATA_STYLE_CACHE);
//                DataStyleUtils.setCellStyle((HSSFCell) cell, curDataStyle);
//            }
//
//            @Override
//            public void end(Sheet hsheet, ISheetData iSheetData) {
//                if(hsheet == null){
//                    return;
//                }
//                if(iSheetData == null){
//                    return;
//                }
//                if(iSheetData.gridInfo() == null){
//                    return;
//                }
//                if(ValueUtils.isNotBlank(iSheetData.gridInfo().getCellMerges())){
//                    setMergedRegions((HSSFSheet) hsheet, iSheetData.gridInfo().getCellMerges());
//                }
//                if(ValueUtils.isNotBlank(iSheetData.gridInfo().getDataValidations())){
//                    DataValidationUtils.setValidation((HSSFSheet) hsheet, iSheetData.gridInfo().getDataValidations());
//                }
//            }
//        });
//    }
//
//
//    /**
//     * 合并单元格
//     * @param sheet
//     * @param cellMerges
//     */
//    public static void setMergedRegions(HSSFSheet sheet, List<Scope> cellMerges){
//        for (Scope cellMerge : cellMerges) {
//            for (int r = cellMerge.getR(); r <= cellMerge.getR() + cellMerge.getRs(); r++){
//                for (int c = cellMerge.getC(); c <= cellMerge.getC() + cellMerge.getCs(); c++){
//                    if(r == cellMerge.getR() && c == cellMerge.getC()){
//                        continue;
//                    }
//                    HSSFRow row = sheet.getRow(r);
//                    if(row == null){
//                        row = sheet.createRow(r);
//                    }
//                    HSSFCell cell = row.getCell(c);
//                    if(cell != null){
//                        continue;
//                    }
//                    cell = row.createCell(c);
//                    HSSFCellStyle cellStyle = cell.getRow().getSheet().getWorkbook().createCellStyle();
//                    DataStyleUtils.synCellBorderStyle(cellStyle);
//                    cell.setCellStyle(cellStyle);
//                }
//            }
//            CellRangeAddress ra_w = new CellRangeAddress(cellMerge.getR(),
//                    cellMerge.getR() + cellMerge.getRs(), cellMerge.getC(),
//                    cellMerge.getC() + cellMerge.getCs());
//            sheet.addMergedRegion(ra_w);
//        }
//    }
}
