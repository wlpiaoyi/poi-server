package com.filling.module.poi.tools.excel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;

import java.util.List;
import java.util.Map;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 9:46
 * {@code @version:}:       1.0
 */
@Data
public class DataStyle {


//    /** 0:String 1:数字 2:日期 **/
//    private int type;
//    /** 数据值 **/
//    private String v;
//    /** 展示值 **/
//    private String m;
//    /** 公式 **/
//    private String f;
//    /** 合并单元格 **/
//    private Scope mc;
    /** 作用范围 **/
    private List<Point> points = null;
//    /** 单元格值格式 **/
//    private Map ct;
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
    /** 垂直对齐  0 中间、1 上、2下 **/
    private int vt = 0;   //
    /** 水平对齐   0 居中、1 左、2 右 **/
    private int ht = 0;
    /** 自动换行   0 截断、1 溢出、2 自动换行 **/
    private int tb = 0;
    /** 单元格数据格式 **/
    private short dfm;
    /** 单元格数据格式 **/
    @JsonIgnore
    private String dfmv;

//    @JsonIgnore
//    private XSSFDataFormat dataFormat;

    public String toString(){
        return "bg:" + this.getBg()
                + ",ff:" + this.ff
                + ",fc:" + this.fc
                + ",it:" + this.it
                + ", bl:" + this.bl
                + ", fs:" + this.fs
                + ",un:" + this.un
                + ",vt:" + this.vt
                + ",ht:" + this.ht
                + ",tb:" + this.tb
                + ",dfmv:" + this.dfmv;
    }

}
