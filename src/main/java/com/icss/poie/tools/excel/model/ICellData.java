package com.icss.poie.tools.excel.model;


/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/21 14:43
 * {@code @version:}:       1.0
 */
public interface ICellData extends IPoint{


    ICellValue v();

    void putV(ICellValue v);
}
