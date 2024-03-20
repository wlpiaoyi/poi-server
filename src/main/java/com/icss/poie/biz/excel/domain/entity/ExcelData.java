package com.icss.poie.biz.excel.domain.entity;

import com.icss.poie.domain.entity.BaseMongoEntity;
import com.icss.poie.tools.excel.model.IExcelData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Update;

import javax.validation.constraints.NotBlank;


/**
 * {@code @author:} 		wlpia:WLPIAOYI-DELL
 * {@code @description:} 	Excel基础内容 实体类
 * {@code @date:} 			2023-09-21 14:21:12
 * {@code @version:}: 		1.0
 */
@Data
@Accessors(chain=false)
@Schema(description = "Excel基础内容")
@Document(collection = "poi_excel_data")
@EqualsAndHashCode(callSuper = true)
public class ExcelData extends BaseMongoEntity implements IExcelData {

//    public static String collectionName(){
//        return "poi_excel_data";
//    }
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
