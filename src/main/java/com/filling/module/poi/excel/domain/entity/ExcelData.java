package com.filling.module.poi.excel.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.domain.entity.BaseMongoEntity;
import com.filling.module.poi.tools.utils.excel.IExcelData;
import com.filling.module.poi.tools.utils.excel.ISheetData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.query.Update;

import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * {@code @author:} 		wlpia:WLPIAOYI-DELL
 * {@code @description:} 	Excel基础内容 实体类
 * {@code @date:} 			2023-09-21 14:21:12
 * {@code @version:}: 		1.0
 */
@Data
@Accessors(chain=false)
@Schema(description = "Excel基础内容")
@EqualsAndHashCode(callSuper = true)
public class ExcelData extends BaseMongoEntity {

    public static String getCollectionName(){
        return "poi_excel_data";
    }
    public Update parseForUpdate(Update update){
        update = super.parseForUpdate(update);
        if(this.getName() != null){
            update.set("name", this.getName());
        }
        return update;
    }

    /** 名称 **/
    @Schema(description = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

}
