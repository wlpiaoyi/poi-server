package com.filling.module.poi.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filling.framework.common.exception.BusinessException;
import com.filling.framework.common.tools.ValueUtils;
import com.filling.framework.common.tools.gson.GsonBuilder;
import com.filling.module.poi.domain.entity.BaseMongoEntity;
import com.filling.module.poi.service.IBaseMongoService;
import com.google.gson.Gson;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.*;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/24 10:23
 * {@code @version:}:       1.0
 */
public class BaseMongoServiceImpl<M extends BaseMongoEntity> implements IBaseMongoService<M> {

    protected Class<M> currentModelClass() {
        return (Class)this.getResolvableType().as(BaseMongoServiceImpl.class).getGeneric(new int[]{0}).getType();
    }

    protected ResolvableType getResolvableType() {
        return ResolvableType.forClass(ClassUtils.getUserClass(this.getClass()));
    }

    @Autowired(required = false)
    protected MongoTemplate baseTemplate;

    public M findOne(ObjectId id, String collectionName){
        return this.baseTemplate.findOne(new Query(Criteria.where("_id").is(id)), this.currentModelClass(), collectionName);
    }


    @Override
    public List<M> queryList(Criteria criteria, String collectionName) {
        Query query;
        if(criteria == null){
            query = new Query();
        }else{
            query = new Query(criteria);
        }
        List<M> list = baseTemplate.find(query, currentModelClass(), collectionName);
        return list;
    }

    @Override
    public Page<M> queryPage(Query query, String collectionName) {
        //获取总数量
        long total = baseTemplate.count(query, collectionName);
        Page<M> page = new Page<>();
        page.setTotal(total);
        if(query.getSkip() >= total){
            return page;
        }
        if(query.getSkip() < 0){
            query.skip(0);
        }
        if(query.getLimit() <= 0){
            query.limit(Integer.MAX_VALUE);
        }
        //查询当前页的数据。
        List<M> list = baseTemplate.find(query, currentModelClass(), collectionName);
        page.setCurrent(query.getSkip() / query.getLimit());
        page.setSize(query.getLimit());
        page.setRecords(list);
        return page;
    }

    @Override
    public M insert(M entity, String collectionName) {
        entity.setCreateTime(new Date());
        if(entity.getId() == null){
            entity.setId(ObjectId.get());
        }
        return this.baseTemplate.insert(entity, collectionName);
    }


    @Override
    public Collection<M> insertBatch(List<M> entities, String collectionName) {
        for (M entity : entities){
            entity.setCreateTime(new Date());
            if(entity.getId() == null){
                entity.setId(ObjectId.get());
            }
        }
        return this.baseTemplate.insert(entities, collectionName);
    }

    @Override
    public UpdateResult update(M entity, String collectionName) {
        if(ValueUtils.isBlank(entity.getId())){
            throw new BusinessException("更新数据Id不能为空");
        }
        Criteria criteria = Criteria.where("_id").is(entity.getId());
        Update update = entity.parseForUpdate(null);
        return this.baseTemplate.updateFirst(new Query(criteria), update, collectionName);
    }

    public BulkWriteResult updateBatch(List<M> entities, String collectionName) {

        BulkOperations operations = this.baseTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, collectionName);
        for (M entity : entities) {
            Update update = entity.parseForUpdate(null);
            operations.updateOne(Query.query(Criteria.where("id").is(entity.getId())), update);
        }
        return operations.execute();
    }

    @Override
    public long removeBatch(List<ObjectId> ids, String collectionName) {
        Criteria criteria = Criteria.where("_id").in(ids);
        DeleteResult res = this.baseTemplate.remove(new Query(criteria), collectionName);
        return res.getDeletedCount();
    }
}
