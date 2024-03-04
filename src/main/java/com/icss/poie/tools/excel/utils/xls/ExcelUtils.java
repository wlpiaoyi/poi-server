package com.icss.poie.tools.excel.utils.xls;

import com.icss.poie.framework.common.tools.ValueUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;

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
