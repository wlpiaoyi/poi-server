package com.filling.module.poi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filling.module.poi.domain.entity.BaseMongoEntity;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;
import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/24 10:13
 * {@code @version:}:       1.0
 */
public interface IBaseMongoService <M> {


    List<M> queryList(Criteria criteria, String collectionName);

    Page<M> queryPage(Query query, String collectionName);

    M insert(M entity, String collectionName);

    Collection<M> insertBatch(List<M> entities, String collectionName);

    UpdateResult update(M entity, String collectionName);

    BulkWriteResult updateBatch(List<M> entities, String collectionName);

    DeleteResult removeBatch(List<ObjectId> ids, String collectionName);

}
