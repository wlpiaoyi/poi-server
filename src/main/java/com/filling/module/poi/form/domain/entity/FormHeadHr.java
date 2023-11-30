package com.filling.module.poi.form.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.filling.module.poi.domain.entity.BaseMongoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    单元隐藏只读
 * {@code @date:}           2023/11/29 11:40
 * {@code @version:}:       1.0
 */
@Data
@Schema(description = "单元隐藏只读")
@EqualsAndHashCode(callSuper = true)
public class FormHeadHr extends BaseMongoEntity {

    public static String collectionName(){
        return "poi_form_head_hr";
    }

    /** 类型  0:隐藏 2:只读 **/
    @Schema(description = "类型  0:隐藏 2:只读")
    @NotNull(message = "类型  0:隐藏 2:只读不能为空")
    private Byte type;

    /** formId **/
    @Schema(description = "表单Id")
    @NotNull(message = "表单Id不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long formId;

    /** 表数据项code **/
    @Schema(description = "表数据项code")
    private String formHeadCode;

    /** 是否继承 0:否 1:是 **/
    @Schema(description = "是否继承 0:否 1:是")
    private Byte isExtends;

    /** extendsId **/
    @Schema(description = "extendsId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long extendsId;

    @Override
    public void clearDb() {
        super.clearDb();
        this.setFormId(null);
    }
}
