package com.filling.module.poi.excel.service.impl;

import com.filling.framework.common.tools.ValueUtils;
import com.filling.framework.common.exception.BusinessException;
import com.filling.module.poi.excel.domain.entity.CellData;
import com.filling.module.poi.excel.domain.entity.SheetData;
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
    public UpdateResult update(CellData entity, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public BulkWriteResult updateBatch(List<CellData> entities, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public UpdateResult update(CellData entity, int randomTag) {
        return super.update(entity, SheetData.cellDataCollectionName(randomTag));
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
            super.updateBatch(updates, SheetData.cellDataCollectionName(randomTag));
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


}
