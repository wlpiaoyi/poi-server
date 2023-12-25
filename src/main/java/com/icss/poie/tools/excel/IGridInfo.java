package com.icss.poie.tools.excel;

import java.util.List;

/**
 * 网格信息
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/12/15 16:06
 * {@code @version:}:       1.0
 */
public interface IGridInfo /** 行高 **/{

    /** 行高 **/
    List<Short> getRowHeights();
    void setRowHeights(List<Short> rowHeights);

    /** 列宽 **/
    List<Short> getColumnWidths();
    void setColumnWidths(List<Short> columnWidths);

    /** 冻结窗格 **/
    Point getFrozenWindow();
    void setFrozenWindow(Point frozenWindow);

    /** 隐藏行 **/
    List<Integer> getHiddenRows();
    void setHiddenRows(List<Integer> hiddenRows);

    /** 隐藏列 **/
    List<Integer> getHiddenColumns();
    void setHiddenColumns(List<Integer> hiddenColumns);

    /** 合并单元格 **/
    List<Scope> getCellMerges();
    void setCellMerges(List<Scope> cellMerges);

    /** 单元格样式 **/
    List<DataStyle> getDataStyles();
    void setDataStyles(List<DataStyle> dataStyles);

    /** 单元格数据验证 **/
    List<DataValidation> getDataValidations();
    void setDataValidations(List<DataValidation> dataValidations);

    /** 图片数据 **/
    List<Picture> getPictures();
    void setPictures(List<Picture> pictures);
}
