package com.icss.poie.tools.excel.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 9:48
 * {@code @version:}:       1.0
 */
@Data
@Accessors(chain = false)
public class Point implements IPoint{

    public Point(){}
    public Point(int c, int r){
        this.c = c;
        this.r = r;
    }

    /** 列 **/
    @Schema(description =  "列")
    @NotNull(message = "列不能为空")
    private int c = -1;

    /** 行 **/
    @Schema(description =  "行")
    @NotNull(message = "行不能为空")
    private int r = -1;

    public String toString(){
        return ":" + this.r + ":" + this.c;
    }

    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(!(object instanceof Point)){
            return false;
        }
        return this.c == ((Point) object).c && this.r == ((Point) object).r;
    }

    @Override
    public int hashCode() {
        return this.r + 100000 + this.c;
    }
}
