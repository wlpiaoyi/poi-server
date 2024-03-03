package com.icss.poie.tools.excel.utils.xlsx;

import com.icss.poie.tools.excel.model.BorderStyle;
import com.icss.poie.tools.excel.model.ICacheMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

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
    static void setBorder(BorderStyle borderData, ICacheMap cacheMap, XSSFCellStyle cellStyle){
        BorderStyle.Border border = borderData.getTop();
        if(border != null){
            if(border.getColor() != null){
                XSSFColor color = cacheMap.getCacheXSSFColor(border.getColor());
                cellStyle.setBorderColor(XSSFCellBorder.BorderSide.TOP, color);
            }
            if(border.getStyleCode() != null){
                org.apache.poi.ss.usermodel.BorderStyle borderStyle = org.apache.poi.ss.usermodel.BorderStyle.valueOf(border.getStyleCode());
                cellStyle.setBorderTop(borderStyle);
            }
        }
        border = borderData.getRight();
        if(border != null){
            if(border.getColor() != null){
                XSSFColor color = cacheMap.getCacheXSSFColor(border.getColor());
                cellStyle.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, color);
            }
            if(border.getStyleCode() != null){
                org.apache.poi.ss.usermodel.BorderStyle borderStyle = org.apache.poi.ss.usermodel.BorderStyle.valueOf(border.getStyleCode());
                cellStyle.setBorderRight(borderStyle);
            }
        }
        border = borderData.getBottom();
        if(border != null){
            if(border.getColor() != null){
                XSSFColor color = cacheMap.getCacheXSSFColor(border.getColor());
                cellStyle.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, color);
            }
            if(border.getStyleCode() != null){
                org.apache.poi.ss.usermodel.BorderStyle borderStyle = org.apache.poi.ss.usermodel.BorderStyle.valueOf(border.getStyleCode());
                cellStyle.setBorderBottom(borderStyle);
            }
        }
        border = borderData.getLeft();
        if(border != null){
            if(border.getColor() != null){
                XSSFColor color = cacheMap.getCacheXSSFColor(border.getColor());
                cellStyle.setBorderColor(XSSFCellBorder.BorderSide.LEFT, color);
            }
            if(border.getStyleCode() != null){
                org.apache.poi.ss.usermodel.BorderStyle borderStyle = org.apache.poi.ss.usermodel.BorderStyle.valueOf(border.getStyleCode());
                cellStyle.setBorderLeft(borderStyle);
            }
        }
    }
}
