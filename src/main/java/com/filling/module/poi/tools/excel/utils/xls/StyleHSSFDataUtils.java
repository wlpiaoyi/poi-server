package com.filling.module.poi.tools.excel.utils.xls;

import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.tools.excel.DataStyle;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;

import java.util.Locale;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 13:45
 * {@code @version:}:       1.0
 */
public class StyleHSSFDataUtils {


    public static boolean setDataStyle(DataStyle dataStyle, HSSFCell cell) {

        HSSFCellStyle cellStyle = cell.getCellStyle();
        if(cellStyle.getFillBackgroundColorColor() != null){
            dataStyle.setBg("#" + cellStyle.getFillBackgroundColorColor().getHexString().toUpperCase(Locale.ROOT));
        }

        synFont(dataStyle, cell);
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

    private static void synFont(DataStyle dataStyle,HSSFCell cell) {
        HSSFFont font = cell.getCellStyle().getFont(cell.getSheet().getWorkbook());
        if(font == null){
            return;
        }
        if(ValueUtils.isNotBlank(font.getFontName())){
            dataStyle.setFf(font.getFontName());
        }else{
            dataStyle.setFf("宋体");
        }
        if(font.getHSSFColor(cell.getSheet().getWorkbook()) != null){
            dataStyle.setFc("#" + font.getHSSFColor(cell.getSheet().getWorkbook()).getHexString().toUpperCase(Locale.ROOT));
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
