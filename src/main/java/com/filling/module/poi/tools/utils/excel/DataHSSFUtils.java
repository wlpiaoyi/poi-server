package com.filling.module.poi.tools.utils.excel;

import com.filling.framework.common.tools.PatternUtils;
import com.filling.framework.common.tools.ValueUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/20 14:21
 * {@code @version:}:       1.0
 */
public class DataHSSFUtils {


    public static void parseHSSF(HSSFWorkbook workbook, ISheetData sheetData){
        
        HSSFSheet sheet = workbook.createSheet(sheetData.sheetName());
        Map<Integer, HSSFRow> rowMap = new HashMap<>();
        for (ICellData cellData : sheetData.cellDatas()){
            if(ValueUtils.isBlank(cellData.v().getF()) && ValueUtils.isBlank(cellData.v().getV())){
                continue;
            }
            HSSFRow row = rowMap.get(cellData.getR());
            if(row == null){
                row = sheet.createRow(cellData.getR());
                rowMap.put(cellData.getR(), row);
            }
            HSSFCell cell = row.createCell(cellData.getC());
            setCell(cell, cellData);
        }
        if(ValueUtils.isNotBlank(sheetData.cellMerges())){
            setMergedRegions(sheet, sheetData.cellMerges());
        }
        setRcValue(sheet, sheetData.gridInfo());
    }

    private static void setRcValue(HSSFSheet sheet, IGridInfo gridInfo){
        if(gridInfo != null && ValueUtils.isNotBlank(gridInfo.getRowHeights())){
            int index = 0;
            for (Short value : gridInfo.getRowHeights()){
                if(sheet.getRow(index) != null){
                    sheet.getRow(index).setHeight(value);
                }
                index ++;
            }
        }
        if(gridInfo != null && ValueUtils.isNotBlank(gridInfo.getColumnWidths())){
            int index = 0;
            for (Short value : gridInfo.getColumnWidths()){
                sheet.setColumnWidth(index, value);
                index ++;
            }
        }
    }

    private static boolean setCell(HSSFCell cell, ICellData cellData){
        HSSFCellStyle cellStyle = DataHSSFUtils.setCellStyle(cell, cellData);
        HSSFFont font = DataHSSFUtils.setFont(cell, cellData);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        // 判断数据的类型
        if(ValueUtils.isNotBlank(cellData.v().getF())){
            cell.setCellFormula(cellData.v().getF().substring(1));
        }else if(ValueUtils.isNotBlank(cellData.v().getV())){
            switch (cellData.v().getType()){
                case 1:{
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue(new Double(cellData.v().getV()));
                }
                break;
                default:{
                    if(PatternUtils.isNumber(cellData.v().getV()) || PatternUtils.isFloat(cellData.v().getV())){
                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue(new Double(cellData.v().getV()));
                    }else{
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(cellData.v().getV());
                    }
                }
                break;
            }
        }else{
            return false;
        }
        return true;
    }


    private static HSSFFont setFont(HSSFCell cell, ICellData cellData) {
        HSSFFont font = cell.getRow().getSheet().getWorkbook().createFont();
        if(ValueUtils.isNotBlank(cellData.v().getFf())){
            font.setFontName(cellData.v().getFf());
        }else{
            font.setFontName("宋体");
        }
//        if(ValueUtils.isNotBlank(cellData.v().getFc())){
//            HSSFColor color = new HSSFColor();
//            color.set(hexToBytes(cellData.v().getFc()));
//            font.setColor(color);
//        }
        font.setItalic(cellData.v().getIt() == 1);
        font.setBold(cellData.v().getBl() == 1);
        if(cellData.v().getFs() > 1){
            font.setFontHeightInPoints(cellData.v().getFs());
        }else{
            font.setFontHeight((short) 10);
        }
        font.setUnderline(cellData.v().getUn());
        return font;
    }

    private static HSSFCellStyle setCellStyle(HSSFCell cell, ICellData cellData){
        HSSFCellStyle cellStyle = cell.getRow().getSheet().getWorkbook().createCellStyle();
        if(ValueUtils.isNotBlank(cellData.v().getBg())){
//            HSSFColor color = new HSSFColor();
////            color.setRGB(hexToBytes(cellData.v().getBg()));
//            cellStyle.setFillForegroundColor(color);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        //不自动换行
        cellStyle.setWrapText(false);
        setDefaultCellBorderStyle(cellStyle);

        //文字对齐方式
        switch (cellData.v().getVt()){
            case 0:{
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            }
            break;
            case 1:{
                cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
            }
            break;
            case 2:{
                cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + cellData.v().getVt());
        }
        //文字对齐方式
        switch (cellData.v().getHt()){
            case 0:{
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
            }
            break;
            case 1:{
                cellStyle.setAlignment(HorizontalAlignment.LEFT);
            }
            break;
            case 2:{
                cellStyle.setAlignment(HorizontalAlignment.RIGHT);
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + cellData.v().getVt());
        }

        return cellStyle;

    }

    private static void setDefaultCellBorderStyle(HSSFCellStyle cellStyle){
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        HSSFColor borderColor = new HSSFColor();
        cellStyle.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        cellStyle.setTopBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        cellStyle.setRightBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
    }

    /**
     * 合并单元格
     * @param sheet
     * @param cellMerges
     */
    public static void setMergedRegions(HSSFSheet sheet, List<ICellMerge> cellMerges){
        for (ICellMerge cellMerge : cellMerges) {
            for (int r = cellMerge.getFirstRow(); r <= cellMerge.getLastRow(); r++){
                for (int c = cellMerge.getFirstCol(); c <= cellMerge.getLastCol(); c++){
                    if(r == cellMerge.getFirstRow() && c == cellMerge.getFirstCol()){
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
                    setDefaultCellBorderStyle(cellStyle);
                    cell.setCellStyle(cellStyle);
                }
            }
            CellRangeAddress ra_w = new CellRangeAddress(cellMerge.getFirstRow(),
                    cellMerge.getLastRow(), cellMerge.getFirstCol()
                    , cellMerge.getLastCol());
            sheet.addMergedRegion(ra_w);
        }
    }

    private static byte[] hexToBytes(String fc) {
        if(fc.startsWith("#")){
            byte[] bytes = ValueUtils.hexToBytes(fc.substring(1));
            return bytes;
        }else if(fc.startsWith("rgb(")){
            Integer[] ints = ValueUtils.toIntegerArray(fc.substring(4, fc.length() - 1).replaceAll(" ", ""));
            byte[] bytes = new byte[]{0,0,0};
            int j = 0;
            for (int i : ints){
                bytes[j] = (byte) i;
                j++;
            }
            return bytes;
        }else{
            return new byte[]{0,0,0};
        }
    }



}
