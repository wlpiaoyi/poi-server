package com.icss.poie.tools.excel.utils.xls;

import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.DataStyle;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;

import java.util.Locale;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 13:45
 * {@code @version:}:       1.0
 */
class StyleDataUtils {

    static String parseColorToHexString(HSSFColor color){
        short[] rgb =  color.getTriplet();
        String hexString = "";
        for (short v : rgb){
            String arg = Integer.toHexString(v);
            if(arg.length() == 1){
                hexString += "0";
            }
            hexString += arg;
        }
        return hexString;

    }


    static boolean setDataStyle(DataStyle dataStyle, HSSFCell cell) {

        HSSFCellStyle cellStyle = cell.getCellStyle();
        if(cellStyle.getFillBackgroundColorColor() != null){
//            dataStyle.setBg(("#" + parseColorToHexString(cellStyle.getFillBackgroundColorColor())) .toUpperCase(Locale.ROOT));
            dataStyle.setBg("#FFFFFF");
        }
        synFont(dataStyle, cell);
        dataStyle.setDfm(cellStyle.getDataFormat());
        dataStyle.setDfmv(cellStyle.getDataFormatString());
        //自动换行
        if(cellStyle.getWrapText()){
            dataStyle.setTb((byte) 2);
        }
        //文字对齐方式
        switch (cellStyle.getVerticalAlignment()){
            case TOP:{
                dataStyle.setVt((byte) 1);
            }
            break;
            case BOTTOM:{
                dataStyle.setVt((byte) 2);
            }
            break;
            default:{
                dataStyle.setVt((byte) 0);
            }
        }
        //文字对齐方式
        switch (cellStyle.getAlignment()){
            case LEFT:{
                dataStyle.setHt((byte) 1);
            }
            break;
            case RIGHT:{
                dataStyle.setHt((byte) 2);
            }
            break;
            default:{
                dataStyle.setHt((byte) 0);
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
            dataStyle.setFc("#" + parseColorToHexString(font.getHSSFColor(cell.getSheet().getWorkbook())).toUpperCase(Locale.ROOT));
        }
        dataStyle.setIt((byte) (font.getItalic() ? 1 : 0));
        dataStyle.setBl((byte) (font.getBold() ? 1 : 0));
        dataStyle.setUn(font.getUnderline());
        if(font.getFontHeight() > 1){
            dataStyle.setFs(font.getFontHeightInPoints());
        }else{
            dataStyle.setFs((short) 10);
        }
    }
}
