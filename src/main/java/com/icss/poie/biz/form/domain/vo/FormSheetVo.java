package com.icss.poie.biz.form.domain.vo;

import com.icss.poie.biz.excel.domain.vo.ExcelDataVo;
import com.icss.poie.biz.excel.domain.vo.SheetDataVo;
import com.icss.poie.biz.excel.domain.entity.CellData;
import com.icss.poie.biz.excel.domain.vo.CellDataVo;
import lombok.Data;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/28 17:17
 * {@code @version:}:       1.0
 */

public class FormSheetVo {

    @Data
    public static class Detail{
        private FormVo form;
        private ExcelDataVo excelData;
    }

    @Data
    public static class FormSheetData extends SheetDataVo {
        private List<FormVo> forms;
    }
    @Data
    public static class FormExcelData extends ExcelDataVo<FormSheetData>{}

    public static <E extends CellData> void sortCellData(int direction, List<E> cellDatas){
        switch (direction){
            case 1: case 3:{
                CellDataVo.sortCellDataH(cellDatas);
            } break;
            case 2:{
                CellDataVo.sortCellDataV(cellDatas);
            } break;
        }
    }


}
