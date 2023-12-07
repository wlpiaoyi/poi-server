package com.icss.poie.biz.excel.domain.vo;

import com.icss.poie.biz.excel.domain.entity.SheetData;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.biz.excel.domain.entity.CellData;
import com.icss.poie.biz.excel.domain.model.CellValue;
import com.icss.poie.tools.excel.GridInfo;
import com.icss.poie.tools.excel.ICellData;
import com.icss.poie.tools.excel.ISheetData;
import com.icss.poie.tools.excel.Scope;
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
                if(cellData.getV().notEmpty()){
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
        return this.getSheetName();
    }

    @Override
    public void putSheetName(String name) {
        this.setSheetName(name);
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
