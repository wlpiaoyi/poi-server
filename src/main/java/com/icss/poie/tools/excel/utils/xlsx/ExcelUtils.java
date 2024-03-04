package com.icss.poie.tools.excel.utils.xlsx;

import com.icss.poie.framework.common.tools.ValueUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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


    public static void formulaEvaluatorAll(XSSFWorkbook workbook){
        // 拿到计算公式
        XSSFFormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator(workbook);
        formulaEvaluator.evaluateAll();
    }

    public static Map<Cell, CellValue> formulaEvaluatorCells(XSSFWorkbook workbook, List<Cell> cells){
        Map<Cell, CellValue> resMap = new HashMap<>();
        // 拿到计算公式
        XSSFFormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator(workbook);
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

    public static List<XSSFCell> formulaEvaluatorInCells(XSSFWorkbook workbook, List<Cell> cells){
        List<XSSFCell> resList = new ArrayList<>();
        // 拿到计算公式
        XSSFFormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator(workbook);
        for (Cell cell : cells){
            if(ValueUtils.isBlank(cell.getCellFormula())){
                continue;
            }
            XSSFCell xcell= formulaEvaluator.evaluateInCell(cell);
            if(xcell == null){
                continue;
            }
            resList.add(xcell);
        }
        return resList;
    }


}
