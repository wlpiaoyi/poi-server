package com.filling.module.poi.form.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.filling.framework.common.tools.ValueUtils;
import com.filling.framework.common.tools.data.DataUtils;
import com.filling.framework.common.exception.BusinessException;
import com.filling.module.poi.form.domain.entity.FormHead;
import lombok.Data;
import lombok.SneakyThrows;

import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/23 15:21
 * {@code @version:}:       1.0
 */
@Data
public class FormHeadVo extends FormHead {

    @JsonIgnore
    private List<FormHeadVo> childrenHeads;

    @JsonIgnore
    @TableField(exist = false)
    private WeakReference<FormHeadVo> parentHead;


    @SneakyThrows
    public String resetColumnCode(){
        if(ValueUtils.isBlank(this.getProperty())){
            return null;
        }
        String vp = this.getProperty();
        FormHeadVo formHead = this;
        while (formHead.getParentHead() != null && (formHead = formHead.getParentHead().get()) != null){
            vp += "_" + formHead.getParentHead();
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(vp.getBytes(StandardCharsets.UTF_8));
        byte[] md5b = md.digest();
        this.setColumnCode(new String(DataUtils.base64Encode(md5b)).replaceAll("\\+", "").replaceAll("/", "").replaceAll("=", ""));
        return this.getColumnCode();
    }

    public void setParentHead(WeakReference<FormHeadVo> parentHead) {
        this.parentHead = parentHead;
        if(this.parentHead == null){
            return;
        }
        if(this.parentHead.get() == null){
            return;
        }
        this.setParentId(parentHead.get().getId());
    }

    public int synDeep(){
        if(this.getParentHead() == null){
            this.setDeep(1);
        }else{
            if(this.getParentHead().get() == null){
                throw new BusinessException("上级节点已经被回收");
            }
            this.setDeep(this.getParentHead().get().synDeep() + 1);
        }
        return this.getDeep();
    }

    public void clearDb(){
        super.clearDb();
        this.setFormId(null);
//        this.setIsLeaf((byte) 0);
        this.setDataId(null);
//        this.setDeep(null);
        this.setParentId(null);
        this.childrenHeads = null;
        this.parentHead = null;
    }

    public static <T extends FormHead> void checkDatas(List<T> formHeads){
        Set<String> columnSet = new HashSet<>(formHeads.size());
        Set<String> propertySet = new HashSet<>(formHeads.size());
        for (FormHead formHead : formHeads){
            if(ValueUtils.isBlank(formHead.getFormId())){
                throw new BusinessException("formId不能为空");
            }
            if(formHead.getLocation() == null){
                throw new BusinessException("cellData不能为空");
            }
            if(ValueUtils.isBlank(formHead.getDirection())){
                throw new BusinessException("direction不能为空");
            }
            if(ValueUtils.isBlank(formHead.getDeep())){
                throw new BusinessException("deep不能为空");
            }
            if(ValueUtils.isBlank(formHead.getProperty())){
                throw new BusinessException("字段不能为空");
            }
            if (ValueUtils.isBlank(formHead.getColumnCode())){
                if(formHead instanceof FormHeadVo){
                    String columnCode = ((FormHeadVo) formHead).resetColumnCode();
                    int i = 10;
                    while (i-- > 0 && columnSet.contains(columnCode)){
                        columnCode = ((FormHeadVo) formHead).resetColumnCode();
                    }
                    if(columnSet.contains(columnCode)){
                        throw new BusinessException("动态生成字段名称超时");
                    }
                    formHead.setColumnCode(columnCode);
                }
                if(ValueUtils.isBlank(formHead.getColumnCode())){
                    throw new BusinessException("表单列不能为空");
                }
                if(columnSet.contains(formHead.getColumnCode())){
                    throw new BusinessException("表头列名重复:" + formHead.getColumnCode());
                }
                if(propertySet.contains(formHead.getProperty())){
                    throw new BusinessException("表头字段名重复:" + formHead.getProperty());
                }
                columnSet.add(formHead.getColumnCode());
                propertySet.add(formHead.getProperty());
            }

        }
    }
}
