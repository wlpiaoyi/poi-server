package com.icss.poie.biz.excel.service.impl;

import com.icss.poie.biz.excel.domain.entity.SheetData;
import com.icss.poie.biz.excel.domain.wrapper.BaseWrapper;
import com.icss.poie.framework.common.tools.MongoTransactional;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.framework.common.exception.BusinessException;
import com.icss.poie.biz.excel.domain.entity.CellData;
import com.icss.poie.biz.excel.service.ICellDataService;
import com.icss.poie.service.impl.BaseMongoServiceImpl;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/24 10:46
 * {@code @version:}:       1.0
 */
@Slf4j
@Service
@Primary
public class CellDataServiceImpl extends BaseMongoServiceImpl<CellData> implements ICellDataService {

    @Override
    public List<CellData> queryBySheetId(ObjectId sheetId, int randomTag) {
        Criteria criteria = Criteria.where("sheetId").is(sheetId);
        return this.queryList(criteria, SheetData.cellDataCollectionName(randomTag));
    }

    @Override
    public CellData insert(CellData entity) {
        throw new BusinessException("不支持的方法");
    }
    @Override
    public CellData insert(CellData entity, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public Collection<CellData> insertBatch(List<CellData> entities) {
        for (CellData cellData : entities){
            if(cellData.getId() == null){
                cellData.setId(ObjectId.get());
            }
        }
        List<CellData> iEntities = BaseWrapper.parseList(entities, CellData.class);
        return super.insertBatch(iEntities);
    }
//    @Override
//    public Collection<CellData> insertBatch(List<CellData> entities, String collectionName) {
//        throw new BusinessException("不支持的方法");
//    }

    @Override
    public UpdateResult update(CellData entity) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public UpdateResult update(CellData entity, String collectionName) {
        throw new BusinessException("不支持的方法");
    }


    @MongoTransactional
    @Override
    public BulkWriteResult updateBatch(List<CellData> entities) {
        List<CellData> iEntities = BaseWrapper.parseList(entities, CellData.class);
        return super.updateBatch(iEntities);
    }
    @MongoTransactional
    @Override
    public BulkWriteResult updateBatch(List<CellData> entities, String collectionName) {
        List<CellData> iEntities = BaseWrapper.parseList(entities, CellData.class);
        return super.updateBatch(iEntities, collectionName);
    }

    @MongoTransactional
    @Override
    public UpdateResult update(CellData entity, int randomTag) {
        CellData iEntity = BaseWrapper.parseOne(entity, CellData.class);
        return super.update(iEntity, SheetData.cellDataCollectionName(randomTag));
    }


    public Collection<CellData> insertBatch(List<CellData> entities, ObjectId sheetId, int randomTag) {
        for (CellData cellData : entities){
            cellData.setSheetId(sheetId);
        }
        return super.insertBatch(entities,SheetData.cellDataCollectionName(randomTag));

    }

    @MongoTransactional
    @Override
    public boolean updateBatch(List<CellData> entities, ObjectId sheetId, int randomTag) {
        for (CellData cellData : entities){
            cellData.setSheetId(sheetId);
        }
        this.updateBatch(entities, SheetData.cellDataCollectionName(randomTag));
        return true;
    }

//    @Override
//    public long removeBatch(List<ObjectId> ids) {
//        throw new BusinessException("不支持的方法");
//    }
//
//    @Override
//    public long removeBatch(List<ObjectId> ids, String collectionName) {
//        throw new BusinessException("不支持的方法");
//    }
}
