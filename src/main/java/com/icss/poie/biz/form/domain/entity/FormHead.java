package com.icss.poie.biz.form.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.icss.poie.domain.entity.BaseMongoEntity;
import com.icss.poie.biz.excel.domain.entity.CellData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * {@code @author:} 		wlpia:WLPIAOYI-DELL
 * {@code @description:} 	表单头 实体类
 * {@code @date:} 			2023-09-23 14:16:53
 * {@code @version:}: 		1.0
 */
@Data
@Schema(description = "表单头")
@Document(collection = "poi_form_head")
@EqualsAndHashCode(callSuper = true)
public class FormHead extends BaseMongoEntity {

//    public static String collectionName(){
//        return "poi_form_head";
//    }

    public void onlyMainDataForLocation(){
        if(this.location != null){
            this.location = this.location.copyOnlyMainData(CellData.class);
        }
    }

    @JsonIgnore
    public String mapKey(){
        if(this.location == null){
            return null;
        }
        return this.location.mapKey();
    }

    public String hexDataId(){
        if(this.getDataId() == null){
            return null;
        }
        return this.getDataId().toHexString();
    }


    /** formId **/
    @Schema(description = "formId")
    @NotNull(message = "formId不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    @Indexed
    private ObjectId formId;


    /** 方向 1:横向 2:纵向 3:角 **/
    @Schema(description = "方向 1:横向 2:纵向 3:角 4:二维横向 5:二维纵向 ")
    @NotNull(message = "方向不能为空")
    private Byte direction;

    /** 上级ID **/
    @Schema(description = "上级ID")
    @NotNull(message = "上级ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    @Indexed
    private ObjectId parentId;

    /** 字段 **/
    @Schema(description = "字段")
    @NotBlank(message = "字段不能为空")
    private String property;

    /** 列 **/
    @Schema(description = "列")
    @NotBlank(message = "列不能为空")
    private String columnCode;

    /** dataId **/
    @Schema(description = "dataId")
    @NotNull(message = "dataId不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId dataId;

    /** sort **/
    @Schema(description = "sort")
    @NotNull(message = "sort不能为空")
    private Long sort;

    /** 深度 **/
    @Schema(description = "深度")
    @NotNull(message = "深度不能为空")
    private Integer deep;

    /** 叶子节点 0:否 1:是 **/
    @Schema(description = "叶子节点 0:否 1:是")
    @NotNull(message = "叶子节点 0:否 1:是不能为空")
    private Byte isLeaf;

    /** 数据类型 **/
    @Schema(description = "数据类型 0:String 1:数字 2:日期")
    @NotNull(message = "数据类型不能为空")
    private Byte dataType;

    /** 表头类型 **/
    @Schema(description = "表头类型 1:普通 2:统计")
    @NotNull(message = "表头类型不能为空")
    private Byte headType;

    /** 表头位置 **/
    @Schema(description = "表头位置")
    @NotNull(message = "表头位置")
    private CellData location;


}
