package com.icss.poie.biz.form.domain.entity;

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

import javax.validation.constraints.NotNull;

/**
 * 字段关联
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/29 11:24
 * {@code @version:}:       1.0
 */
@Data
@Schema(description = "字段关联")
@Document(collection = "poi_form_head_rela")
@EqualsAndHashCode(callSuper = true)
public class FormHeadRela extends BaseMongoEntity{

    public static String collectionName(){
        return "poi_form_head_rela";
    }

    public String getMapKey(){
        if(this.location == null){
            return null;
        }
        return this.location.mapKey();
    }

    public void onlyMainDataForLocation(){
        if(this.location != null){
            this.location = this.location.copyOnlyMainData(CellData.class);
        }
        if(this.locationRela != null){
            this.locationRela = this.locationRela.copyOnlyMainData(CellData.class);
        }
    }

    /** 当前表单数据位置 **/
    @Schema(description = "当前表单数据位置")
    @NotNull(message = "当前表单数据位置不能为空")
    private CellData location;
    /** 关联表单数据位置 **/
    @Schema(description = "关联表单数据位置")
    private CellData locationRela;

    /** 当前表头Id **/
    @Schema(description = "当前表头Id")
    @NotNull(message = "当前表头Id不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    @Indexed
    private ObjectId headId;
    /** 关联表头Id **/
    @Schema(description = "关联表头Id")
    @JsonSerialize(using = ToStringSerializer.class)
    @Indexed
    private ObjectId headRelaId;

    /** 当前表单Id **/
    @Schema(description = "当前表单Id")
    @NotNull(message = "当前表单Id不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    @Indexed
    private ObjectId formId;
    /** 关联表单Id **/
    @Schema(description = "关联表单Id")
    @JsonSerialize(using = ToStringSerializer.class)
    @Indexed
    private ObjectId formRelaId;

    @Schema(description = "关联字段名字（外部关联时使用）")
    private String relaField;

    /** 显示字段 **/
    @Schema(description =  "显示字段")
    private String propertyName;

    @Schema(description = "是否是索引")
    private Byte isIndex;

    public void clearDb(){
        this.setHeadId(null);
        this.setFormId(null);
    }


}
