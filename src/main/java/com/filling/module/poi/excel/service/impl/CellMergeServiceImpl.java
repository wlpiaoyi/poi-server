package com.filling.module.poi.excel.service.impl;

import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.excel.domain.entity.CellMerge;
import com.filling.module.poi.excel.service.ICellMergeService;
import com.filling.module.poi.service.impl.BaseMongoServiceImpl;
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
 * {@code @date:}           2023/11/24 13:01
 * {@code @version:}:       1.0
 */
@Slf4j
@Service
@Primary
public class CellMergeServiceImpl extends BaseMongoServiceImpl<CellMerge> implements ICellMergeService<CellMerge> {
    @Override
    public List<CellMerge> queryBySheetId(ObjectId sheetId) {
        Criteria criteria = Criteria.where("sheetId").is(sheetId);
        List<CellMerge> datas = this.queryList(criteria, CellMerge.collectionName());
        return datas;
    }


    @Override
    public boolean updateBatch(Collection<CellMerge> entities, ObjectId sheetId) {
        List<CellMerge> dbs = this.queryBySheetId(sheetId);
        Map<String, CellMerge> dbMaps = dbs.stream().collect(Collectors.toMap(
                CellMerge::mapKey, Function.identity()
        ));
        List<CellMerge> opts = new ArrayList(){{addAll(entities);}};
        List<CellMerge> adds = new ArrayList<>();
        List<CellMerge> updates = new ArrayList<>();
        List<CellMerge> removes = new ArrayList<>();
        for(CellMerge entity : opts){
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
            Map<String, CellMerge> optMaps = opts.stream().collect(Collectors.toMap(
                    CellMerge::mapKey, Function.identity()
            ));
            for (CellMerge entity : dbs){
                if(!optMaps.containsKey(entity.mapKey())){
                    removes.add(entity);
                }
            }
        }
        if(ValueUtils.isNotBlank(updates)){
            super.updateBatch(updates, CellMerge.collectionName());
        }
        if(ValueUtils.isNotBlank(adds)){
            super.insertBatch(adds, CellMerge.collectionName());
        }
        if(ValueUtils.isNotBlank(removes)){
            List<ObjectId> ids = removes.stream().map(CellMerge::getId).collect(Collectors.toList());
            super.removeBatch(ids, CellMerge.collectionName());
        }
        return true;
    }
}
