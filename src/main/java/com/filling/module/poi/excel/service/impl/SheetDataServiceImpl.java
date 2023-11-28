package com.filling.module.poi.excel.service.impl;

import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.excel.domain.entity.CellData;
import com.filling.module.poi.excel.domain.entity.CellMerge;
import com.filling.module.poi.excel.domain.entity.SheetData;
import com.filling.module.poi.excel.domain.vo.SheetDataVo;
import com.filling.module.poi.excel.service.ICellDataService;
import com.filling.module.poi.excel.service.ICellMergeService;
import com.filling.module.poi.excel.service.ISheetDataService;
import com.filling.module.poi.service.impl.BaseMongoServiceImpl;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/24 12:36
 * {@code @version:}:       1.0
 */
@Slf4j
@Service
@Primary
public class SheetDataServiceImpl extends BaseMongoServiceImpl<SheetData> implements ISheetDataService<SheetData> {

    @Autowired
    private ICellDataService cellDataService;

    @Autowired
    private ICellMergeService cellMergeService;


    @Override
    public SheetDataVo detail(ObjectId id) {
        Criteria criteria = Criteria.where("id").is(id);
        SheetDataVo sheetDataVo = this.baseTemplate.findOne(new Query(criteria), SheetDataVo.class, SheetData.collectionName());
        if(sheetDataVo == null){
            return null;
        }
        sheetDataVo.setCellDatas(this.cellDataService.queryBySheetId(sheetDataVo.getId(), sheetDataVo.getRandomTag()));
        sheetDataVo.setCellMerges(this.cellMergeService.queryBySheetId(sheetDataVo.getId()));
        return sheetDataVo;
    }

    @Override
    public List<SheetData> queryByExcelId(ObjectId excelId, Class<SheetData> entityClazz) {
        Criteria criteria = Criteria.where("excelId").is(excelId);
        return this.queryList(criteria, SheetData.collectionName());
    }

    public SheetData insert(SheetData entity, String collectionName) {
        if(entity.getId() == null){
            entity.setId(ObjectId.get());
        }
        entity.createRandomTag();
        if(entity instanceof SheetDataVo){
            SheetDataVo entityVo = (SheetDataVo) entity;
            if(ValueUtils.isNotBlank(entityVo.getCellDatas())){
                for (CellData cellData : entityVo.getCellDatas()){
                    cellData.setSheetId(entity.getId());
                }
                this.cellDataService.insertBatch(entityVo.getCellDatas(), SheetData.cellDataCollectionName(entityVo.getRandomTag()));
            }
            if(ValueUtils.isNotBlank(entityVo.getCellMerges())){
                for (CellMerge cellMerge : entityVo.getCellMerges()){
                    cellMerge.setSheetId(entity.getId());
                }
                this.cellMergeService.insertBatch(entityVo.getCellMerges(), CellMerge.collectionName());
            }
        }
        return super.insert(entity, collectionName);
    }

    @Override
    public UpdateResult update(SheetData entity, String collectionName) {
        if(entity instanceof SheetDataVo){
            SheetDataVo entityVo = (SheetDataVo) entity;
            if(ValueUtils.isNotBlank(entityVo.getCellDatas())){
                for (CellData cellData : entityVo.getCellDatas()){
                    cellData.setSheetId(entity.getId());
                }
                this.cellDataService.updateBatch(entityVo.getCellDatas(), SheetData.cellDataCollectionName(entityVo.getRandomTag()));
            }
            if(ValueUtils.isNotBlank(entityVo.getCellMerges())){
                for (CellMerge cellMerge : entityVo.getCellMerges()){
                    cellMerge.setSheetId(entity.getId());
                }
                this.cellMergeService.updateBatch(entityVo.getCellMerges(), CellMerge.collectionName());
            }
        }
        return super.update(entity, collectionName);
    }

    @Override
    public DeleteResult removeBatch(List<ObjectId> ids, String collectionName) {
        Criteria criteria = Criteria.where("id").in(ids);
        List<SheetData> sheetDatas = this.queryList(criteria, SheetData.collectionName());
        for(SheetData sheetData : sheetDatas){
            this.baseTemplate.remove(new Query(Criteria.where("sheetId").is(sheetData.getId())),
                    SheetData.cellDataCollectionName(sheetData.getRandomTag()));
        }
        this.baseTemplate.remove(new Query(Criteria.where("sheetId").in(ids)), CellMerge.collectionName());
        return super.removeBatch(ids, collectionName);
    }
}
