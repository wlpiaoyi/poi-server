package com.icss.poie.tools.excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    网格信息
 * {@code @date:}           2023/11/23 14:02
 * {@code @version:}:       1.0
 */
@Data
@Schema(description =  "网格信息")
public class GridInfo{
    /** 行高 **/
    @Schema(description =  "行高")
    private List<Short> rowHeights;
    /** 列宽 **/
    @Schema(description =  "列宽")
    private List<Short> columnWidths;
    /** 冻结窗格 **/
    @Schema(description =  "冻结窗格")
    private Point frozenWindow;
    /** 隐藏行 **/
    @Schema(description =  "隐藏行")
    private List<Integer> hiddenRows;
    /** 隐藏列 **/
    @Schema(description =  "隐藏列")
    private List<Integer> hiddenColumns;
    /** 合并单元格 **/
    @Schema(description =  "合并单元格")
    private List<Scope> cellMerges;
    /** 单元格样式 **/
    @Schema(description =  "单元格样式")
    private List<DataStyle> dataStyles;
    /** 单元格数据验证 **/
    @Schema(description =  "单元格数据验证")
    private List<DataValidation> dataValidations;
//    /** 边框信息 **/
//    @Schema(description =  "边框信息")
//    private List<BorderInfo> borderInfos;






}
