package com.filling.module.poi.tools.excel.utils.xlsx;

import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.tools.excel.DataValidation;
import com.filling.module.poi.tools.excel.Scope;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/12/1 12:35
 * {@code @version:}:       1.0
 */
class DataValidationUtils {

    static boolean setValidation(XSSFSheet sheet, List<DataValidation> dataValidations){
        if(ValueUtils.isBlank(dataValidations)){
            return false;
        }
        for (DataValidation dataValidation : dataValidations){
            XSSFDataValidationHelper helper = new XSSFDataValidationHelper(sheet);
            XSSFDataValidationConstraint constraint = getDataValidationConstraint(dataValidation);
            CellRangeAddressList addressList = new CellRangeAddressList();
            for (Scope cellRange : dataValidation.getCellRanges()){
                //参数顺序：开始行、结束行、开始列、结束列
                addressList.addCellRangeAddress(cellRange.getR(), cellRange.getC(),
                        cellRange.getR() + cellRange.getRs(),
                        cellRange.getC() + cellRange.getCs());
            }
            XSSFDataValidation validation = (XSSFDataValidation)helper.createValidation(constraint, addressList);
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
            validation.setShowPromptBox(true);
            if(ValueUtils.isNotBlank(dataValidation.getErrorBoxText())){
                validation.createErrorBox("错误", dataValidation.getErrorBoxText());
            }
            if(ValueUtils.isNotBlank(dataValidation.getPromptBoxText())){
                validation.createPromptBox("提醒", dataValidation.getPromptBoxText());
            }
            sheet.addValidationData(validation);
        }
        return true;
    }

    @NotNull
    private static XSSFDataValidationConstraint getDataValidationConstraint(DataValidation dataValidation) {
        XSSFDataValidationConstraint constraint = null;

        switch (dataValidation.getContainer().getValidationType()){
            case DataValidationConstraint.ValidationType.LIST:{
                constraint = new XSSFDataValidationConstraint(dataValidation.getContainer().getExplicitListOfValues());
            }
            break;
            default:{
                constraint = new XSSFDataValidationConstraint(
                        dataValidation.getContainer().getValidationType(),
                        dataValidation.getContainer().getOperator(),
                        dataValidation.getContainer().getFormula1(),
                        dataValidation.getContainer().getFormula2()
                );
            }
            break;
        }
        return constraint;
    }

//    @NotNull
//    private static List<Scope> getCellRanges(XSSFDataValidation sheetDv) {
//        List<Scope> cellRanges = new ArrayList<>();
//        for (CellRangeAddress crd : sheetDv.getRegions().getCellRangeAddresses()){
//            Scope cellRange = new Scope();
//            cellRange.setR(crd.getFirstRow());
//            cellRange.setC(crd.getFirstColumn());
//            cellRange.setRs(crd.getLastRow() - crd.getFirstRow());
//            cellRange.setCs(crd.getLastColumn() - crd.getFirstColumn());
//            cellRanges.add(cellRange);
//        }
//        return cellRanges;
//    }

}
