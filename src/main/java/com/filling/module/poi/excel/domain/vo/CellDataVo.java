package com.filling.module.poi.excel.domain.vo;

import com.filling.module.poi.excel.domain.entity.CellData;
import com.filling.module.poi.excel.domain.model.CellValue;
import com.filling.module.poi.tools.utils.excel.ICellData;
import com.filling.module.poi.tools.utils.excel.ICellValue;

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


}
