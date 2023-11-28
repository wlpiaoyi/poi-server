package com.filling.module.poi.excel.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.filling.module.poi.domain.entity.BaseMongoEntity;
import com.filling.module.poi.tools.utils.excel.ICellMerge;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Update;

import javax.validation.constraints.NotNull;



/**
 * {@code @author:} 		wlpia:WLPIAOYI-DELL
 * {@code @description:} 	 实体类
 * {@code @date:} 			2023-09-21 14:21:12
 * {@code @version:}: 		1.0
 */
@Data
@Accessors(chain=false)
@TableName("poi_cell_merge")
@Schema(description = "")
@EqualsAndHashCode(callSuper = true)
public class CellMerge extends BaseMongoEntity implements ICellMerge {

    public static String collectionName(){
        return "poi_cell_merge";
    }
    public Update parseForUpdate(Update update){
        update = super.parseForUpdate(update);
        if(this.getSheetId() != null){
            update.set("sheetId", this.getSheetId());
        }
        update.set("firstRow", this.getFirstRow());
        update.set("lastRow", this.getLastRow());
        update.set("firstCol", this.getFirstCol());
        update.set("lastCol", this.getLastCol());
        return update;
    }

    /** sheetId **/
    @Schema(description =  "sheetId")
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId sheetId;

    /** 状态 **/
    @Schema(description =  "状态")
    @NotNull(message = "状态不能为空")
    private int firstRow;

    /** 是否删除 **/
    @Schema(description =  "是否删除")
    @NotNull(message = "是否删除不能为空")
    private int lastRow;

    /** firstCol **/
    @Schema(description =  "firstCol")
    @NotNull(message = "firstCol不能为空")
    private int firstCol;

    /** lastCol **/
    @Schema(description =  "lastCol")
    @NotNull(message = "lastCol不能为空")
    private int lastCol;

    public void clearDb(){
        super.clearDb();
        this.setSheetId(null);
    }
}
