package com.icss.poie.biz.excel.domain.ro;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.icss.poie.biz.excel.domain.entity.CellData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * <p><b>{@code @description:}</b>  </p>
 * <p><b>{@code @date:}</b>         2024-03-04 11:25:29</p>
 * <p><b>{@code @author:}</b>       wlpiaoyi</p>
 * <p><b>{@code @version:}</b>      1.0</p>
 */
public class SheetDataRo {

    @Data
    @Accessors(chain=false)
    @Schema(description = "Sheet基础内容")
    public static class Update{

        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(description = "主键id")
        private ObjectId id;

        /** 缩略图 **/
        @Schema(description =  "缩略图")
        private String thumbImage;

        /** 名称 **/
        @Schema(description = "名称")
        private String sheetName;

        private List<CellData> updateCells;
        private List<CellData> addCells;
        private List<ObjectId> removeCellIds;

    }

}
