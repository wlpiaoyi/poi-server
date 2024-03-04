package com.icss.poie.tools.excel.utils;

import com.icss.poie.framework.common.exception.BusinessException;
import com.icss.poie.framework.common.tools.ValueUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Workbook;
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


    public static void formulaEvaluatorAll(Workbook workbook){
        if(workbook instanceof  HSSFWorkbook){
            com.icss.poie.tools.excel.utils.xls.ExcelUtils.formulaEvaluatorAll((HSSFWorkbook) workbook);
        } else if (workbook instanceof XSSFWorkbook) {
            com.icss.poie.tools.excel.utils.xlsx.ExcelUtils.formulaEvaluatorAll((XSSFWorkbook) workbook);
        }else {
            throw new BusinessException("不支持的workbook类型");
        }
    }

    public static Map<Cell, CellValue> formulaEvaluatorCells(Workbook workbook, List<Cell> cells){
        if(workbook instanceof  HSSFWorkbook){
            return com.icss.poie.tools.excel.utils.xls.ExcelUtils.formulaEvaluatorCells((HSSFWorkbook) workbook, cells);
        } else if (workbook instanceof XSSFWorkbook) {
            return com.icss.poie.tools.excel.utils.xlsx.ExcelUtils.formulaEvaluatorCells((XSSFWorkbook) workbook, cells);
        }else {
            throw new BusinessException("不支持的workbook类型");
        }
    }

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
