package com.icss.poie.biz.excel.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.icss.poie.biz.excel.domain.model.CellValue;
import com.icss.poie.domain.entity.BaseMongoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.query.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * {@code @author:} 		wlpia:WLPIAOYI-DELL
 * {@code @description:} 	 实体类
 * {@code @date:} 			2023-09-21 14:21:12
 * {@code @version:}: 		1.0
 */
@Data
@Accessors(chain = false)
@Schema(description = "")
@EqualsAndHashCode(callSuper = true)
public class CellData extends BaseMongoEntity{

    public static final String COLLECTION_NAME = "poi_cell_data";

    public static String mapKey(int c, int r){
        return c + "_" + r;
    }

    public String mapKey(){
        if(this.c < 0){
            return super.mapKey();
        }
        if(this.r < 0){
            return super.mapKey();
        }
        return CellData.mapKey(this.c, this.r);
    }

    public void synKey(int offc, int offr){
        this.c = offc + this.c;
        this.r = offr + this.r;
    }
    public String hexId(){
        if(this.getId() == null){
            return null;
        }
        return this.getId().toHexString();
    }


    /** 列 **/
    @Schema(description =  "列")
    @NotNull(message = "列不能为空")
    private int c = -1;

    /** 行 **/
    @Schema(description =  "行")
    @NotNull(message = "行不能为空")
    private int r = -1;

    /** 单元格内容 **/
    @Schema(description =  "单元格内容")
    @NotBlank(message = "单元格内容不能为空")
    private CellValue v;

    /** sheetId **/
    @Schema(description =  "sheetId")
    @NotNull(message = "sheetId不能为空")
    @Indexed
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId sheetId;

    @Schema(description =  "类型  1:隐藏 2:只读")
    @NotNull(message = "类型  1:隐藏 2:只读不能为空")
    private Byte dataType;

    @Schema(description =  "拓展数据")
    private String cellBlob;


    @SneakyThrows
    public <T extends CellData> T copyOnlyMainData(Class<T> clazz){
        T obj = clazz.newInstance();
        obj.setR(this.getR());
        obj.setC(this.getC());
        obj.setId(this.getId());
        return obj;
    }

    public Update parseForUpdate(Update update){
        update = super.parseForUpdate(update);
        update.set("c", this.getC());
        update.set("r", this.getR());
        if(this.getV() != null){
            update.set("v", this.getV());
        }
        if(this.getSheetId() != null){
            update.set("sheetId", this.getSheetId());
        }
        if(this.getDataType() != null){
            update.set("dataType", this.getDataType());
        }
        if(this.getCellBlob() != null){
            update.set("cellBlob", this.getCellBlob());
        }
        return update;
    }

    public void clearDb(){
        super.clearDb();
        this.setSheetId(null);

    }

    /**
     * 纵向排序
     * @param cellDatas
     */
    public static <E extends CellData>  void sortCellDataV(List<E> cellDatas){
        cellDatas.sort((o1, o2) -> sortCellDataV(o1, o2));

    }
    public static int sortCellDataV(CellData o1, CellData o2){
        if(o1.getR() < o2.getR()){
            return -1;
        }
        if(o1.getR() > o2.getR()){
            return 1;
        }
        if(o1.getC() < o2.getC()){
            return -1;
        }
        if(o1.getC() > o2.getC()){
            return 1;
        }
        return 0;
    }

    /**
     * 横向排序
     * @param cellDatas
     */
    public static <E extends CellData> void sortCellDataH(List<E> cellDatas){
        cellDatas.sort((o1, o2) -> sortCellDataH(o1, o2));
    }
    public static int sortCellDataH(CellData o1, CellData o2){
        if(o1.getC() < o2.getC()){
            return -1;
        }
        if(o1.getC() > o2.getC()){
            return 1;
        }
        if(o1.getR() < o2.getR()){
            return -1;
        }
        if(o1.getR() > o2.getR()){
            return 1;
        }
        return 0;
    }

}
