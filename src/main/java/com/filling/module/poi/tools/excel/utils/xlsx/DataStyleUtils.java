package com.filling.module.poi.tools.excel.utils.xlsx;

import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.tools.excel.DataStyle;
import com.filling.module.poi.tools.excel.utils.DataSheetUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 10:28
 * {@code @version:}:       1.0
 */
class DataStyleUtils {


    static XSSFCellStyle setCellStyle(XSSFCell cell, DataStyle dataStyle){
        XSSFCellStyle cellStyle = cell.getRow().getSheet().getWorkbook().createCellStyle();
        //单元格背景
        if(ValueUtils.isNotBlank(dataStyle.getBg())){
            XSSFColor color = new XSSFColor();
            color.setRGB(DataSheetUtils.hexToBytes(dataStyle.getBg()));
            cellStyle.setFillForegroundColor(color);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        if(dataStyle.getDfm() > 0 && ValueUtils.isNotBlank(dataStyle.getDfmv())){
            XSSFDataFormat dataFormat = (XSSFDataFormat) dataStyle.getDataFormat();
            if(dataFormat == null){
                dataFormat = cell.getSheet().getWorkbook().createDataFormat();
                dataStyle.setDataFormat(dataFormat);
            }
            cellStyle.setDataFormat(dataFormat.getFormat(dataStyle.getDfmv()));
        }
        //自动换行
        cellStyle.setWrapText(dataStyle.getTb() == 2);
        //文字对齐方式
        switch (dataStyle.getVt()){
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
                throw new IllegalStateException("Unexpected value: " + dataStyle.getVt());
        }
        //文字对齐方式
        switch (dataStyle.getHt()){
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
                throw new IllegalStateException("Unexpected value: " + dataStyle.getVt());
        }

        XSSFFont font = cell.getRow().getSheet().getWorkbook().createFont();
        synFont(font, dataStyle);
        cellStyle.setFont(font);
        synCellBorderStyle(cellStyle);
        cell.setCellStyle(cellStyle);
        return cellStyle;

    }


    static void synCellBorderStyle(XSSFCellStyle cellStyle){
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        XSSFColor borderColor = new XSSFColor();
        borderColor.setRGB(ValueUtils.hexToBytes("A1A1A1"));
        cellStyle.setBottomBorderColor(borderColor);
        cellStyle.setTopBorderColor(borderColor);
        cellStyle.setLeftBorderColor(borderColor);
        cellStyle.setRightBorderColor(borderColor);
    }

    static void synFont(XSSFFont font, DataStyle dataStyle) {
        if(ValueUtils.isNotBlank(dataStyle.getFf())){
            font.setFontName(dataStyle.getFf());
        }else{
            font.setFontName("宋体");
        }
        if(ValueUtils.isNotBlank(dataStyle.getFc())){
            XSSFColor color = new XSSFColor();
            color.setRGB(DataSheetUtils.hexToBytes(dataStyle.getFc()));
            font.setColor(color);
        }
        font.setItalic(dataStyle.getIt() == 1);
        font.setBold(dataStyle.getBl() == 1);
        if(dataStyle.getFs() > 1){
            font.setFontHeightInPoints(dataStyle.getFs());
        }else{
            font.setFontHeight(10);
        }
        font.setUnderline(dataStyle.getUn());
    }

}
