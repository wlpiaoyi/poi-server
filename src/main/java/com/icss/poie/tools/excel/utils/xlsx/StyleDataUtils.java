package com.icss.poie.tools.excel.utils.xlsx;

import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.model.DataStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.util.Locale;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    单元格内容格式
 * {@code @date:}           2023/11/30 11:15
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
     * {@link XSSFCell}
     * </p>
     *
     * <p><b>{@code @date:}</b>2023/12/25 11:42</p>
     * <p><b>{@code @return:}</b>{@link boolean}</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    static boolean setDataStyle(DataStyle dataStyle, XSSFCell cell) {

        XSSFCellStyle cellStyle = cell.getCellStyle();
        XSSFColor bgColor = cellStyle.getFillForegroundColorColor();
        byte[] rgb;
        if(bgColor != null && (rgb = ExcelUtils.getColorBytes(bgColor)) != null){
            dataStyle.setBg("#" + ValueUtils.bytesToHex(rgb).toUpperCase(Locale.ROOT));
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
     * {@link XSSFCell}
     * </p>
     *
     * <p><b>{@code @date:}</b>2023/12/25 11:43</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
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
            dataStyle.setFc(ExcelUtils.getColorHex(font.getXSSFColor()));
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
