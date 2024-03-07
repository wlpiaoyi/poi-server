package com.icss.poie.tools.excel.utils.xls;

import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.model.DataStyle;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Locale;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 13:45
 * {@code @version:}:       1.0
 */
class StyleDataUtils {


    /**
     * <p><b>{@code @description:}</b>
     * 读取Excel单元格格式同步到数据对象内
     * </p>
     *
     * <p><b>@param</b> <b>dataStyle</b>
     * {@link DataStyle}
     * </p>
     *
     * <p><b>@param</b> <b>cell</b>
     * {@link HSSFCell}
     * </p>
     *
     * <p><b>{@code @date:}</b>2023/12/25 11:42</p>
     * <p><b>{@code @return:}</b>{@link boolean}</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    static boolean setDataStyle(DataStyle dataStyle, HSSFCell cell) {

        HSSFCellStyle cellStyle = cell.getCellStyle();
        short colorIndex = cellStyle.getFillForegroundColor();
        if(colorIndex < 0 || colorIndex >= 64){
            colorIndex = 9;
        }
        dataStyle.setBg(ExcelUtils.getColorHex(ExcelUtils.getColor(colorIndex)));

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

    /**
     * <p><b>{@code @description:}</b>
     * 同步单元格字体到数据对象中
     * </p>
     *
     * <p><b>@param</b> <b>dataStyle</b>
     * {@link DataStyle}
     * </p>
     *
     * <p><b>@param</b> <b>cell</b>
     * {@link HSSFCell}
     * </p>
     *
     * <p><b>{@code @date:}</b>2023/12/25 11:43</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    private static void synFont(DataStyle dataStyle,HSSFCell cell) {
        HSSFWorkbook workbook = cell.getSheet().getWorkbook();
        HSSFFont font = cell.getCellStyle().getFont(workbook);
        if(font == null){
            return;
        }
        if(ValueUtils.isNotBlank(font.getFontName())){
            dataStyle.setFf(font.getFontName());
        }else{
            dataStyle.setFf("宋体");
        }
        if(font.getHSSFColor(workbook) != null){
            dataStyle.setFc(ExcelUtils.getColorHex(font.getHSSFColor(workbook)));
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
    

//    static String parseColorToHexString(HSSFColor color){
//        short[] rgb =  color.getTriplet();
//        String hexString = "";
//        for (short v : rgb){
//            String arg = Integer.toHexString(v);
//            if(arg.length() == 1){
//                hexString += "0";
//            }
//            hexString += arg;
//        }
//        return hexString;
//
//    }
//
//
//    static boolean setDataStyle(DataStyle dataStyle, HSSFCell cell) {
//
//        HSSFCellStyle cellStyle = cell.getCellStyle();
//        if(cellStyle.getFillBackgroundColorColor() != null){
////            dataStyle.setBg(("#" + parseColorToHexString(cellStyle.getFillBackgroundColorColor())) .toUpperCase(Locale.ROOT));
//            dataStyle.setBg("#FFFFFF");
//        }
//        synFont(dataStyle, cell);
//        dataStyle.setDfm(cellStyle.getDataFormat());
//        dataStyle.setDfmv(cellStyle.getDataFormatString());
//        //自动换行
//        if(cellStyle.getWrapText()){
//            dataStyle.setTb((byte) 2);
//        }
//        //文字对齐方式
//        switch (cellStyle.getVerticalAlignment()){
//            case TOP:{
//                dataStyle.setVt((byte) 1);
//            }
//            break;
//            case BOTTOM:{
//                dataStyle.setVt((byte) 2);
//            }
//            break;
//            default:{
//                dataStyle.setVt((byte) 0);
//            }
//        }
//        //文字对齐方式
//        switch (cellStyle.getAlignment()){
//            case LEFT:{
//                dataStyle.setHt((byte) 1);
//            }
//            break;
//            case RIGHT:{
//                dataStyle.setHt((byte) 2);
//            }
//            break;
//            default:{
//                dataStyle.setHt((byte) 0);
//            }
//        }
//        return true;
//    }
//
//    private static void synFont(DataStyle dataStyle,HSSFCell cell) {
//        HSSFFont font = cell.getCellStyle().getFont(cell.getSheet().getWorkbook());
//        if(font == null){
//            return;
//        }
//        if(ValueUtils.isNotBlank(font.getFontName())){
//            dataStyle.setFf(font.getFontName());
//        }else{
//            dataStyle.setFf("宋体");
//        }
//        if(font.getHSSFColor(cell.getSheet().getWorkbook()) != null){
//            dataStyle.setFc("#" + parseColorToHexString(font.getHSSFColor(cell.getSheet().getWorkbook())).toUpperCase(Locale.ROOT));
//        }
//        dataStyle.setIt((byte) (font.getItalic() ? 1 : 0));
//        dataStyle.setBl((byte) (font.getBold() ? 1 : 0));
//        dataStyle.setUn(font.getUnderline());
//        if(font.getFontHeight() > 1){
//            dataStyle.setFs(font.getFontHeightInPoints());
//        }else{
//            dataStyle.setFs((short) 10);
//        }
//    }
}
