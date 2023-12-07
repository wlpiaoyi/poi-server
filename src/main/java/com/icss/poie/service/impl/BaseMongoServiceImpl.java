package com.icss.poie.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.poie.domain.entity.BaseMongoEntity;
import com.icss.poie.framework.common.exception.BusinessException;
import com.icss.poie.framework.common.tools.MongoTransactional;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.service.IBaseMongoService;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BaseMongoServiceImpl<M extends BaseMongoEntity> implements IBaseMongoService<M> {


    protected Class<M> currentEntityClass() {
        return (Class)this.getResolvableType().as(BaseMongoServiceImpl.class).getGeneric(new int[]{0}).getType();
    }

    protected ResolvableType getResolvableType() {
        return ResolvableType.forClass(ClassUtils.getUserClass(this.getClass()));
    }

    @Autowired(required = false)
    protected MongoTemplate baseTemplate;

    public M findOne(ObjectId id){
        return this.baseTemplate.findOne(new Query(Criteria.where("_id").is(id)), this.currentEntityClass());
    }

    public M findOne(ObjectId id, String collectionName){
        return this.baseTemplate.findOne(new Query(Criteria.where("_id").is(id)), this.currentEntityClass(), collectionName);
    }


    @Override
    public List<M> queryList(Criteria criteria) {
        Query query;
        if(criteria == null){
            query = new Query();
        }else{
            query = new Query(criteria);
        }
        List<M> list = baseTemplate.find(query, currentEntityClass());
        return list;
    }
    @Override
    public List<M> queryList(Criteria criteria, String collectionName) {
        Query query;
        if(criteria == null){
            query = new Query();
        }else{
            query = new Query(criteria);
        }
        List<M> list = baseTemplate.find(query, currentEntityClass(), collectionName);
        return list;
    }

    @Override
    public Page<M> queryPage(Query query) {
        //获取总数量
        long total = baseTemplate.count(query, currentEntityClass());
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
        List<M> list = baseTemplate.find(query, currentEntityClass());
        page.setCurrent(query.getSkip() / query.getLimit());
        page.setSize(query.getLimit());
        page.setRecords(list);
        return page;
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
        List<M> list = baseTemplate.find(query, currentEntityClass(), collectionName);
        page.setCurrent(query.getSkip() / query.getLimit());
        page.setSize(query.getLimit());
        page.setRecords(list);
        return page;
    }



    @Override
    @MongoTransactional
    public M insert(M entity) {
        org.springframework.data.mongodb.core.mapping.Document document = this.currentEntityClass().getAnnotation(org.springframework.data.mongodb.core.mapping.Document.class);
//        this.synIndex(document.collection());
        entity.setCreateTime(new Date());
        if(entity.getId() == null){
            entity.setId(ObjectId.get());
        }
        try{
            log.info("mongoTemplate insert to collection {}, data {}", entity);
            return this.baseTemplate.insert(entity);
        }catch (Exception e){
            throw e;
        }
    }
    @Override
    @MongoTransactional
    public M insert(M entity, String collectionName) {
//        this.synIndex(collectionName);
        entity.setCreateTime(new Date());
        if(entity.getId() == null){
            entity.setId(ObjectId.get());
        }
        try{
            log.info("mongoTemplate insert to collection {}, data {}", collectionName, entity);
            return this.baseTemplate.insert(entity, collectionName);
        }catch (Exception e){
            throw e;
        }
    }


    @Override
    @MongoTransactional
    public Collection<M> insertBatch(List<M> entities) {
//        org.springframework.data.mongodb.core.mapping.Document document = this.currentEntityClass().getAnnotation(org.springframework.data.mongodb.core.mapping.Document.class);
//        this.synIndex(document.collection());
        for (M entity : entities){
            entity.setCreateTime(new Date());
            if(entity.getId() == null){
                entity.setId(ObjectId.get());
            }
        }
        return this.baseTemplate.insert(entities, this.currentEntityClass());
    }
    @Override
    @MongoTransactional
    public Collection<M> insertBatch(List<M> entities, String collectionName) {
//        this.synIndex(collectionName);
        for (M entity : entities){
            entity.setCreateTime(new Date());
            if(entity.getId() == null){
                entity.setId(ObjectId.get());
            }
        }
        return this.baseTemplate.insert(entities, collectionName);
    }

    @Override
    @MongoTransactional
    public UpdateResult update(M entity) {
        if(ValueUtils.isBlank(entity.getId())){
            throw new BusinessException("更新数据Id不能为空");
        }
        Criteria criteria = Criteria.where("_id").is(entity.getId());
        Update update = entity.parseForUpdate(null);
        return this.baseTemplate.updateFirst(new Query(criteria), update, this.currentEntityClass());
    }
    @Override
    @MongoTransactional
    public UpdateResult update(M entity, String collectionName) {
        if(ValueUtils.isBlank(entity.getId())){
            throw new BusinessException("更新数据Id不能为空");
        }
        Criteria criteria = Criteria.where("_id").is(entity.getId());
        Update update = entity.parseForUpdate(null);
        return this.baseTemplate.updateFirst(new Query(criteria), update, this.currentEntityClass(), collectionName);
    }

    @Override
    @MongoTransactional
    public BulkWriteResult updateBatch(List<M> entities) {

        BulkOperations operations = this.baseTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, this.currentEntityClass());
        for (M entity : entities) {
            Update update = entity.parseForUpdate(null);
            operations.updateOne(Query.query(Criteria.where("id").is(entity.getId())), update);
        }
        return operations.execute();
    }
    @Override
    @MongoTransactional
    public BulkWriteResult updateBatch(List<M> entities, String collectionName) {

        BulkOperations operations = this.baseTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, collectionName);
        for (M entity : entities) {
            Update update = entity.parseForUpdate(null);
            operations.updateOne(Query.query(Criteria.where("id").is(entity.getId())), update);
        }
        return operations.execute();
    }

    @Override
    @MongoTransactional
    public long removeBatch(List<ObjectId> ids, String collectionName) {
        Criteria criteria = Criteria.where("_id").in(ids);
        DeleteResult res = this.baseTemplate.remove(new Query(criteria), collectionName);
        return res.getDeletedCount();
    }


    @Override
    @MongoTransactional
    public long removeBatch(List<ObjectId> ids) {
        Criteria criteria = Criteria.where("_id").in(ids);
        DeleteResult res = this.baseTemplate.remove(new Query(criteria), this.currentEntityClass());
        return res.getDeletedCount();
    }
}
