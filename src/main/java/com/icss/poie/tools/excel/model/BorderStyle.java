package com.icss.poie.tools.excel.model;

import lombok.Data;

/**
 * <p><b>{@code @description:}</b>  边框样式</p>
 * <p><b>{@code @date:}</b>         2024-02-27 18:32:46</p>
 * <p><b>{@code @author:}</b>       wlpiaoyi</p>
 * <p><b>{@code @version:}</b>      1.0</p>
 */
@Data
public class BorderStyle extends StyleBase{


    private Border top;
    private Border right;
    private Border bottom;
    private Border left;

    public boolean isEmpty(){
        if(this.top != null && !this.top.isEmpty()){
            return true;
        }
        if(this.right != null && !this.right.isEmpty()){
            return true;
        }
        if(this.bottom != null && !this.bottom.isEmpty()){
            return true;
        }
        if(this.left != null && !this.left.isEmpty()){
            return true;
        }
        return false;
    }

    @Data
    public static class Border{
        /**
         * {@link org.apache.poi.ss.usermodel.BorderStyle}
         */
        private Short styleCode;

        private String color;

        public boolean isEmpty(){
            if(this.styleCode != null){
                return true;
            }
            if(this.color != null && this.color.length() > 0){
                return true;
            }
            return false;
        }
        @Override
        public String toString() {
            String text = styleCode + ":";
            if(this.color != null){
                text += this.color;
            }else{
                text += "null";
            }
            return text;
        }
    }

    @Override
    public String toString() {
        if(this.top == null && this.left == null && this.bottom == null && this.right == null){
            return "";
        }
        String text = "";
        Border b = this.top;
        if(b != null){
            text += "top:" + b.toString();
        }else{
            text += "top:null";
        }
        b = this.right;
        if(b != null){
            text += "right:" + b.toString();
        }else{
            text += "right:null";
        }
        b = this.bottom;
        if(b != null){
            text += "bottom:" + b.toString();
        }else{
            text += "bottom:null";
        }
        b = this.left;
        if(b != null){
            text += "left:" + b.toString();
        }else{
            text += "left:null";
        }
        return text;
    }

    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(!(object instanceof BorderStyle)){
            return false;
        }
        return super.equals(object);
    }
}
