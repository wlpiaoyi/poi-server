package com.filling.module.poi.excel.domain.vo;

import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.excel.domain.entity.CellData;
import com.filling.module.poi.excel.domain.entity.SheetData;
import com.filling.module.poi.excel.domain.model.CellValue;
import com.filling.module.poi.tools.excel.GridInfo;
import com.filling.module.poi.tools.excel.ICellData;
import com.filling.module.poi.tools.excel.ISheetData;
import com.filling.module.poi.tools.excel.Scope;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/23 14:46
 * {@code @version:}:       1.0
 */
@Data
public class SheetDataVo extends SheetData implements ISheetData {

    /** 单元格数据 **/
    @Schema(description =  "单元格数据")
    private List<CellDataVo> cellDatas;


    public void setAllId(){
        if(this.getId() == null){
            this.setId(ObjectId.get());
        }
        if(ValueUtils.isNotBlank(this.getCellDatas())){
            for (CellData cellData : this.getCellDatas()){
                if(cellData.getId() == null){
                    cellData.setId(ObjectId.get());
                }
                cellData.setSheetId(this.getId());
            }
        }
    }

    public void removeBlankCellData(){
        if(ValueUtils.isNotBlank(this.getCellDatas())){
            List<CellData> removes = new ArrayList<>();
            for (CellData cellData : this.getCellDatas()){
                if(cellData.getV() == null){
                    removes.add(cellData);
                    continue;
                }
                if(ValueUtils.isNotBlank(cellData.getV().getV())){
                    continue;
                }
                if(ValueUtils.isNotBlank(cellData.getV().getM())){
                    continue;
                }
                if(ValueUtils.isNotBlank(cellData.getV().getF())){
                    continue;
                }
                removes.add(cellData);
            }
            if(ValueUtils.isNotBlank(removes)){
                this.getCellDatas().removeAll(removes);
            }
        }
    }

    public void synCellMc() {
        if(this.getGridInfo() != null && ValueUtils.isNotBlank(this.getGridInfo().getCellMerges())
                && ValueUtils.isNotBlank(this.getCellDatas())){
            for (CellDataVo cellData : this.getCellDatas()){
                for (Scope cellMerge : this.getGridInfo().getCellMerges()){
                    if(cellMerge.inScope(cellData)){
                        if(cellData.getV() == null){
                            cellData.setV(new CellValue());
                        }
                        cellData.getV().setMc(cellMerge);
                        break;
                    }
                }
            }
        }
    }
    public void clearDb(){
        super.clearDb();
        this.setRandomTag(null);
        this.setExcelId(null);
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
    public GridInfo gridInfo() {
        return this.getGridInfo();
    }


    @Override
    public void putGridInfo(GridInfo gridInfo) {
        this.setGridInfo(gridInfo);
    }

}
