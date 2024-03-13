package com.icss.poie.biz.excel.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.model.ICellValue;
import com.icss.poie.tools.excel.model.Scope;
import lombok.Data;
import lombok.experimental.Accessors;

import java.beans.Transient;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/18 16:03
 * {@code @version:}:       1.0
 */
@Data
@Accessors(chain=false)
public class CellValue implements ICellValue {

    /**
     * 是否为空
     */
    @JsonIgnore
    @Transient
    public boolean isEmpty(){
        if(ValueUtils.isNotBlank(this.getV())){
            return false;
        }
        if(ValueUtils.isNotBlank(this.getM())){
            return false;
        }
        if(ValueUtils.isNotBlank(this.getF())){
            return false;
        }
        if(this.getMc() != null && !this.getMc().isEmpty()){
            return false;
        }
        return true;
    }

    /** 0:String 1:数字 **/
    private int type;
    /** 数据值 **/
    private String v;
    /** 展示值 **/
    private String m;
    /** 公式 **/
    private String f;
    /** 合并单元格 **/
    private Scope mc;

}
