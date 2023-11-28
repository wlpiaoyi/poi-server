package com.filling.module.poi.tools.utils.excel;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/21 14:41
 * {@code @version:}:       1.0
 */
public interface ICellMerge {

    int getFirstRow();

    void setFirstRow(int firstRow);

    int getLastRow();

    void setLastRow(int lastRow);

    int getFirstCol();

    void setFirstCol(int firstCol);

    int getLastCol();

    void setLastCol(int lastCol);
}
