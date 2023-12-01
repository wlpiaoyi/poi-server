package com.filling.module.poi.form.service.impl;

import com.filling.framework.common.exception.BusinessException;
import com.filling.module.poi.excel.domain.wrapper.BaseWrapper;
import com.filling.module.poi.form.domain.entity.FormHead;
import com.filling.module.poi.form.domain.vo.FormHeadVo;
import com.filling.module.poi.form.service.IFormHeadService;
import com.filling.module.poi.service.impl.BaseMongoServiceImpl;
import com.filling.module.poi.tools.utils.IdUtils;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/28 15:14
 * {@code @version:}:       1.0
 */
@Slf4j
@Service
@Primary
public class FormHeadServiceImpl extends BaseMongoServiceImpl<FormHead> implements IFormHeadService<FormHead> {

    @Override
    public List<FormHead> queryListByFormId(ObjectId formId) {
        Criteria criteria = Criteria.where("formId").is(formId);
        List<FormHead> datas = this.queryList(criteria);
        return datas;
    }

    @Override
    public FormHead insert(FormHead entity) {
        throw new BusinessException("不支持的方法");
    }
    @Override
    public FormHead insert(FormHead entity, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public Collection<FormHead> insertBatch(List<FormHead> entities) {
        for (FormHead formHead : entities){
            if(formHead.getId() == null){
                formHead.setId(ObjectId.get());
            }
            if(formHead instanceof FormHeadVo){
                FormHeadVo formHeadVo = (FormHeadVo) formHead;
                if(formHeadVo.getParentHead() != null && formHeadVo.getParentHead().get() != null){
                    formHeadVo.setParentId(formHeadVo.getParentHead().get().getId());
                }
//                formHeadVo.setSort(IdUtils.nextId());
            }
        }
        FormHeadVo.checkDatas(entities);
        List<FormHead> iEntities = BaseWrapper.parseList(entities, FormHead.class);
        return super.insertBatch(iEntities);
    }
    @Override
    public Collection<FormHead> insertBatch(List<FormHead> entities, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public UpdateResult update(FormHead entity) {
        throw new BusinessException("不支持的方法");
    }
    @Override
    public UpdateResult update(FormHead entity, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public BulkWriteResult updateBatch(List<FormHead> entities) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public BulkWriteResult updateBatch(List<FormHead> entities, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public long removeBatchByFormIds(List<ObjectId> ids) {
        Criteria criteria = Criteria.where("formId").in(ids);
        DeleteResult res = this.baseTemplate.remove(new Query(criteria), FormHead.class);
        return res.getDeletedCount();
    }
}

