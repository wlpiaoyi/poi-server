package com.icss.poie.tools.excel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 9:50
 * {@code @version:}:       1.0
 */
@Data
public class Scope extends Point{


    /** 列数量 **/
    private int cs;

    /** 行数量 **/
    private int rs;

    public Scope(){

    }

    public Scope(Point start, Point end){
        this.setC(start.getC());
        this.setR(start.getR());
        this.setCs(end.getC() - start.getC() + 1);
        this.setRs(end.getR() - start.getR() + 1);
    }




    @JsonIgnore
    public boolean isEmpty(){
        return this.getC() == 0 && this.getR() == 0 && this.getCs() == 0 && this.getRs() == 0;
    }

    /**
     * <p><b>{@code @description:}</b>
     * 单元格是否在范围以内
     * </p>
     *
     * <p><b>@param</b> <b>point</b>
     * {@link Point}
     * </p>
     *
     * <p><b>{@code @date:}</b>2023/12/5 17:45</p>
     * <p><b>{@code @return:}</b>{@link boolean}</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    public boolean inScope(IPoint point){
        if(this.getC() == point.getC() && this.getR() == point.getR()){
            return true;
        }
        if(this.getC() > point.getC()){
            return false;
        }
        if(this.getR() > point.getR()){
            return false;
        }
        if(this.getC() + this.getCs() > point.getC()){
            return false;
        }
        if(this.getR() + this.getRs() > point.getR()){
            return false;
        }
        return true;
    }

    public String toString(){
        return super.toString() + ":" + this.rs + ":" + this.cs;
    }

    public Scope copy() {
        Scope scope = new Scope();
        scope.setC(this.getC());
        scope.setR(this.getR());
        scope.setCs(this.getCs()).setRs(this.getRs());
        return scope;
    }
}
