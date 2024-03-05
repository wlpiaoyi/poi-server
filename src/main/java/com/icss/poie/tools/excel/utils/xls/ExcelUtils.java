package com.icss.poie.tools.excel.utils.xls;

import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.model.Point;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p><b>{@code @description:}</b>  一些常用的函数</p>
 * <p><b>{@code @date:}</b>         2024-03-04 10:32:52</p>
 * <p><b>{@code @author:}</b>       wlpiaoyi</p>
 * <p><b>{@code @version:}</b>      1.0</p>
 */
public class ExcelUtils {


    public static com.icss.poie.tools.excel.model.Comment getCellComment(HSSFCell cell){
        HSSFComment comment = cell.getCellComment();
        if(comment == null){
            return null;
        }
        if(comment.getString() == null){
            return null;
        }
        String text = comment.getString().getString();
        if(ValueUtils.isBlank(text)){
            return null;
        }
        ClientAnchor clientAnchor = comment.getClientAnchor();
        String author = comment.getAuthor();
        return new com.icss.poie.tools.excel.model.Comment(
                clientAnchor, new Point(cell.getColumnIndex(),cell.getRowIndex()), text, author
        );
    }
    public static void setCellComment(HSSFSheet sheet, HSSFCell cell, com.icss.poie.tools.excel.model.Comment comment){
        //设置单元格中的批注
        Drawing draw = sheet.createDrawingPatriarch();
        //此处八个参数   前四个参数为两个坐标点(从a坐标到b坐标)后四个参数   为  编辑和显示批注时的大小(看需求调整)
        Comment commentc = draw.createCellComment(new HSSFClientAnchor(
                comment.getDx1(),comment.getDy1(),
                comment.getDx2(),comment.getDy2(),
                (short) comment.getCol1(),comment.getRow1(),
                (short) comment.getCol2(),comment.getRow2()));
        //输入批注信息
        commentc.setString(new HSSFRichTextString(comment.getText()));
        //添加作者,选中B5单元格,看状态栏
        commentc.setAuthor(comment.getAuthor());
        //将批注添加到单元格对象中
        cell.setCellComment(commentc);
    }

    public static void formulaEvaluatorAll(HSSFWorkbook workbook){
        // 拿到计算公式
        HSSFFormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator(workbook);
        formulaEvaluator.evaluateAll();
    }

    public static Map<Cell, CellValue> formulaEvaluatorCells(HSSFWorkbook workbook, List<Cell> cells){
        Map<Cell, CellValue> resMap = new HashMap<>();
        // 拿到计算公式
        HSSFFormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator(workbook);
        for (Cell cell : cells){
            if(ValueUtils.isBlank(cell.getCellFormula())){
                continue;
            }
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            if(cellValue == null){
                continue;
            }
            resMap.put(cell, cellValue);
        }
        return resMap;
    }

    public static List<HSSFCell> formulaEvaluatorInCells(HSSFWorkbook workbook, List<Cell> cells){
        List<HSSFCell> resList = new ArrayList<>();
        // 拿到计算公式
        HSSFFormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator(workbook);
        for (Cell cell : cells){
            if(ValueUtils.isBlank(cell.getCellFormula())){
                continue;
            }
            HSSFCell xcell= formulaEvaluator.evaluateInCell(cell);
            if(xcell == null){
                continue;
            }
            resList.add(xcell);
        }
        return resList;
    }


}
