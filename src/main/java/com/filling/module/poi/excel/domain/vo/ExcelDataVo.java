package com.filling.module.poi.excel.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.excel.domain.entity.ExcelData;
import com.filling.module.poi.excel.domain.entity.SheetData;
import com.filling.module.poi.tools.utils.excel.IExcelData;
import com.filling.module.poi.tools.utils.excel.ISheetData;
import lombok.Data;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/23 14:49
 * {@code @version:}:       1.0
 */
@Data
public class ExcelDataVo extends ExcelData  implements IExcelData {

    @TableField(exist = false)
    private List<SheetDataVo> sheetDatas;

    public void clearDb(){
        super.clearDb();
        if(ValueUtils.isNotBlank(this.getSheetDatas())){
            for (SheetData sheetData : this.getSheetDatas()){
                sheetData.clearDb();
            }
        }
    }

//    public void synCellDataHead(){
//        if(ValueUtils.isNotBlank(this.getSheetDatas())){
//            for (SheetData sheetData : this.getSheetDatas()){
//                sheetData.synCellDataHead();
//            }
//        }
//    }
//
//
//    public void synCellDataToFormHeadRela(){
//
//        if(ValueUtils.isNotBlank(this.getSheetDatas())){
//            for (SheetData sheetData : this.getSheetDatas()){
//                List<Form> forms = sheetData.getForms();
//                List<CellData> cellDatas = sheetData.getCellDatas();
//                SheetData.synCellDataToFormHeadRela(forms, cellDatas);
//            }
//        }
//    }
//
//
//
//    public void synCellDataByHr(){
//        if(ValueUtils.isNotBlank(this.getSheetDatas())){
//            for (SheetData sheetData : this.getSheetDatas()){
//                sheetData.synCellDataByHr();
//            }
//        }
//    }
//
//    public void synProperties(){
//        if(ValueUtils.isNotBlank(this.getSheetDatas())){
//            for (SheetData sheetData : this.getSheetDatas()){
//                sheetData.setEtype(this.getEtype());
//                sheetData.synProperties();
//            }
//        }
//    }

    @Override
    public List<ISheetData> sheetDatas() {
        List datas = this.getSheetDatas();
        return datas;
    }

    @Override
    public void putSheetDatas(List<ISheetData> sheetDatas) {
        List datas = sheetDatas;
        this.setSheetDatas(datas);
    }
}
