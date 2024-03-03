package com.icss.poie.tools.excel.utils.xls;

import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.model.DataValidation;
import com.icss.poie.tools.excel.model.Scope;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/12/1 17:32
 * {@code @version:}:       1.0
 */
public class ValidationDataUtils {

    static boolean setData(HSSFSheet sheet, List<DataValidation> dataValidations){
        if(ValueUtils.isBlank(sheet.getDataValidations())){
            return false;
        }

        for (HSSFDataValidation sheetDv : sheet.getDataValidations()){
            DataValidation dataValidation = new DataValidation();
            dataValidation.setContainer(new DataValidation.Container());
            dataValidation.getContainer().setExplicitListOfValues(sheetDv.getValidationConstraint().getExplicitListValues());
            if(sheetDv.getRegions() != null && ValueUtils.isNotBlank(sheetDv.getRegions().getCellRangeAddresses())){
                dataValidation.setCellRanges(getCellRanges(sheetDv));
            }
            dataValidation.getContainer().setOperator(sheetDv.getValidationConstraint().getOperator());
            dataValidation.getContainer().setValidationType(sheetDv.getValidationConstraint().getValidationType());
            dataValidation.getContainer().setFormula1(sheetDv.getValidationConstraint().getFormula1());
            dataValidation.getContainer().setFormula2(sheetDv.getValidationConstraint().getFormula2());
            dataValidation.setPromptBoxText(sheetDv.getPromptBoxText());
            dataValidation.setErrorBoxText(sheetDv.getErrorBoxText());
            dataValidations.add(dataValidation);
        }
        return true;
    }


    @NotNull
    private static List<Scope> getCellRanges(HSSFDataValidation sheetDv) {
        List<Scope> cellRanges = new ArrayList<>();
        for (CellRangeAddress crd : sheetDv.getRegions().getCellRangeAddresses()){
            Scope cellRange = new Scope();
            cellRange.setR(crd.getFirstRow());
            cellRange.setC(crd.getFirstColumn());
            cellRange.setRs(crd.getLastRow() - crd.getFirstRow());
            cellRange.setCs(crd.getLastColumn() - crd.getFirstColumn());
            cellRanges.add(cellRange);
        }
        return cellRanges;
    }
}
