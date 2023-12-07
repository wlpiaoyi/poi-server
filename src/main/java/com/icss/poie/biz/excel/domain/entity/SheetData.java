package com.icss.poie.biz.excel.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.icss.poie.domain.entity.BaseMongoEntity;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.GridInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Update;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;


/**
 * {@code @author:} 		wlpia:WLPIAOYI-DELL
 * {@code @description:} 	Sheet基础内容 实体类
 * {@code @date:} 			2023-09-21 14:21:12
 * {@code @version:}: 		1.0
 */
@Data
@Accessors(chain=false)
@Schema(description = "Sheet基础内容")
@Document(collection = "poi_sheet_data")
@EqualsAndHashCode(callSuper = true)
public class SheetData extends BaseMongoEntity {


//    public static String collectionName(){
//        return "poi_sheet_data";
//    }
    public Update parseForUpdate(Update update){
        update = super.parseForUpdate(update);
        if(this.getExcelId() != null){
            update.set("excelId", this.getExcelId());
        }
        if(this.getRandomTag() != null){
            update.set("randomTag", this.getRandomTag());
        }
        if(this.getGridInfo() != null){
            update.set("gridInfo", this.getGridInfo());
        }
        if(this.getThumbImage() != null){
            update.set("thumbImage", this.getThumbImage());
        }
        if(this.getSheetName() != null){
            update.set("sheetName", this.getSheetName());
        }
//        if(this.getDataVerification() != null){
//            update.set("dataVerification", this.getDataVerification());
//        }
        return update;
    }

    /** excelId **/
    @Schema(description =  "excelId")
    @NotNull(message = "excelId不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    @Indexed
    private ObjectId excelId;

    /** 随机标识, CellData使用 **/
    @Schema(description =  "随机标识, CellData使用 ")
    @NotNull(message = "随机标识, CellData使用")
    private Byte randomTag;

    /** 网格信息 **/
    @Schema(description =  "网格信息")
    private GridInfo gridInfo;

    /** 缩略图 **/
    @Schema(description =  "缩略图")
    private String thumbImage;

    /** 名称 **/
    @Schema(description = "名称")
    private String sheetName;
//
//    /** 数据验证 **/
//    @Schema(description =  "数据验证")
//    private String dataVerification;

    public static String cellDataCollectionName(int randomTag){
        if(randomTag < 10){
            return CellData.COLLECTION_NAME + "_0" + randomTag;
        }else{
            return CellData.COLLECTION_NAME + "_" + randomTag;
        }
    }

    public static final int MAX_RANDOM_TAG = 12;

    /**
     * 获取并自动生成随机数
     * @return
     */
    public Byte createRandomTag() {
        if(this.getId() == null){
            return null;
        }
        BigInteger idValue = new BigInteger(ValueUtils.hexToBytes(this.getId().toHexString()));
        this.randomTag = (byte) (idValue.divideAndRemainder(new BigInteger(String.valueOf(MAX_RANDOM_TAG)))[1].byteValue() + 1);
        return this.randomTag;
    }


}
