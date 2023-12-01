package com.filling.module.poi.tools.excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/12/1 12:39
 * {@code @version:}:       1.0
 */
@Data
public class DataValidation {


    /** 验证实体 **/
    @Schema(description = "验证实体")
    private Container container;
    /** 提醒内容 **/
    @Schema(description = "提醒内容")
    private String promptBoxText;
    /** 错误内容 **/
    @Schema(description = "错误内容")
    private String errorBoxText;




    /** 验证范围 **/
    @Schema(description = "验证范围")
    private List<Scope> cellRanges;

    @Data
    public static class Container{

        /** 公式1 **/
        @Schema(description = "公式1")
        private String formula1;

        /** 公式2 **/
        @Schema(description = "公式2")
        private String formula2;

        /** 验证类型 **/
        @Schema(description = "验证类型")
        private int validationType = -1;

        /** 日期格式化 **/
        @Schema(description = "日期格式化")
        private String dateFormat = "YYYY/MM/DD";
        private int operator = -1;

        /** 下拉规则 **/
        @Schema(description = "下拉规则")
        private String[] explicitListOfValues;
    }

}
