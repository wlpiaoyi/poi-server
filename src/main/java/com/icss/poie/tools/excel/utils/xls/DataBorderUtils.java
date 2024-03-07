package com.icss.poie.tools.excel.utils.xls;

import com.icss.poie.tools.excel.model.BorderStyle;
import com.icss.poie.tools.excel.model.ICacheMap;
import com.icss.poie.tools.excel.utils.ExcelUtils;
import com.icss.poie.tools.excel.utils.xlsx.ColorIndex;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.awt.*;

/**
 * <p><b>{@code @description:}</b>  向xlsx写入边框信息</p>
 * <p><b>{@code @date:}</b>         2024-02-27 19:00:02</p>
 * <p><b>{@code @author:}</b>       wlpiaoyi</p>
 * <p><b>{@code @version:}</b>      1.0</p>
 */
public class DataBorderUtils {


    /**
     * <p><b>{@code @description:}</b>  TODO</p>
     * <p><b>{@code @date:}</b>         2024/2/27 19:01</p>
     * <p><b>{@code @author:}</b>       wlpiaoyi</p>
     * <p><b>{@code @version:}</b>      1.0</p>
     */
    static void setBorder(BorderStyle borderData, HSSFCellStyle cellStyle, ICacheMap cacheMap){
        BorderStyle.Border border = borderData.getTop();
        if(border != null){
            if(border.getColor() != null){
                HSSFColor color = cacheMap.getCacheHSSFColor(border.getColor());
                cellStyle.setTopBorderColor(color.getIndex());
            }
            if(border.getStyleCode() != 0){
                org.apache.poi.ss.usermodel.BorderStyle borderStyle = org.apache.poi.ss.usermodel.BorderStyle.valueOf(border.getStyleCode());
                cellStyle.setBorderTop(borderStyle);
            }
        }
        border = borderData.getRight();
        if(border != null){
            if(border.getColor() != null){
                HSSFColor color = cacheMap.getCacheHSSFColor(border.getColor());
                cellStyle.setRightBorderColor(color.getIndex());
            }
            if(border.getStyleCode() != 0){
                org.apache.poi.ss.usermodel.BorderStyle borderStyle = org.apache.poi.ss.usermodel.BorderStyle.valueOf(border.getStyleCode());
                cellStyle.setBorderRight(borderStyle);
            }
        }
        border = borderData.getBottom();
        if(border != null){
            if(border.getColor() != null){
                HSSFColor color = cacheMap.getCacheHSSFColor(border.getColor());
                cellStyle.setBottomBorderColor(color.getIndex());
            }
            if(border.getStyleCode() != 0){
                org.apache.poi.ss.usermodel.BorderStyle borderStyle = org.apache.poi.ss.usermodel.BorderStyle.valueOf(border.getStyleCode());
                cellStyle.setBorderBottom(borderStyle);
            }
        }
        border = borderData.getLeft();
        if(border != null){
            if(border.getColor() != null){
                HSSFColor color = cacheMap.getCacheHSSFColor(border.getColor());
                cellStyle.setLeftBorderColor(color.getIndex());
            }
            if(border.getStyleCode() != 0){
                org.apache.poi.ss.usermodel.BorderStyle borderStyle = org.apache.poi.ss.usermodel.BorderStyle.valueOf(border.getStyleCode());
                cellStyle.setBorderLeft(borderStyle);
            }
        }
    }

    static void setBorderDefault(HSSFCellStyle cellStyle){
        cellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        cellStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        cellStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        cellStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
    }
}
