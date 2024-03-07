package com.icss.poie.tools.excel.utils.xlsx;

import com.icss.poie.framework.common.tools.MapUtils;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.model.*;
import com.icss.poie.tools.excel.utils.DataToSheetUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.util.List;
import java.util.Map;


/**
 * <p><b>{@code @description:}</b>  </p>
 * <p><b>{@code @date:}</b>         2023/9/20 14:21</p>
 * <p><b>{@code @author:}</b>       wlpiaoyi</p>
 * <p><b>{@code @version:}</b>      1.0</p>
 */
public class DataXSSFUtils {

    /**
     * <p><b>{@code @description:}</b>
     * 将数据转成Excel
     * </p>
     *
     * <p><b>@param</b> <b>workbook</b>
     * {@link XSSFWorkbook}
     * </p>
     *
     * <p><b>@param</b> <b>sheetData</b>
     * {@link ISheetData}
     * </p>
     *
     * <p><b>{@code @date:}</b>2023/12/25 11:46</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    public static void parseSheet(XSSFWorkbook workbook, ISheetData sheetData){
        DataToSheetUtils.parseSheet(workbook, sheetData, new DataToSheetUtils.CellDataRun() {
            @Override
            public void start(Sheet sheet, ISheetData sheetData) {

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
                        DataStyleUtils.setCellStyle((XSSFCell) cell, ((XSSFCellStyle) cellStyle), curDataStyle, cacheMap);
                        curDataStyle.getCacheCellStyle().put(ccskey, cellStyle);
                    }
                }
                if(curBorderStyle != null && cellStyle != null){
                    DataBorderUtils.setBorder(curBorderStyle, ((XSSFCellStyle) cellStyle), sheetData.cacheMap());
                }else if(cellStyle != null){
                    DataBorderUtils.setBorderDefault((XSSFCellStyle) cellStyle);
                }
                cell.setCellStyle(cellStyle);
            }

            @Override
            public void end(Sheet xsheet, ISheetData iSheetData) {
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
                    setMergedRegions((XSSFSheet) xsheet, iSheetData.gridInfo().getCellMerges());
                }
                if(ValueUtils.isNotBlank(iSheetData.gridInfo().getDataValidations())){
                    DataValidationUtils.setValidation((XSSFSheet) xsheet, iSheetData.gridInfo().getDataValidations());
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
     * {@link XSSFSheet}
     * </p>
     *
     * <p><b>@param</b> <b>cellMerges</b>
     * {@link List<Scope>}
     * </p>
     *
     * <p><b>{@code @date:}</b>2023/12/25 11:45</p>
     * <p><b>{@code @author:}</b>wlpia</p>
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
