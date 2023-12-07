package com.icss.poie.tools.excel;


/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/21 14:43
 * {@code @version:}:       1.0
 */
public interface ICellData {

    int getC();

    void setC(int c);

    int getR();

    void setR(int r);

    ICellValue v();

    void putV(ICellValue v);
}
