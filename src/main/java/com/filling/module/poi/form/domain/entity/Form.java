package com.filling.module.poi.form.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.filling.module.poi.domain.entity.BaseMongoEntity;
import com.filling.module.poi.form.domain.model.FormPosition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;


/**
 * {@code @author:} 		wlpia:WLPIAOYI-DELL
 * {@code @description:} 	一维表单 实体类
 * {@code @date:} 			2023-09-23 14:16:53
 * {@code @version:}: 		1.0
 */
@Data
@Schema(description = "表单")
@EqualsAndHashCode(callSuper = true)
public class Form extends BaseMongoEntity {

    public static String collectionName(){
        return "poi_form";
    }

    /** tableCode **/
    @Schema(description = "tableCode")
    private String tableCode;

    /** 表单名称 **/
    @Schema(description = "表单名称")
    private String name;

    /** 表类型 **/
    @Schema(description = "表类型 1:Excel表单 2:数据表单")
    private int type = 1;

    /** 方向 1:一维横向 2:一维纵向 3:二维  4:登记表 **/
    @Schema(description = "方向 1:一维横向 2:一维纵向 3:二维 4:登记表")
    @NotNull(message = "方向不能为空")
    private Byte direction;

    /** 模板Id **/
    @Schema(description = "模板Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId templateId;

    /** 模板对应的ExcelId **/
    @Schema(description = "模板对应的ExcelId")
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId templateExcelId;

    /** 模板对应的SheetId **/
    @Schema(description = "模板对应的SheetId")
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId templateSheetId;

    /** 表头位置 **/
    @Schema(description = "表头位置")
    private FormPosition formPosition;

    /** 拓展信息 **/
    @Schema(description = "拓展信息")
    private String dataExpand;


    public void clearDb(){
        super.clearDb();
    }


}
