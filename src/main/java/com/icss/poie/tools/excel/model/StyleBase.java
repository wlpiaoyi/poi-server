package com.icss.poie.tools.excel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.icss.poie.framework.common.tools.ValueUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
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

    /** 点集合 **/
    @JsonIgnore
    @Transient
    private List<Point> points = new ArrayList<>();

    /** 作用范围 **/
    @Schema(description = "作用范围")
    private List<Scope> scopes;

//    @JsonIgnore
//    @Transient
//    protected String _cacheToString_;

//    public void cleanCacheData(){
//        this._cacheToString_ = null;
//    }

    public static List<Point> parseScopesToPoints(List<Scope> scopes){
        List<Point> points = new ArrayList<>();
        for (Scope scope : scopes){
            int rm = scope.getR() + scope.getRs();
            for (int r = scope.getR(); r < rm; r ++){
                int cm = scope.getC() + scope.getCs();
                for (int c = scope.getC(); c < cm; c ++){
                    points.add(new Point().setC(c).setR(r));
                }
            }
        }
        return points;
    }

    public static List<Scope> parsePointsToScopes(List<Point> points){
        points.sort((o1, o2) -> {
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
        });
        Point preP = null;
        Point startP = null;
        List<Scope> temp1Scopes = new ArrayList<>();
        for (Point point : points){
            try{
                if(startP == null){
                    startP = point;
                    continue;
                }
                if(preP.getR() == point.getR()){
                    if(preP.getC() + 1 == point.getC()){
                        continue;
                    }
                }
                temp1Scopes.add(new Scope(startP, preP));
                startP = point;
            }finally {
                preP = point;
            }
        }
        if(startP != null){
            temp1Scopes.add(new Scope(startP, preP));
        }

        List<Scope> temp2Scopes = new ArrayList<>();
        Scope startS = null;
        for (Scope scope : temp1Scopes){
            if(startS == null){
                startS = scope.copy();
                continue;
            }
            if(startS.getC() == scope.getC()){
                if( startS.getR() + startS.getRs() == scope.getR()){
                    startS.setRs(startS.getRs() + scope.getRs());
                    continue;
                }
            }
            temp2Scopes.add(startS);
            startS = scope;
        }
        if(startS != null){
            temp2Scopes.add(startS);
        }
        return temp2Scopes;
    }

    public static <T extends StyleBase> void mergeIn(List<T> styleBases, T styleBase, Point point){
        int index = styleBases.indexOf(styleBase);
        if(index < 0){
            styleBase.getPoints().clear();
            styleBase.getPoints().add(point);
            styleBases.add(styleBase);
          return;
        }
        T orgSB = styleBases.get(index);
        if(orgSB.getPoints().contains(point)){
            return;
        }
        orgSB.getPoints().add(point);
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
