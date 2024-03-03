package com.icss.poie.biz.excel.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.icss.poie.biz.excel.domain.entity.SheetData;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.biz.excel.domain.entity.ExcelData;
import com.icss.poie.tools.excel.model.IExcelData;
import com.icss.poie.tools.excel.model.ISheetData;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/23 14:49
 * {@code @version:}:       1.0
 */
@Data
public class ExcelDataVo<S extends SheetDataVo> extends ExcelData  implements IExcelData {

    @TableField(exist = false)
    private List<S> sheetDatas;

    public void clearDb(){
        super.clearDb();
        if(ValueUtils.isNotBlank(this.getSheetDatas())){
            for (SheetData sheetData : this.getSheetDatas()){
                sheetData.clearDb();
            }
        }
    }

    public void setAllId(){
        if(this.getId() == null){
            this.setId(ObjectId.get());
        }
        if(ValueUtils.isNotBlank(this.getSheetDatas())){
            for (SheetDataVo sheetData : this.getSheetDatas()){
                sheetData.setExcelId(this.getId());
                sheetData.setAllId();
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
