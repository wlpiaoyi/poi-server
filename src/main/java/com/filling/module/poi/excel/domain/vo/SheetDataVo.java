package com.filling.module.poi.excel.domain.vo;

import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.excel.domain.entity.CellData;
import com.filling.module.poi.excel.domain.entity.CellMerge;
import com.filling.module.poi.excel.domain.entity.SheetData;
import com.filling.module.poi.excel.domain.model.GridInfo;
import com.filling.module.poi.tools.utils.excel.ICellData;
import com.filling.module.poi.tools.utils.excel.ICellMerge;
import com.filling.module.poi.tools.utils.excel.IGridInfo;
import com.filling.module.poi.tools.utils.excel.ISheetData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/23 14:46
 * {@code @version:}:       1.0
 */
@Data
public class SheetDataVo extends SheetData implements ISheetData {

    /** 单元格合并数据 **/
    @Schema(description =  "单元格合并数据")
    private List<CellMerge> cellMerges;

    /** 单元格数据 **/
    @Schema(description =  "单元格数据")
    private List<CellDataVo> cellDatas;



    public void clearDb(){
        super.clearDb();
        this.setRandomTag(null);
        this.setExcelId(null);
        if(ValueUtils.isNotBlank(this.getCellMerges())){
            for (CellMerge cellMerge : this.getCellMerges()){
                cellMerge.clearDb();
            }
        }
        if(ValueUtils.isNotBlank(this.getCellDatas())){
            for (CellData cellData : this.getCellDatas()){
                cellData.clearDb();
            }
        }
        this.synProperties();
    }


    @Override
    public String sheetName() {
        return this.getName();
    }

    @Override
    public void putSheetName(String name) {
        this.setName(name);
    }

    @Override
    public List<ICellData> cellDatas() {
        List datas = this.cellDatas;
        return datas;
    }

    @Override
    public void putCellDatas(List<ICellData> cellDatas) {
        List datas = cellDatas;
        this.cellDatas = datas;
    }

    @Override
    public List<ICellMerge> cellMerges() {
        List datas = this.cellMerges;
        return datas;
    }

    @Override
    public void putCellMerges(List<ICellMerge> cellMerges) {
        List datas = cellMerges;
        this.cellMerges = datas;

    }

    @Override
    public IGridInfo gridInfo() {
        return this.getGridInfo();
    }

    @Override
    public void putGridInfo(IGridInfo gridInfo) {
        this.setGridInfo((GridInfo) gridInfo);
    }

}
