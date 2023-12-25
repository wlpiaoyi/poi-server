package com.icss.poie.biz.excel.service.impl;

import com.icss.poie.biz.excel.domain.entity.GridInfo;
import com.icss.poie.biz.excel.domain.entity.SheetData;
import com.icss.poie.biz.excel.domain.wrapper.BaseWrapper;
import com.icss.poie.biz.excel.service.IGridInfoService;
import com.icss.poie.framework.common.exception.BusinessException;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.service.impl.BaseMongoServiceImpl;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/12/15 16:23
 * {@code @version:}:       1.0
 */
@Slf4j
@Service
@Primary
public class GridInfoServiceImpl  extends BaseMongoServiceImpl<GridInfo> implements IGridInfoService<GridInfo> {


    @Override
    public GridInfo findOneBySheetId(ObjectId sheetId, int randomTag) {
        Criteria criteria = Criteria.where("sheetId").is(sheetId);
        List<GridInfo> datas = this.queryList(criteria, SheetData.gridInfoCollectionName(randomTag));
        if(ValueUtils.isBlank(datas)){
            return null;
        }
        return datas.get(0);
    }

    @Override
    public List<GridInfo> queryBySheets(List<SheetData> sheetDatas) {

        Map<String, List<ObjectId>> sheetIdMap = new HashMap<>();
        for (SheetData sheetData : sheetDatas){
            String collectionName = SheetData.gridInfoCollectionName(sheetData.getGiRandomTag());
            List<ObjectId> objectIds = sheetIdMap.get(collectionName);
            if(objectIds == null){
                objectIds = new ArrayList<>();
                sheetIdMap.put(collectionName, objectIds);
            }
            objectIds.add(sheetData.getId());
        }
        List<GridInfo> gridInfos = new ArrayList<>();
        for (Map.Entry<String, List<ObjectId>> entry : sheetIdMap.entrySet()){
            Criteria criteria = Criteria.where("sheetId").in(entry.getValue());
            List<GridInfo> datas = this.queryList(criteria, entry.getKey());
            if(ValueUtils.isNotBlank(datas)){
                gridInfos.addAll(datas);
            }
        }
        return gridInfos;
    }

    @Override
    public GridInfo insert(GridInfo entity) {
        throw new BusinessException("不支持的方法");
    }
//    @Override
//    public GridInfo insert(GridInfo entity, String collectionName) {
//        throw new BusinessException("不支持的方法");
//    }

    @Override
    public Collection<GridInfo> insertBatch(List<GridInfo> entities) {
        throw new BusinessException("不支持的方法");
    }
//    @Override
//    public Collection<GridInfo> insertBatch(List<GridInfo> entities, String collectionName) {
//        throw new BusinessException("不支持的方法");
//    }

    @Override
    public UpdateResult update(GridInfo entity) {
        throw new BusinessException("不支持的方法");
    }

//    @Override
//    public UpdateResult update(GridInfo entity, String collectionName) {
//        throw new BusinessException("不支持的方法");
//    }


    @Override
    public BulkWriteResult updateBatch(List<GridInfo> entities) {
        throw new BusinessException("不支持的方法");
    }
    @Override
    public BulkWriteResult updateBatch(List<GridInfo> entities, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public UpdateResult update(GridInfo entity, int randomTag) {
        GridInfo iEntity = BaseWrapper.parseOne(entity, GridInfo.class);
        return super.update(iEntity, SheetData.gridInfoCollectionName(randomTag));
    }

}
