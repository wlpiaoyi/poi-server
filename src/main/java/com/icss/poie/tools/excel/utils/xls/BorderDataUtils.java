package com.icss.poie.tools.excel.utils.xls;

import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.model.BorderStyle;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * <p><b>{@code @description:}</b>  读取xlsx的边框信息到数据对象</p>
 * <p><b>{@code @date:}</b>         2024-02-27 18:24:54</p>
 * <p><b>{@code @author:}</b>       wlpiaoyi</p>
 * <p><b>{@code @version:}</b>      1.0</p>
 */
class BorderDataUtils {


    /**
     * <p><b>{@code @description:}</b>
     * TODO
     * </p>
     *
     * <p><b>@param</b> <b>cell</b>
     * {@link HSSFCell}
     * </p>
     *
     * <p><b>@param</b> <b>borderData</b>
     * {@link BorderStyle}
     * </p>
     *
     * <p><b>{@code @date:}</b>2024/3/7 11:31</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    static void setBorder(HSSFCell cell, BorderStyle borderData){
        HSSFCellStyle cellStyle = cell.getCellStyle();
        HSSFColor color = ExcelUtils.getColor(cellStyle.getTopBorderColor());
        if(color != null){
            if(borderData.getTop() == null){
                borderData.setTop(new BorderStyle.Border());
            }

            borderData.getTop().setColor(ExcelUtils.getColorHex(color));
        }
        color = ExcelUtils.getColor(cellStyle.getLeftBorderColor());
        if(color != null){
            if(borderData.getLeft() == null){
                borderData.setLeft(new BorderStyle.Border());
            }

            borderData.getLeft().setColor(ExcelUtils.getColorHex(color));
        }
        color = ExcelUtils.getColor(cellStyle.getBottomBorderColor());
        if(color != null){
            if(borderData.getBottom() == null){
                borderData.setBottom(new BorderStyle.Border());
            }

            borderData.getBottom().setColor(ExcelUtils.getColorHex(color));
        }
        color = ExcelUtils.getColor(cellStyle.getRightBorderColor());
        if(color != null){
            if(borderData.getRight() == null){
                borderData.setRight(new BorderStyle.Border());
            }

            borderData.getRight().setColor(ExcelUtils.getColorHex(color));
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
