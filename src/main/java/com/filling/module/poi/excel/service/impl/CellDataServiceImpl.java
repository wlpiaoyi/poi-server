package com.filling.module.poi.excel.service.impl;

import com.filling.framework.common.tools.ValueUtils;
import com.filling.framework.common.exception.BusinessException;
import com.filling.module.poi.excel.domain.entity.CellData;
import com.filling.module.poi.excel.domain.entity.ExcelData;
import com.filling.module.poi.excel.domain.entity.SheetData;
import com.filling.module.poi.excel.domain.vo.CellDataVo;
import com.filling.module.poi.excel.domain.wrapper.BaseWrapper;
import com.filling.module.poi.excel.service.ICellDataService;
import com.filling.module.poi.service.impl.BaseMongoServiceImpl;
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
public class CellDataServiceImpl extends BaseMongoServiceImpl<CellData> implements ICellDataService<CellData> {

    @Override
    public List<CellData> queryBySheetId(ObjectId sheetId, int randomTag) {
        Criteria criteria = Criteria.where("sheetId").is(sheetId);
        List<CellData> datas = this.queryList(criteria, SheetData.cellDataCollectionName(randomTag));
        return datas;
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


    @Override
    public BulkWriteResult updateBatch(List<CellData> entities) {
        List<CellData> iEntities = BaseWrapper.parseList(entities, CellData.class);
        return super.updateBatch(iEntities);
    }
    @Override
    public BulkWriteResult updateBatch(List<CellData> entities, String collectionName) {
        List<CellData> iEntities = BaseWrapper.parseList(entities, CellData.class);
        return super.updateBatch(iEntities, collectionName);
    }

    @Override
    public UpdateResult update(CellData entity, int randomTag) {
        CellData iEntity = BaseWrapper.parseOne(entity, CellData.class);
        return super.update(iEntity, SheetData.cellDataCollectionName(randomTag));
    }

    @Override
    public boolean updateBatch(Collection<CellData> entities, ObjectId sheetId, int randomTag) {
        List<CellData> dbs = this.queryBySheetId(sheetId, randomTag);
        Map<String, CellData> dbMaps = dbs.stream().collect(Collectors.toMap(
                CellData::mapKey, Function.identity()
        ));
        List<CellData> opts = new ArrayList(){{addAll(entities);}};
        List<CellData> adds = new ArrayList<>();
        List<CellData> updates = new ArrayList<>();
        List<CellData> removes = new ArrayList<>();
        for(CellData entity : opts){
            if(dbMaps.containsKey(entity.mapKey())){
                updates.add(entity);
            }else{
                if(entity.getId() == null){
                    entity.setId(ObjectId.get());
                }
                adds.add(entity);
            }
        }
        opts.removeAll(adds);
        opts.removeAll(updates);
        if(ValueUtils.isNotBlank(opts)){
            Map<String, CellData> optMaps = opts.stream().collect(Collectors.toMap(
                    CellData::mapKey, Function.identity()
            ));
            for (CellData entity : dbs){
                if(!optMaps.containsKey(entity.mapKey())){
                    removes.add(entity);
                }
            }
        }
        if(ValueUtils.isNotBlank(updates)){
            this.updateBatch(updates, SheetData.cellDataCollectionName(randomTag));
        }
        if(ValueUtils.isNotBlank(adds)){
            super.insertBatch(adds, SheetData.cellDataCollectionName(randomTag));
        }
        if(ValueUtils.isNotBlank(removes)){
            List<ObjectId> ids = removes.stream().map(CellData::getId).collect(Collectors.toList());
            super.removeBatch(ids, SheetData.cellDataCollectionName(randomTag));
        }
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
