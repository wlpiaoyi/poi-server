package com.icss.poie.tools.excel.model;


/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/21 14:43
 * {@code @version:}:       1.0
 */
public interface ICellData<CV extends ICellValue> extends IPoint{


    CV getV();

    void setV(CV v);
}
