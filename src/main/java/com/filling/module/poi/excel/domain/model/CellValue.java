package com.filling.module.poi.excel.domain.model;

import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.tools.utils.excel.ICellValue;
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

    @Data
    public static class MergeCell {

        /** 起始列 **/
        private int c;

        /** 合并列数量 **/
        private int cs;

        /** 起始行 **/
        private int r;

        /** 合并行数量 **/
        private int rs;
        public boolean isNotEmpty(){
            if(this.c >= 0 && this.cs > 0){
                return true;
            }
            if(this.r >= 0 && this.rs > 0){
                return true;
            }
            return false;
        }

    }

    /**
     * 是否为空
     * @return
     */
    public boolean isNotEmpty(){
        if(ValueUtils.isNotBlank(this.getV())){
            return true;
        }
        if(ValueUtils.isNotBlank(this.getM())){
            return true;
        }
        if(ValueUtils.isNotBlank(this.getF())){
            return true;
        }
        if(this.getMc() != null && this.getMc().isNotEmpty()){
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
    private MergeCell mc;
    /** 单元格值格式 **/
    private Map ct;
    /** 背景色 **/
    private String bg = "#FFFFFF";
    /** 字体 **/
    private String ff = "宋体";
    /** 字体颜色 **/
    private String fc = "#000000";
    /** 斜体 **/
    private int it = 0;
    /** 是否加粗 **/
    private int bl = 0;
    /** 字体大小 **/
    private short fs = 10;
    /** 下滑线 **/
    private byte un = 0;
    private int vt = 0;   // 垂直对齐  0 中间、1 上、2下
    private int ht = 0;       // 水平对齐   0 居中、1 左、2 右
    private int tb = 0;       // 自动换行   0 截断、1 溢出、2 自动换行


}
