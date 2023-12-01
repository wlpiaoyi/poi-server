package com.filling.module.poi.tools.excel.utils.xlsx;

import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.tools.excel.DataStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.util.Locale;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 11:15
 * {@code @version:}:       1.0
 */
class StyleDataUtils {


    static boolean setDataStyle(DataStyle dataStyle, XSSFCell cell) {

        XSSFCellStyle cellStyle = cell.getCellStyle();
        XSSFColor bgColor = cellStyle.getFillForegroundColorColor();
        if(bgColor != null && bgColor.getRGB() != null){
            dataStyle.setBg("#" + ValueUtils.bytesToHex(bgColor.getRGB()).toUpperCase(Locale.ROOT));
        }

        synFont(dataStyle, cell);
        dataStyle.setDfm(cellStyle.getDataFormat());
        dataStyle.setDfmv(cellStyle.getDataFormatString());
        //自动换行
        if(cellStyle.getWrapText()){
            dataStyle.setTb(2);
        }
        //文字对齐方式
        switch (cellStyle.getVerticalAlignment()){
            case TOP:{
                dataStyle.setVt(1);
            }
            break;
            case BOTTOM:{
                dataStyle.setVt(2);
            }
            break;
            default:{
                dataStyle.setVt(0);
            }
        }
        //文字对齐方式
        switch (cellStyle.getAlignment()){
            case LEFT:{
                dataStyle.setHt(1);
            }
            break;
            case RIGHT:{
                dataStyle.setHt(2);
            }
            break;
            default:{
                dataStyle.setHt(0);
            }
        }
        return true;
    }

    private static void synFont(DataStyle dataStyle,XSSFCell cell) {
        XSSFFont font = cell.getCellStyle().getFont();
        if(font == null){
            return;
        }
        if(ValueUtils.isNotBlank(font.getFontName())){
            dataStyle.setFf(font.getFontName());
        }else{
            dataStyle.setFf("宋体");
        }
        if(font.getXSSFColor() != null){
            dataStyle.setFc("#" + ValueUtils.bytesToHex(font.getXSSFColor().getRGB()).toUpperCase(Locale.ROOT));
        }
        dataStyle.setIt(font.getItalic() ? 1 : 0);
        dataStyle.setBl(font.getBold() ? 1 : 0);
        dataStyle.setUn(font.getUnderline());
        if(font.getFontHeight() > 1){
            dataStyle.setFs(font.getFontHeightInPoints());
        }else{
            dataStyle.setFs((short) 10);
        }
    }
}
