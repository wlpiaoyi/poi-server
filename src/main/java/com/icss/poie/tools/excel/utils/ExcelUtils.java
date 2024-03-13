package com.icss.poie.tools.excel.utils;

import com.icss.poie.framework.common.exception.BusinessException;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.model.Comment;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.util.*;

/**
 * <p><b>{@code @description:}</b>  一些常用的函数</p>
 * <p><b>{@code @date:}</b>         2024-03-04 10:32:52</p>
 * <p><b>{@code @author:}</b>       wlpiaoyi</p>
 * <p><b>{@code @version:}</b>      1.0</p>
 */
public class ExcelUtils {

    public static String parseRGBBytesToHex(byte[] rgb){
        return "#" + ValueUtils.bytesToHex(rgb).toUpperCase(Locale.ROOT);
    }



    public static int[] parseRGBHexToInts(String rgbHex){
        if(rgbHex.startsWith("#")){
            String hexValue = rgbHex.substring(1, 7);
            int len = hexValue.length();
            int[] b = new int[len / 2];
            for (int i = 0; i < len; i += 2) {
                b[i / 2] = ((Character.digit(hexValue.charAt(i), 16) << 4) + Character
                        .digit(hexValue.charAt(i + 1), 16));
            }
            return b;
        }else if(rgbHex.startsWith("rgb(")){
            Integer[] ints = ValueUtils.toIntegerArray(rgbHex.substring(4, rgbHex.length() - 1).replaceAll(" ", ""));
            int[] bytes = new int[]{0,0,0};
            int j = 0;
            for (int i : ints){
                bytes[j] =  i;
                j++;
            }
            return bytes;
        }else{
            return new int[]{0,0,0};
        }
    }

    public static byte[] parseRGBHexToBytes(String rgbHex){
        if(rgbHex.startsWith("#")){
            return ValueUtils.hexToBytes(rgbHex.substring(1, 7));
        }else if(rgbHex.startsWith("rgb(")){
            Integer[] ints = ValueUtils.toIntegerArray(rgbHex.substring(4, rgbHex.length() - 1).replaceAll(" ", ""));
            byte[] bytes = new byte[]{0,0,0};
            int j = 0;
            for (int i : ints){
                bytes[j] = (byte) i;
                j++;
            }
            return bytes;
        }else{
            return new byte[]{0,0,0};
        }
    }

    /**
     * <p><b>{@code @description:}</b>
     * 获取批注
     * </p>
     *
     * <p><b>@param</b> <b>cell</b>
     * {@link Cell}
     * </p>
     *
     * <p><b>{@code @date:}</b>2024/3/5 14:30</p>
     * <p><b>{@code @return:}</b>{@link Comment}</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    public static Comment getCellComment(Cell cell){
        if(cell instanceof XSSFCell){
            return  com.icss.poie.tools.excel.utils.xlsx.ExcelUtils.getCellComment((XSSFCell) cell);
        }else if(cell instanceof HSSFCell){
            return com.icss.poie.tools.excel.utils.xls.ExcelUtils.getCellComment((HSSFCell) cell);
        }else{
            throw new BusinessException("不支持的cell类型");
        }
    }

    /**
     * <p><b>{@code @description:}</b>
     * 设置批注
     * </p>
     *
     * <p><b>@param</b> <b>sheet</b>
     * {@link Sheet}
     * </p>
     *
     * <p><b>@param</b> <b>cell</b>
     * {@link Cell}
     * </p>
     *
     * <p><b>@param</b> <b>comment</b>
     * {@link Comment}
     * </p>
     *
     * <p><b>{@code @date:}</b>2024/3/5 14:30</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    public static void setCellComment(Sheet sheet, Cell cell, Comment comment){
        if(sheet instanceof XSSFSheet && cell instanceof XSSFCell){
            com.icss.poie.tools.excel.utils.xlsx.ExcelUtils.setCellComment((XSSFSheet) sheet, (XSSFCell) cell, comment);
        }else if(sheet instanceof HSSFSheet && cell instanceof HSSFCell){
            com.icss.poie.tools.excel.utils.xls.ExcelUtils.setCellComment((HSSFSheet) sheet, (HSSFCell) cell, comment);
        }else{
            throw new BusinessException("不支持的sheet或cell类型");
        }

    }

    /**
     * <p><b>{@code @description:}</b>
     * 公式计算
     * </p>
     *
     * <p><b>@param</b> <b>workbook</b>
     * {@link Workbook}
     * </p>
     *
     * <p><b>{@code @date:}</b>2024/3/5 14:30</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    public static void formulaEvaluatorAll(Workbook workbook){
        if (workbook instanceof XSSFWorkbook) {
            com.icss.poie.tools.excel.utils.xlsx.ExcelUtils.formulaEvaluatorAll((XSSFWorkbook) workbook);
        }else if(workbook instanceof  HSSFWorkbook){
            com.icss.poie.tools.excel.utils.xls.ExcelUtils.formulaEvaluatorAll((HSSFWorkbook) workbook);
        }else{
            throw new BusinessException("不支持的workbook类型");
        }
    }

    /**
     * <p><b>{@code @description:}</b>
     * 根据给出的单元格技术啊公式，并返回值
     * </p>
     *
     * <p><b>@param</b> <b>workbook</b>
     * {@link Workbook}
     * </p>
     *
     * <p><b>@param</b> <b>cells</b>
     * {@link List<Cell>}
     * </p>
     *
     * <p><b>{@code @date:}</b>2024/3/5 14:31</p>
     * <p><b>{@code @return:}</b>{@link Map<Cell, CellValue>}</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    public static Map<Cell, CellValue> formulaEvaluatorCells(Workbook workbook, List<Cell> cells){
        if (workbook instanceof XSSFWorkbook) {
            return com.icss.poie.tools.excel.utils.xlsx.ExcelUtils.formulaEvaluatorCells((XSSFWorkbook) workbook, cells);
        }else if(workbook instanceof  HSSFWorkbook){
            return com.icss.poie.tools.excel.utils.xls.ExcelUtils.formulaEvaluatorCells((HSSFWorkbook) workbook, cells);
        }else{
            throw new BusinessException("不支持的workbook类型");
        }
    }

    /**
     * <p><b>{@code @description:}</b>
     * 根据给出的单元格技术啊公式，并把值设置到单元格
     * </p>
     *
     * <p><b>@param</b> <b>workbook</b>
     * {@link Workbook}
     * </p>
     *
     * <p><b>@param</b> <b>cells</b>
     * {@link List<Cell>}
     * </p>
     *
     * <p><b>{@code @date:}</b>2024/3/5 14:32</p>
     * <p><b>{@code @return:}</b>{@link List<T>}</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    public static <T extends Cell> List<T> formulaEvaluatorInCells(Workbook workbook, List<Cell> cells){
        if(workbook instanceof  HSSFWorkbook){
            return (List<T>) com.icss.poie.tools.excel.utils.xls.ExcelUtils.formulaEvaluatorInCells((HSSFWorkbook) workbook, cells);
        } else if (workbook instanceof XSSFWorkbook) {
            return (List<T>) com.icss.poie.tools.excel.utils.xlsx.ExcelUtils.formulaEvaluatorInCells((XSSFWorkbook) workbook, cells);
        }else {
            throw new BusinessException("不支持的workbook类型");
        }
    }

}
