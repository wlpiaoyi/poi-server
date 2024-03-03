package com.icss.poie.tools.excel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.icss.poie.framework.common.tools.ValueUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.List;

/**
 * <p><b>{@code @description:}</b>  样式基础类</p>
 * <p><b>{@code @date:}</b>         2024-02-28 10:14:29</p>
 * <p><b>{@code @author:}</b>       wlpiaoyi</p>
 * <p><b>{@code @version:}</b>      1.0</p>
 */
@Data
public class StyleBase{

    public static final String KEY_CUR_DATA_STYLE_CACHE = "curDataStyle";
    public static final String KEY_CUR_BORDER_DATA_CACHE = "curBorderData";

    /** 作用范围 **/
    @Schema(description = "作用范围")
    private final List<Point> points = new ArrayList<>();

    @JsonIgnore
    @Transient
    protected String _cacheToString_;

    public void cleanCacheData(){
        this._cacheToString_ = null;
    }

    public boolean mergeIn(StyleBase styleBase, Point point){
        if(!this.equals(styleBase)){
            return false;
        }
        if(this.points.contains(point)){
            return true;
        }
        this.points.add(point);
        return true;
    }

    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(!(object instanceof StyleBase)){
            return false;
        }
        return this.toString().equals(object.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
