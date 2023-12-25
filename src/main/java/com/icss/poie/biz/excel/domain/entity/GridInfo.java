package com.icss.poie.biz.excel.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.icss.poie.domain.entity.BaseMongoEntity;
import com.icss.poie.tools.excel.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    网格信息
 * {@code @date:}           2023/11/23 14:02
 * {@code @version:}:       1.0
 */
@Data
@Schema(description =  "网格信息")
public class GridInfo extends BaseMongoEntity implements IGridInfo{

    public static final String COLLECTION_NAME = "poi_grid_info";

    /** sheetId **/
    @Schema(description =  "sheetId")
    @NotNull(message = "sheetId不能为空")
    @Indexed
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId sheetId;

    @Schema(description =  "行高")
    private List<Short> rowHeights;

    @Schema(description =  "列宽")
    private List<Short> columnWidths;

    @Schema(description =  "冻结窗格")
    private Point frozenWindow;

    @Schema(description =  "隐藏行")
    private List<Integer> hiddenRows;

    @Schema(description =  "隐藏列")
    private List<Integer> hiddenColumns;

    @Schema(description =  "合并单元格")
    private List<Scope> cellMerges;

    @Schema(description =  "单元格样式")
    private List<DataStyle> dataStyles;

    @Schema(description =  "单元格数据验证")
    private List<DataValidation> dataValidations;

    @Schema(description =  "图片数据")
    private List<Picture> pictures;
}
