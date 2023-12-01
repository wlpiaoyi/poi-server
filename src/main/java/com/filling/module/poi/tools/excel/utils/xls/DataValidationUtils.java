package com.filling.module.poi.tools.excel.utils.xls;


import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.tools.excel.DataValidation;
import com.filling.module.poi.tools.excel.Scope;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDataValidationHelper;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/12/1 17:24
 * {@code @version:}:       1.0
 */
@Slf4j
public class DataValidationUtils {

    static boolean setValidation(HSSFSheet sheet, List<DataValidation> dataValidations){
        if(ValueUtils.isBlank(dataValidations)){
            return false;
        }
        for (DataValidation dataValidation : dataValidations){
            HSSFDataValidationHelper helper = new HSSFDataValidationHelper(sheet);
            //构造constraint对象 设置有效性验证为 文本长度 最小长度为1
            DVConstraint constraint;
            try{
                constraint = getDataValidationConstraint(dataValidation);
            }catch (Exception e){
                log.error("", e);
                continue;
            }
            CellRangeAddressList addressList = new CellRangeAddressList();
            for (Scope cellRange : dataValidation.getCellRanges()){
                //参数顺序：开始行、结束行、开始列、结束列
                addressList.addCellRangeAddress(cellRange.getR(), cellRange.getC(),
                        cellRange.getR() + cellRange.getRs(),
                        cellRange.getC() + cellRange.getCs());
            }
            HSSFDataValidation validation = (HSSFDataValidation)helper.createValidation(constraint, addressList);
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
    private static DVConstraint getDataValidationConstraint(DataValidation dataValidation) {
        DVConstraint constraint = null;
        switch (dataValidation.getContainer().getValidationType()){
            case DataValidationConstraint.ValidationType.LIST:{
                constraint = DVConstraint.createExplicitListConstraint(dataValidation.getContainer().getExplicitListOfValues());
            }
            break;
            case DataValidationConstraint.ValidationType.ANY:
            case DataValidationConstraint.ValidationType.DECIMAL:
            case DataValidationConstraint.ValidationType.INTEGER:
            case DataValidationConstraint.ValidationType.TEXT_LENGTH:{
                constraint = DVConstraint.createNumericConstraint(
                        dataValidation.getContainer().getValidationType(),
                        dataValidation.getContainer().getOperator(),
                        dataValidation.getContainer().getFormula1(),
                        dataValidation.getContainer().getFormula2()
                );
            }
            break;
            case DataValidationConstraint.ValidationType.TIME:{
                constraint = DVConstraint.createTimeConstraint(
                        dataValidation.getContainer().getOperator(),
                        dataValidation.getContainer().getFormula1(),
                        dataValidation.getContainer().getFormula2()
                );
            }
            break;
            case DataValidationConstraint.ValidationType.DATE:{
                constraint = DVConstraint.createDateConstraint(
                        dataValidation.getContainer().getOperator(),
                        dataValidation.getContainer().getFormula1(),
                        dataValidation.getContainer().getFormula2(),
                        dataValidation.getContainer().getDateFormat()
                );
            }
            break;
            default:
                throw new IllegalArgumentException("Validation Type ("
                        + dataValidation.getContainer().getValidationType() + ") not supported with this method");
        }
        return constraint;
    }

}
