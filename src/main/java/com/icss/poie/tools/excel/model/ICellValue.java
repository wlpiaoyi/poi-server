package com.icss.poie.tools.excel.model;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/21 14:38
 * {@code @version:}:       1.0
 */
public interface ICellValue {

    /**
     * 是否为空
     * @return
     */
    boolean isEmpty();
    /** 0:String 1:数字**/
    int getType();

    void setType(int type);

    String getV();
    void setV(String v);

    /** 展示值 **/
    String getM();
    void setM(String m);

    /** 公式 **/
    String getF();
    void setF(String f);
}
