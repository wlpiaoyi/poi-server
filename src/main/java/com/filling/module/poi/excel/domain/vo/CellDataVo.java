package com.filling.module.poi.excel.domain.vo;

import com.filling.framework.common.exception.BusinessException;
import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.excel.domain.entity.CellData;
import com.filling.module.poi.excel.domain.model.CellValue;
import com.filling.module.poi.form.domain.entity.FormHead;
import com.filling.module.poi.form.domain.vo.FormHeadVo;
import com.filling.module.poi.tools.excel.ICellData;
import com.filling.module.poi.tools.excel.ICellValue;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/23 14:51
 * {@code @version:}:       1.0
 */
public class CellDataVo extends CellData implements ICellData {


    @Override
    public ICellValue v() {
        return this.getV();
    }

    @Override
    public void putV(ICellValue v) {
        this.setV((CellValue)v);
    }


    public static void synFormHeadForLocation(List<? extends CellData> cellDatas, List<? extends FormHead> formHeads){
        if(ValueUtils.isBlank(cellDatas)){
            return;
        }
        if(ValueUtils.isBlank(formHeads)){
            return;
        }
        Map<String, CellData> cellDataMap = cellDatas.stream().collect(Collectors.toMap(
                CellData::hexId, Function.identity()
        ));
        for (FormHead formHead : formHeads){
            if(formHead.getLocation() == null){
                throw new BusinessException("表单头位置不能为空");
            }
            if(formHead.getLocation().getC() < 0){
                throw new BusinessException("表单头位置数据错误");
            }
            if(formHead.getLocation().getR() < 0){
                throw new BusinessException("表单头位置数据错误");
            }
            if(formHead.getIsLeaf() == null || formHead.getIsLeaf().byteValue() != 1){
                continue;
            }
            CellData cellData = cellDataMap.get(formHead.hexDataId());
            if(cellData == null){
                throw new BusinessException("没有找到单元格数据");
            }
            formHead.setLocation(cellData);

        }
    }

    public static void synFormHeadForDataId(List<? extends CellData> cellDatas, List<? extends FormHead> formHeads){
        if(ValueUtils.isBlank(cellDatas)){
            return;
        }
        if(ValueUtils.isBlank(formHeads)){
            return;
        }
        Map<String, CellData> cellDataMap = cellDatas.stream().collect(Collectors.toMap(
                CellData::mapKey, Function.identity()
        ));
        for (FormHead formHead : formHeads){
            if(formHead.getLocation() == null){
                throw new BusinessException("表单头位置不能为空");
            }
            if(formHead.getLocation().getC() < 0){
                throw new BusinessException("表单头位置数据错误");
            }
            if(formHead.getLocation().getR() < 0){
                throw new BusinessException("表单头位置数据错误");
            }
            if(formHead.getIsLeaf() == null || formHead.getIsLeaf().byteValue() != 1){
                continue;
            }
            CellData cellData = cellDataMap.get(formHead.mapKey());
            if(cellData == null){
                throw new BusinessException("没有找到单元格数据");
            }
            formHead.setLocation(cellData);
            formHead.setDataId(cellData.getId());

        }
    }

}
