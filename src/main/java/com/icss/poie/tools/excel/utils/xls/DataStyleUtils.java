package com.icss.poie.tools.excel.utils.xls;

import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.DataStyle;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 13:29
 * {@code @version:}:       1.0
 */
public class DataStyleUtils {

    static HSSFCellStyle setCellStyle(HSSFCell cell, DataStyle dataStyle){
        HSSFCellStyle cellStyle = cell.getRow().getSheet().getWorkbook().createCellStyle();
        //单元格背景
        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //自动换行
        cellStyle.setWrapText(dataStyle.getTb() == 2);
        cellStyle.setDataFormat(dataStyle.getDfm());


        if(dataStyle.getDfm() > 0 && ValueUtils.isNotBlank(dataStyle.getDfmv())){
            HSSFDataFormat dataFormat = (HSSFDataFormat) dataStyle.getDataFormat();
            if(dataFormat == null){
                dataFormat = cell.getSheet().getWorkbook().createDataFormat();
                dataStyle.setDataFormat(dataFormat);
            }
            cellStyle.setDataFormat(dataFormat.getFormat(dataStyle.getDfmv()));
        }
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

        HSSFFont font = cell.getRow().getSheet().getWorkbook().createFont();
        synFont(font, dataStyle);
        cellStyle.setFont(font);
        synCellBorderStyle(cellStyle);
        cell.setCellStyle(cellStyle);
        return cellStyle;

    }


    static void synCellBorderStyle(HSSFCellStyle cellStyle){
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        cellStyle.setBottomBorderColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        cellStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        cellStyle.setLeftBorderColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        cellStyle.setRightBorderColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
    }
    
    static void synFont(HSSFFont font, DataStyle dataStyle) {
        if(ValueUtils.isNotBlank(dataStyle.getFf())){
            font.setFontName(dataStyle.getFf());
        }else{
            font.setFontName("宋体");
        }
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setItalic(dataStyle.getIt() == 1);
        font.setBold(dataStyle.getBl() == 1);
        if(dataStyle.getFs() > 1){
            font.setFontHeightInPoints(dataStyle.getFs());
        }else{
            font.setFontHeight((short) 10);
        }
        font.setUnderline(dataStyle.getUn());
    }

}
