package com.icss.poie.biz.excel.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.biz.excel.domain.entity.ExcelData;
import com.icss.poie.tools.excel.model.IExcelDataEx;
import com.icss.poie.tools.excel.model.ISheetDataEx;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/23 14:49
 * {@code @version:}:       1.0
 */
@Data
public class ExcelDataVo<SD extends ISheetDataEx> extends ExcelData  implements IExcelDataEx<SD> {

    @Transient
    @TableField(exist = false)
    private List<SD> sheetDatas;

    public void clearDb(){
        super.clearDb();
        if(ValueUtils.isNotBlank(this.getSheetDatas())){
            List<SheetDataVo> sheetDatas = (List<SheetDataVo>) this.getSheetDatas();
            for (SheetDataVo sheetData : sheetDatas){
                sheetData.clearDb();
            }
        }
    }

    public void setAllId(){
        if(this.getId() == null){
            this.setId(ObjectId.get());
        }
        if(ValueUtils.isNotBlank(this.getSheetDatas())){
            List<SheetDataVo> sheetDatas = (List<SheetDataVo>) this.getSheetDatas();
            for (SheetDataVo sheetData : sheetDatas){
                sheetData.setExcelId(this.getId());
                sheetData.setAllId();
            }
        }
    }
}
