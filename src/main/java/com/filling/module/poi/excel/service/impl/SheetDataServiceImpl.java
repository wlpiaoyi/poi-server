package com.filling.module.poi.excel.service.impl;

import com.filling.framework.common.exception.BusinessException;
import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.excel.domain.entity.CellData;
import com.filling.module.poi.excel.domain.entity.SheetData;
import com.filling.module.poi.excel.domain.vo.CellDataVo;
import com.filling.module.poi.excel.domain.vo.SheetDataVo;
import com.filling.module.poi.excel.domain.wrapper.BaseWrapper;
import com.filling.module.poi.excel.service.ICellDataService;
import com.filling.module.poi.excel.service.ISheetDataService;
import com.filling.module.poi.service.impl.BaseMongoServiceImpl;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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


    @Override
    public SheetDataVo detail(ObjectId id) {
        SheetDataVo sheetDataVo = this.baseTemplate.findOne(new Query(Criteria.where("_id").is(id)),
                SheetDataVo.class);
        if(sheetDataVo == null){
            return null;
        }
        List<CellData> cellDatas = this.cellDataService.queryBySheetId(sheetDataVo.getId(), sheetDataVo.getRandomTag());
        if(ValueUtils.isNotBlank(cellDatas)){
            sheetDataVo.setCellDatas(BaseWrapper.parseList(cellDatas, CellDataVo.class));
        }
        return sheetDataVo;
    }

    @Override
    public List<SheetData> queryByExcelId(ObjectId excelId, Class<SheetData> entityClazz) {
        Criteria criteria = Criteria.where("excelId").is(excelId);
        return this.queryList(criteria);
    }

    public SheetData insert(SheetData entity) {
        if(entity.getId() == null){
            entity.setId(ObjectId.get());
        }
        entity.createRandomTag();
        if(entity instanceof SheetDataVo){
            SheetDataVo entityVo = (SheetDataVo) entity;
            entityVo.synCellMc();
            if(ValueUtils.isNotBlank(entityVo.getCellDatas())){
                ((SheetDataVo) entity).removeBlankCellData();
                for (CellData cellData : entityVo.getCellDatas()){
                    cellData.setSheetId(entity.getId());
                }
                this.cellDataService.insertBatch(entityVo.getCellDatas(), SheetData.cellDataCollectionName(entityVo.getRandomTag()));
            }
        }
        SheetData iEntity = BaseWrapper.parseOne(entity, SheetData.class);
        return super.insert(iEntity);
    }
    public SheetData insert(SheetData entity, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public Collection<SheetData> insertBatch(List<SheetData> entities, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public UpdateResult update(SheetData entity) {
        if(entity.getId() == null){
            throw new BusinessException("Id不能为空");
        }
        SheetData db = this.findOne(entity.getId());
        if(db == null){
            throw new BusinessException("没有找到数据");
        }
        if(entity instanceof SheetDataVo){
            SheetDataVo entityVo = (SheetDataVo) entity;
            entityVo.synCellMc();
            if(ValueUtils.isNotBlank(entityVo.getCellDatas())){
                ((SheetDataVo) entity).removeBlankCellData();
                for (CellData cellData : entityVo.getCellDatas()){
                    cellData.setSheetId(entity.getId());
                }
                List<CellData> removes = this.cellDataService.queryBySheetId(db.getId(), db.getRandomTag());
                if(ValueUtils.isNotBlank(removes)){
                    this.cellDataService.removeBatch(removes.stream().map(CellData::getId).collect(Collectors.toList()), SheetData.cellDataCollectionName(db.getRandomTag()));
                }
                this.cellDataService.insertBatch(entityVo.getCellDatas(), SheetData.cellDataCollectionName(db.getRandomTag()));
            }
        }
        SheetData iEntity = BaseWrapper.parseOne(entity, SheetData.class);
        return super.update(iEntity);
    }
    @Override
    public UpdateResult update(SheetData entity, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public BulkWriteResult updateBatch(List<SheetData> entities) {
        throw new BusinessException("不支持的方法");
    }
    @Override
    public BulkWriteResult updateBatch(List<SheetData> entities, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public long removeBatch(List<ObjectId> ids) {
        Criteria criteria = Criteria.where("id").in(ids);
        List<SheetData> sheetDatas = this.queryList(criteria);
        for(SheetData sheetData : sheetDatas){
            this.baseTemplate.remove(new Query(Criteria.where("sheetId").is(sheetData.getId())),
                    SheetData.cellDataCollectionName(sheetData.getRandomTag()));
        }
        return super.removeBatch(ids);
    }
    @Override
    public long removeBatch(List<ObjectId> ids, String collectionName) {
        throw new BusinessException("不支持的方法");

    }
}
