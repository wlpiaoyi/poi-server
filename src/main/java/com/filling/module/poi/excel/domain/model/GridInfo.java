package com.filling.module.poi.excel.domain.model;

import com.filling.module.poi.tools.utils.excel.IGridInfo;
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
public class GridInfo implements IGridInfo {
    /** 行高 **/
    @Schema(description =  "行高")
    private List<Short> rowHeights;
    /** 列宽 **/
    @Schema(description =  "列宽")
    private List<Short> columnWidths;
    /** 冻结行 **/
    @Schema(description =  "冻结行")
    private List<Integer> frozenRows;
    /** 冻结列 **/
    @Schema(description =  "冻结列")
    private List<Integer> frozenColumns;
    /** 隐藏行 **/
    @Schema(description =  "隐藏行")
    private List<Integer> hiddenRows;
    /** 隐藏列 **/
    @Schema(description =  "隐藏列")
    private List<Integer> hiddenColumns;
    /** 边框信息 **/
    @Schema(description =  "边框信息")
    private String borderInfo;
}
