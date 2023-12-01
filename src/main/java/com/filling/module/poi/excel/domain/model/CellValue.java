package com.filling.module.poi.excel.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.tools.excel.ICellValue;
import com.filling.module.poi.tools.excel.Scope;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

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
     * @return
     */
    @JsonIgnore
    public boolean notEmpty(){
        if(ValueUtils.isNotBlank(this.getV())){
            return true;
        }
        if(ValueUtils.isNotBlank(this.getM())){
            return true;
        }
        if(ValueUtils.isNotBlank(this.getF())){
            return true;
        }
        return false;
    }

    /** 0:String 1:数字 2:日期 **/
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
