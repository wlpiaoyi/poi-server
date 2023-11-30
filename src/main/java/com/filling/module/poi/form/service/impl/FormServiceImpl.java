package com.filling.module.poi.form.service.impl;

import com.filling.framework.common.exception.BusinessException;
import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.excel.domain.entity.CellData;
import com.filling.module.poi.excel.domain.entity.ExcelData;
import com.filling.module.poi.excel.domain.vo.CellDataVo;
import com.filling.module.poi.excel.domain.vo.ExcelDataVo;
import com.filling.module.poi.excel.domain.vo.SheetDataVo;
import com.filling.module.poi.excel.domain.wrapper.BaseWrapper;
import com.filling.module.poi.excel.service.IExcelDataService;
import com.filling.module.poi.form.domain.entity.Form;
import com.filling.module.poi.form.domain.entity.FormHead;
import com.filling.module.poi.form.domain.vo.FormSheetVo;
import com.filling.module.poi.form.domain.vo.FormHeadVo;
import com.filling.module.poi.form.domain.vo.FormVo;
import com.filling.module.poi.form.service.IFormHeadService;
import com.filling.module.poi.form.service.IFormService;
import com.filling.module.poi.service.IBaseMongoService;
import com.filling.module.poi.service.impl.BaseMongoServiceImpl;
import com.filling.module.poi.tools.excel.IExcelData;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/28 15:33
 * {@code @version:}:       1.0
 */
@Slf4j
@Service
@Primary
public class FormServiceImpl extends BaseMongoServiceImpl<Form> implements IFormService<Form> {

    @Autowired
    private IFormHeadService formHeadService;

    @Autowired
    private IExcelDataService excelDataService;

    public FormSheetVo.Detail detail(ObjectId id){
        Form form = this.findOne(id, Form.collectionName());
        if(form == null){
            return null;
        }
        FormSheetVo.Detail detail = new FormSheetVo.Detail();
        FormVo formVo = BaseWrapper.parseOne(form, FormVo.class);
        List<FormHead> formHeads = this.formHeadService.queryListByFormId(formVo.getId());
        if(ValueUtils.isNotBlank(formHeads)){
            formVo.setFormHeads(BaseWrapper.parseList(formHeads, FormHeadVo.class));
        }
        detail.setForm(formVo);
        if(formVo.getExcelId() != null){
            detail.setExcelData(this.excelDataService.detail(formVo.getExcelId()));
        }
        return detail;
    }

    @Override
    public Form insert(Form entity, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public Collection<Form> insertBatch(List<Form> entities, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    /**
     *
     * @param formExcelData
     */
    public void insert(FormSheetVo.FormExcelData formExcelData) {
        formExcelData.setAllId();
        List<FormHeadVo> formHeadSaves = new ArrayList<>();
        List<FormVo> forms = new ArrayList<>();
        for (FormSheetVo.FormSheetData sheetData : formExcelData.getSheetDatas()){
            for (FormVo form : sheetData.getForms()){
                forms.add(form);
                if(form.getId() == null){
                    form.setId(ObjectId.get());
                }
                form.resetTableName();
                FormVo.checkData(form);
                List<FormHeadVo> formHeads = form.getFormHeads();
                if(form.getType() == 1){
                    CellDataVo.synFormHeadForDataId(sheetData.getCellDatas(), formHeads);
                }
                if(ValueUtils.isNotBlank(formHeads)){
                    formHeadSaves.addAll(formHeads);
                }
                form.synProperties();
                form.onlyMainDataForLocation();
            }
        }

        if (ValueUtils.isNotBlank(formHeadSaves)) {
            this.formHeadService.insertBatch(formHeadSaves, FormHead.collectionName());
        }
        this.excelDataService.insert(formExcelData, ExcelData.collectionName());
        List<Form> iEntities = BaseWrapper.parseList(forms, Form.class);
        super.insertBatch(iEntities, Form.collectionName());

    }

    @Override
    public UpdateResult update(Form entity, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public BulkWriteResult updateBatch(List<Form> entities, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    /**
     *
     * @param formExcelData
     */
    public void update(FormSheetVo.FormExcelData formExcelData) {
        formExcelData.setAllId();
        List<FormHeadVo> formHeadSaves = new ArrayList<>();
        List<FormVo> forms = new ArrayList<>();
        for (FormSheetVo.FormSheetData sheetData : formExcelData.getSheetDatas()){
            for (FormVo form : sheetData.getForms()){
                forms.add(form);
                if(form.getId() == null){
                    throw new BusinessException("表单Id不能为空");
                }
            }
        }
        List<Form> dbs = this.queryList(Criteria.where("_id").in(forms.stream().map(Form::getId).collect(Collectors.toList())), Form.collectionName());
        forms.clear(); forms = null;
        if(ValueUtils.isBlank(dbs)){
            throw new BusinessException("没有找到数据");
        }
        Map<String, Form> dbMap = dbs.stream().collect(Collectors.toMap(Form::mapKey, Function.identity()));

        for (FormSheetVo.FormSheetData sheetData : formExcelData.getSheetDatas()){
            for (FormVo form : sheetData.getForms()){
                Form db = dbMap.get(form.mapKey());
                if (db == null){
                    throw new BusinessException("没有找到当前数据");
                }
                FormVo.checkData(form);
                List<FormHeadVo> formHeads = form.getFormHeads();
                if(form.getType() == 1){
                    CellDataVo.synFormHeadForDataId(sheetData.getCellDatas(), formHeads);
                }
                if(ValueUtils.isNotBlank(formHeads)){
                    formHeadSaves.addAll(formHeads);
                }
                form.synProperties();
                form.onlyMainDataForLocation();
            }
        }
        if (ValueUtils.isNotBlank(formHeadSaves)) {
            this.formHeadService.removeBatchByFormIds(forms.stream().map(Form::getId).collect(Collectors.toList()), Form.collectionName());
            this.formHeadService.insertBatch(formHeadSaves, FormHead.collectionName());
        }
        this.excelDataService.update(formExcelData, ExcelData.collectionName());
        List<Form> iEntities = BaseWrapper.parseList(forms, Form.class);
        super.updateBatch(iEntities, Form.collectionName());
    }
}
