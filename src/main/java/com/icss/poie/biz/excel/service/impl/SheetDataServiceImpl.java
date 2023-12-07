package com.icss.poie.biz.excel.service.impl;

import com.icss.poie.biz.excel.domain.entity.SheetData;
import com.icss.poie.biz.excel.domain.vo.CellDataVo;
import com.icss.poie.biz.excel.domain.vo.SheetDataVo;
import com.icss.poie.biz.excel.domain.wrapper.BaseWrapper;
import com.icss.poie.biz.excel.service.ICellDataService;
import com.icss.poie.biz.excel.service.ISheetDataService;
import com.icss.poie.framework.common.exception.BusinessException;
import com.icss.poie.framework.common.tools.MongoTransactional;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.biz.excel.domain.entity.CellData;
import com.icss.poie.service.impl.BaseMongoServiceImpl;
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
import java.util.Map;
import java.util.function.Function;
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
        long pointTime = System.currentTimeMillis();
        log.info("sheet start detail dataId[{}]", id.toHexString());
        SheetDataVo sheetDataVo = this.baseTemplate.findOne(new Query(Criteria.where("_id").is(id)),
                SheetDataVo.class);
        if(sheetDataVo == null){
            return null;
        }
        List<CellData> cellDatas = this.cellDataService.queryBySheetId(sheetDataVo.getId(), sheetDataVo.getRandomTag());
        if(ValueUtils.isNotBlank(cellDatas)){
            sheetDataVo.setCellDatas(BaseWrapper.parseList(cellDatas, CellDataVo.class));
        }
        log.info("sheet end detail dataId[{}], duriTime:{}ms", id.toHexString(), System.currentTimeMillis() - pointTime);
        return sheetDataVo;
    }

    @Override
    public List<SheetData> queryByExcelId(ObjectId excelId) {
        Criteria criteria = Criteria.where("excelId").is(excelId);
        return this.queryList(criteria);
    }
    @MongoTransactional
    @Override
    public SheetData insert(SheetData entity) {
        if(entity.getId() == null){
            entity.setId(ObjectId.get());
        }
        if(ValueUtils.isBlank(entity.getSheetName())){
            throw new BusinessException("SheetName不能为空");
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
    @MongoTransactional
    public Collection<SheetData> insertBatch(List<SheetData> entities) {
        long pointTime = System.currentTimeMillis();
        log.info("sheet start insertBath dataId[{}]", entities.hashCode());
        for (SheetData entity : entities){
            if(entity.getId() == null){
                entity.setId(ObjectId.get());
            }
            if(ValueUtils.isBlank(entity.getSheetName())){
                throw new BusinessException("SheetName不能为空");
            }
            entity.createRandomTag();
            if(entity instanceof SheetDataVo){
                log.info("sheet start insertBath handle dataId[{}][{}]", entities.hashCode(), entity.hashCode());
                SheetDataVo entityVo = (SheetDataVo) entity;
                entityVo.synCellMc();
                if(ValueUtils.isNotBlank(entityVo.getCellDatas())){
                    ((SheetDataVo) entity).removeBlankCellData();
                    for (CellData cellData : entityVo.getCellDatas()){
                        cellData.setSheetId(entity.getId());
                    }
                    log.info("sheet start insertBath insertCellBatch dataId[{}][{}]", entities.hashCode(), entity.hashCode());
                    this.cellDataService.insertBatch(entityVo.getCellDatas(),
                            SheetData.cellDataCollectionName(entityVo.getRandomTag()));
                    log.info("sheet end insertBath insertCellBatch dataId[{}][{}]", entities.hashCode(), entity.hashCode());

                }
                log.info("sheet end insertBath handle dataId[{}][{}]", entities.hashCode(), entity.hashCode());
            }
        }
        List<SheetData> iEntities = BaseWrapper.parseList(entities, SheetData.class);
        Collection<SheetData> result = super.insertBatch(iEntities);
        log.info("sheet end insertBath dataId[{}], duriTime:{}ms", entities.hashCode(), System.currentTimeMillis() - pointTime);
        return result;
    }

    @Override
    public Collection<SheetData> insertBatch(List<SheetData> entities, String collectionName) {
        throw new BusinessException("不支持的方法");
    }
    @MongoTransactional
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

    @MongoTransactional
    @Override
    public BulkWriteResult updateBatch(List<SheetData> entities) {
        long pointTime = System.currentTimeMillis();
        log.info("sheet start updateBath dataId[{}]", entities.hashCode());
        List<SheetData> dbs = this.queryList(Criteria.where("_id").in(
                entities.stream().map(SheetData::getId).collect(Collectors.toList())
        ));
        if(entities.size() != dbs.size()){
            throw new BusinessException("不能修改不存在的数据");
        }
        Map<String, SheetData> dbMap = dbs.stream().collect(Collectors.toMap(
                SheetData::mapKey, Function.identity()
        ));
        for (SheetData entity : entities){
            if(entity.getId() == null){
                throw new BusinessException("Id不能为空");
            }
            log.info("sheet start updateBatch handle dataId[{}][{}]", entities.hashCode(), entity.getId().toHexString());
            if(entity instanceof SheetDataVo){
                SheetData db = dbMap.get(entity.mapKey());
                if(db == null){
                    throw new BusinessException("没有找到数据");
                }
                SheetDataVo entityVo = (SheetDataVo) entity;
                entityVo.removeBlankCellData();
                entityVo.synCellMc();
                log.info("sheet start updateBatch updateCellBatch dataId[{}][{}]", entities.hashCode(), entity.getId().toHexString());
                List<CellData> removes = this.cellDataService.queryBySheetId(db.getId(), db.getRandomTag());
                if(ValueUtils.isNotBlank(removes)){
                    this.cellDataService.removeBatch(removes.stream().map(CellData::getId).collect(Collectors.toList()), SheetData.cellDataCollectionName(db.getRandomTag()));
                }
                this.cellDataService.insertBatch(entityVo.getCellDatas(), SheetData.cellDataCollectionName(db.getRandomTag()));
                log.info("sheet end updateBatch updateCellBatch dataId[{}][{}]", entities.hashCode(), entity.getId().toHexString());
            }
            log.info("sheet end updateBatch handle dataId[{}][{}]", entities.hashCode(), entity.getId().toHexString());
        }
        List<SheetData> iEntities = BaseWrapper.parseList(entities, SheetData.class);
        BulkWriteResult result = super.updateBatch(iEntities);
        log.info("sheet end updateBath dataId[{}], duriTime:{}ms", entities.hashCode(), System.currentTimeMillis() - pointTime);
        return result;
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
