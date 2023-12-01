package com.filling.module.poi.form.service;

import com.filling.module.poi.form.domain.entity.FormHead;
import com.filling.module.poi.service.IBaseMongoService;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/28 15:11
 * {@code @version:}:       1.0
 */
public interface IFormHeadService<M extends FormHead> extends IBaseMongoService<M> {

    List<M> queryListByFormId(ObjectId formId);


    long removeBatchByFormIds(List<ObjectId> ids);

}
