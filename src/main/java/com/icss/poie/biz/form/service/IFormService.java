package com.icss.poie.biz.form.service;

import com.icss.poie.biz.form.domain.entity.Form;
import com.icss.poie.biz.form.domain.vo.FormSheetVo;
import com.icss.poie.service.IBaseMongoService;
import org.bson.types.ObjectId;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/28 15:12
 * {@code @version:}:       1.0
 */
public interface IFormService<M extends Form> extends IBaseMongoService<M> {


    /**
     * 详情
     * @param id
     * @return
     */
    FormSheetVo.Detail detail(ObjectId id);

    /**
     * 新增
     * @param formExcelData
     */
    void insert(FormSheetVo.FormExcelData formExcelData);


    /**
     * 修改
     * @param formExcelData
     */
    void update(FormSheetVo.FormExcelData formExcelData);

}
