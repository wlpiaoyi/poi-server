package com.icss.poie.biz.form.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.framework.common.tools.data.DataUtils;
import com.icss.poie.domain.entity.BaseMongoEntity;
import com.icss.poie.biz.form.domain.model.FormPosition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


/**
 * {@code @author:} 		wlpia:WLPIAOYI-DELL
 * {@code @description:} 	一维表单 实体类
 * {@code @date:} 			2023-09-23 14:16:53
 * {@code @version:}: 		1.0
 */
@Data
@Schema(description = "表单")
@Document(collection = "poi_form")
@EqualsAndHashCode(callSuper = true)
public class Form extends BaseMongoEntity {

//    public static String collectionName(){
//        return "poi_form";
//    }


    /** tableName **/
    @Schema(description = "tableName")
    private String tableName;

    /** 表单名称 **/
    @Schema(description = "表单名称")
    @NotNull(message = "表单名称不能为空")
    private String name;

    /** 标签 用于生成TableName**/
    @Schema(description = "标签")
    private String tab;

    /** 表类型 **/
    @Schema(description = "表类型 1:Excel表单 2:数据表单")
    private int type = 1;

    /** 方向 1:一维横向 2:一维纵向 3:二维  4:登记表 **/
    @Schema(description = "方向 1:一维横向 2:一维纵向 3:二维 4:登记表")
    @NotNull(message = "方向不能为空")
    private Byte direction;

    /* ExcelId **/
    @Schema(description = "ExcelId")
    @JsonSerialize(using = ToStringSerializer.class)
    @Indexed
    private ObjectId excelId;

    /** SheetId **/
    @Schema(description = "SheetId")
    @JsonSerialize(using = ToStringSerializer.class)
    @Indexed
    private ObjectId sheetId;

    /** 表头位置 **/
    @Schema(description = "表头位置")
    private FormPosition formPosition;

    /** 拓展信息 **/
    @Schema(description = "拓展信息")
    private String dataExpand;


    public void clearDb(){
        super.clearDb();
    }



    @SneakyThrows
    public String resetTableName(){
        if(ValueUtils.isBlank(this.tableName) || ValueUtils.isBlank(this.tab)){
            return null;
        }
        String vp = this.tableName + ":" + this.tab;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(vp.getBytes(StandardCharsets.UTF_8));
        byte[] md5b = md.digest();
        String tableName = new String(DataUtils.base64Encode(md5b))
                .replaceAll("\\+", "")
                .replaceAll("/", "")
                .replaceAll("=", "");
        tableName = "dym_" + tableName;
        this.tableName = tableName;
        return tableName;
    }


}
