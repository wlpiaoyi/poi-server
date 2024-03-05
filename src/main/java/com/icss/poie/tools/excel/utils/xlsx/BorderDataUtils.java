package com.icss.poie.tools.excel.utils.xlsx;

import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.model.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

import java.util.ArrayList;

/**
 * <p><b>{@code @description:}</b>  读取xlsx的边框信息到数据对象</p>
 * <p><b>{@code @date:}</b>         2024-02-27 18:24:54</p>
 * <p><b>{@code @author:}</b>       wlpiaoyi</p>
 * <p><b>{@code @version:}</b>      1.0</p>
 */
class BorderDataUtils {


    public static byte[] getRGB(XSSFColor color){
        return ExcelUtils.getColorBytes(color);
    }


    /**
     * <p><b>{@code @description:}</b>
     * TODO
     * </p>
     *
     * <p><b>@param</b> <b>cellStyle</b>
     * {@link XSSFCellStyle}
     * </p>
     *
     * <p><b>@param</b> <b>dataStyle</b>
     * {@link DataStyle}
     * </p>
     *
     * <p><b>{@code @date:}</b>2024/2/27 18:37</p>
     * <p><b>{@code @return:}</b>{@link XSSFCellStyle}</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    static void setBorder(XSSFCell cell, BorderStyle borderData){
        XSSFCellStyle cellStyle = cell.getCellStyle();
        XSSFColor color = cellStyle.getBorderColor(XSSFCellBorder.BorderSide.TOP);
        byte[] rgb = getRGB(color);
        if(rgb != null){
            if(borderData.getTop() == null){
                borderData.setTop(new BorderStyle.Border());
            }

            borderData.getTop().setColor("#" + ValueUtils.bytesToHex(rgb));
        }
        color = cellStyle.getBorderColor(XSSFCellBorder.BorderSide.RIGHT);
        rgb = getRGB(color);
        if(rgb != null){
            if(borderData.getRight() == null){
                borderData.setRight(new BorderStyle.Border());
            }
            borderData.getRight().setColor("#" + ValueUtils.bytesToHex(rgb));
        }
        color = cellStyle.getBorderColor(XSSFCellBorder.BorderSide.BOTTOM);
        rgb = getRGB(color);
        if(rgb != null){
            if(borderData.getBottom() == null){
                borderData.setBottom(new BorderStyle.Border());
            }
            borderData.getBottom().setColor("#" + ValueUtils.bytesToHex(rgb));
        }
        color = cellStyle.getBorderColor(XSSFCellBorder.BorderSide.LEFT);
        rgb = getRGB(color);
        if(rgb != null){
            if(borderData.getLeft() == null){
                borderData.setLeft(new BorderStyle.Border());
            }
            borderData.getLeft().setColor("#" + ValueUtils.bytesToHex(rgb));
        }
        org.apache.poi.ss.usermodel.BorderStyle borderStyle = cellStyle.getBorderTop();
        if(borderStyle != null && ValueUtils.isNotBlank(borderStyle.getCode())){
            if(borderData.getTop() == null){
                borderData.setTop(new BorderStyle.Border());
            }
            borderData.getTop().setStyleCode(borderStyle.getCode());
        }
        borderStyle = cellStyle.getBorderRight();
        if(borderStyle != null && ValueUtils.isNotBlank(borderStyle.getCode())){
            if(borderData.getRight() == null){
                borderData.setRight(new BorderStyle.Border());
            }
            borderData.getRight().setStyleCode(borderStyle.getCode());
        }
        borderStyle = cellStyle.getBorderBottom();
        if(borderStyle != null && ValueUtils.isNotBlank(borderStyle.getCode())){
            if(borderData.getBottom() == null){
                borderData.setBottom(new BorderStyle.Border());
            }
            borderData.getBottom().setStyleCode(borderStyle.getCode());
        }
        borderStyle = cellStyle.getBorderLeft();
        if(borderStyle != null && ValueUtils.isNotBlank(borderStyle.getCode())){
            if(borderData.getLeft() == null){
                borderData.setLeft(new BorderStyle.Border());
            }
            borderData.getLeft().setStyleCode(borderStyle.getCode());
        }
    }

}
