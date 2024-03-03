package com.icss.poie.tools.excel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 9:46
 * {@code @version:}:       1.0
 */
@Data
public class DataStyle extends StyleBase{


    /** 作用范围 **/
    @Schema(description = "作用范围")
    private List<Point> points = null;
    /** 背景色 **/
    @Schema(description = "背景色")
    private String bg = "#FFFFFF";
    /** 字体 **/
    @Schema(description = "字体名称")
    private String ff = "宋体";
    /** 字体颜色 **/
    @Schema(description = "字体颜色")
    private String fc = "#000000";
    /** 斜体 **/
    @Schema(description = "斜体 0:否 1:是")
    private byte it = 0;
    /** 是否加粗 **/
    @Schema(description = "是否加粗")
    private byte bl = 0;
    /** 字体大小 **/
    @Schema(description = "字体大小 默认10")
    private short fs = 10;
    /** 下滑线 **/
    @Schema(description = "下滑线")
    private byte un = 0;
    /** 垂直对齐  0 中间、1 上、2下 **/
    @Schema(description = "垂直对齐")
    private byte vt = 0;
    /** 水平对齐 0 居中、1 左、2 右 **/
    @Schema(description = "水平对齐 0 居中、1 左、2 右 ")
    private byte ht = 0;
    /** 自动换行  0 截断、1 溢出、2 自动换行 **/
    @Schema(description = "自动换行  0 截断、1 溢出、2 自动换行")
    private byte tb = 0;
    /** 单元格数据格式 **/
    @Schema(description = "单元格数据格式 数字")
    private short dfm;
    /** 单元格数据格式 **/
    @Schema(description = "单元格数据格式 值")
    private String dfmv;

    @Transient
    @JsonIgnore
    private Object dataFormat;

    public String toString(){
        if(this._cacheToString_ != null){
            return this._cacheToString_;
        }
        String text = "bg:" + this.bg
                + ",ff:" + this.ff
                + ",fc:" + this.fc
                + ",it:" + this.it
                + ",bl:" + this.bl
                + ",fs:" + this.fs
                + ",un:" + this.un
                + ",vt:" + this.vt
                + ",ht:" + this.ht
                + ",tb:" + this.tb
                + ",dfm:" + this.dfm
                + ",dfmv:" + this.dfmv;

        this._cacheToString_ = text;
        return text;
    }

    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(!(object instanceof DataStyle)){
            return false;
        }
        return super.equals(object);
    }

}
