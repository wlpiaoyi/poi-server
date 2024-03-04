package com.icss.poie.biz.excel.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.icss.poie.domain.entity.BaseMongoEntity;
import com.icss.poie.framework.common.tools.MapUtils;
import com.icss.poie.tools.excel.model.*;
import com.icss.poie.tools.excel.utils.DataToSheetUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    网格信息
 * {@code @date:}           2023/11/23 14:02
 * {@code @version:}:       1.0
 */
@Data
@Schema(description =  "网格信息")
public class GridInfo extends BaseMongoEntity implements IGridInfo, ICacheMap {

    public static final String COLLECTION_NAME = "poi_grid_info";

    /** sheetId **/
    @Schema(description =  "sheetId")
    @NotNull(message = "sheetId不能为空")
    @Indexed
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId sheetId;

    @Schema(description = "行高")
    private List<Short> rowHeights;

    @Schema(description = "列宽")
    private List<Short> columnWidths;

    @Schema(description = "冻结窗格")
    private Point frozenWindow;

    @Schema(description = "隐藏行")
    private List<Integer> hiddenRows;

    @Schema(description = "隐藏列")
    private List<Integer> hiddenColumns;

    @Schema(description = "合并单元格")
    private List<Scope> cellMerges;

    @Schema(description = "单元格数据验证")
    private List<DataValidation> dataValidations;

//    @Transient
    @Schema(description = "单元格样式")
    private List<DataStyle> dataStyles;

//    @Transient
    @Schema(description = "边框样式")
    private List<BorderStyle> borderStyles;

//    @Transient
    @Schema(description = "图片数据")
    private List<Picture> pictures;

//    @Transient
    @Schema(description = "批注")
    private List<Comment> comments;

    @JsonIgnore
    @Transient
    private final Map cacheMap = new HashMap<>();


    public void clearCacheMap(){
        this.cacheMap.clear();
    }
    public XSSFColor getCacheXSSFColor(String rgb){
        Map<String, XSSFColor> cacheColorMap = MapUtils.getMap(this.cacheMap, "cacheColor");
        if(cacheColorMap == null){
            cacheColorMap = new HashMap<>();
            this.cacheMap.put("cacheColor", cacheColorMap);
        }
        XSSFColor color = cacheColorMap.get(rgb);
        if(color == null){
            color = new XSSFColor();
            color.setRGB(DataToSheetUtils.hexToBytes(rgb));
            cacheColorMap.put(rgb, color);
        }
        return color;
    }

}
