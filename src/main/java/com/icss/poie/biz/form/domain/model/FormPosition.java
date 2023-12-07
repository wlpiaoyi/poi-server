package com.icss.poie.biz.form.domain.model;

import com.icss.poie.biz.form.domain.vo.FormVo;
import lombok.Data;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/29 14:02
 * {@code @version:}:       1.0
 */
@Data
public class FormPosition {

    private Byte direction;

    /** 起始列 **/
    private int c;

    /** 列数量 **/
    private int cs;

    /** 起始行 **/
    private int r;

    /** 行数量 **/
    private int rs;

    public void checkRc(FormVo.FormHeadContext formHeadContext){
        if(this.getC() == -1){
            this.setC(formHeadContext.getFormPosition().getC());
        }
        if(this.getR() == -1){
            this.setR(formHeadContext.getFormPosition().getR());
        }
        if(this.getCs() == -1){
            this.setCs(formHeadContext.getFormPosition().getCs());
        }
        if(this.getRs() == -1){
            this.setRs(formHeadContext.getFormPosition().getRs());
        }
        if(this.getC() > formHeadContext.getFormPosition().getC()){
            this.setC(formHeadContext.getFormPosition().getC());
        }
        if(this.getR() > formHeadContext.getFormPosition().getR()){
            this.setR(formHeadContext.getFormPosition().getR());
        }
        if(this.getCs() < formHeadContext.getFormPosition().getCs()){
            this.setCs(formHeadContext.getFormPosition().getCs());
        }
        if(this.getRs() < formHeadContext.getFormPosition().getRs()){
            this.setRs(formHeadContext.getFormPosition().getRs());
        }
    }
}
