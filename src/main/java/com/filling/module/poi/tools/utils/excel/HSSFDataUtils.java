package com.filling.module.poi.tools.utils.excel;

import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.excel.domain.model.GridInfo;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/20 14:21
 * {@code @version:}:       1.0
 */
public class HSSFDataUtils {


    @SneakyThrows
    public static void parseHSSF(ISheetData sheetData, HSSFSheet sheet,
                                 Class<? extends ICellMerge> cmClazz,
                                 Class<? extends ICellData> cdClazz,
                                 Class<? extends ICellValue> cvClass){
        sheetData.putSheetName(sheet.getSheetName());
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<ICellData> cellDatas = new ArrayList<>();
        sheetData.putCellDatas(cellDatas);
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                HSSFCell cell = (HSSFCell) cellIterator.next();
                ICellData cellData = cdClazz.newInstance();
                setCell(cellData, cell, cvClass);
                cellDatas.add(cellData);
            }
        }
        sheetData.putCellMerges(new ArrayList<>());
        setMergedRegions(sheetData.cellMerges(), sheet, cmClazz);
        sheetData.putGridInfo(new GridInfo());
        setRcValue(sheetData.gridInfo(), sheet);
    }


    private static void setRcValue(IGridInfo gridInfo, HSSFSheet sheet){
        Iterator<Row> rowIterator = sheet.rowIterator();
        gridInfo.setRowHeights(new ArrayList<>());
        gridInfo.setColumnWidths(new ArrayList<>());
        int maxC = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            maxC = Math.max(maxC, row.getLastCellNum());
            short value = (short) Math.max(10, row.getHeight());
            gridInfo.getRowHeights().add(value);
        }
        for (int i = 0; i <= maxC; i ++){
            gridInfo.getColumnWidths().add((short) sheet.getColumnWidth(i));
        }
    }

    private static void setMergedRegions(List<ICellMerge> cellMerges, HSSFSheet sheet, Class<? extends ICellMerge> cmClazz) throws InstantiationException, IllegalAccessException {
        for (ICellMerge cellMerge : cellMerges) {
            CellRangeAddress ra_w = new CellRangeAddress(cellMerge.getFirstRow(),
                    cellMerge.getLastRow(), cellMerge.getFirstCol()
                    , cellMerge.getLastCol());
            sheet.addMergedRegion(ra_w);
        }
        int count = sheet.getNumMergedRegions();
        for (int i = 0; i < count; i++) {
            CellRangeAddress rangeAddress = sheet.getMergedRegion(i);
            ICellMerge cellMerge = cmClazz.newInstance();
            cellMerge.setFirstCol(rangeAddress.getFirstColumn());
            cellMerge.setFirstRow(rangeAddress.getFirstRow());
            cellMerge.setLastCol(rangeAddress.getLastColumn());
            cellMerge.setLastRow(rangeAddress.getLastRow());
            cellMerges.add(cellMerge);
        }
    }

    private static boolean setCell(ICellData cellData, HSSFCell cell, Class<? extends ICellValue> cvClass) throws InstantiationException, IllegalAccessException {
        cellData.setR(cell.getRowIndex());
        cellData.setC(cell.getColumnIndex());
        cellData.putV(cvClass.newInstance());

        HSSFDataUtils.setCellStyle(cellData, cell);
        HSSFDataUtils.setFont(cellData, cell);

        switch (cell.getCellType()) {
            case NUMERIC:{
                cellData.v().setType(1);
                cellData.v().setV(new BigDecimal(cell.getNumericCellValue()).toString());
            }
                break;
            case STRING:{
                cellData.v().setType(0);
                cellData.v().setV(cell.getStringCellValue());
            }
                break;
            case BOOLEAN:{
                cellData.v().setType(1);
                cellData.v().setV(new Boolean(cell.getStringCellValue()).toString());
            }
                break;
            case FORMULA:{
                cellData.v().setF("=" + cell.getCellFormula());
            }
                break;
            case BLANK:
//                cellData.v().setV("");
                break;
            default:
                cellData.v().setV("非法字符");
                break;
        }
        return true;
    }


    private static void setFont(ICellData cellData, HSSFCell cell) {
        HSSFFont font = cell.getCellStyle().getFont(cell.getSheet().getWorkbook());
        if(font == null){
            return;
        }
        if(ValueUtils.isNotBlank(font.getFontName())){
            cellData.v().setFf(font.getFontName());
        }else{
            cellData.v().setFf("宋体");
        }
        cellData.v().setIt(font.getItalic() ? 1 : 0);
        cellData.v().setBl(font.getBold() ? 1 : 0);
        cellData.v().setUn(font.getUnderline());
        if(font.getFontHeight() > 1){
            cellData.v().setFs(font.getFontHeightInPoints());
        }else{
            cellData.v().setFs((short) 10);
        }
    }

    private static HSSFCellStyle setCellStyle(ICellData cellData, HSSFCell cell){
        HSSFCellStyle cellStyle = cell.getCellStyle();
        cellData.v().setBg("#FFFFFF");
        switch (cellStyle.getVerticalAlignment()){
            case TOP:{
                cellData.v().setVt(1);
            }
            break;
            case BOTTOM:{
                cellData.v().setVt(2);
            }
            break;
            default:{
                cellData.v().setVt(0);
            }
        }
        switch (cellStyle.getAlignment()){
            case LEFT:{
                cellData.v().setHt(1);
            }
            break;
            case RIGHT:{
                cellData.v().setHt(2);
            }
            break;
            default:{
                cellData.v().setHt(0);
            }
        }

        return cellStyle;

    }



}
