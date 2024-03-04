package com.icss.poie.tools.excel.model;

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
    IGridInfo setRowHeights(List<Short> rowHeights);

    /** 列宽 **/
    List<Short> getColumnWidths();
    IGridInfo setColumnWidths(List<Short> columnWidths);

    /** 冻结窗格 **/
    Point getFrozenWindow();
    IGridInfo setFrozenWindow(Point frozenWindow);

    /** 隐藏行 **/
    List<Integer> getHiddenRows();
    IGridInfo setHiddenRows(List<Integer> hiddenRows);

    /** 隐藏列 **/
    List<Integer> getHiddenColumns();
    IGridInfo setHiddenColumns(List<Integer> hiddenColumns);

    /** 合并单元格 **/
    List<Scope> getCellMerges();
    IGridInfo setCellMerges(List<Scope> cellMerges);

    /** 单元格样式 **/
    List<DataStyle> getDataStyles();
    IGridInfo setDataStyles(List<DataStyle> dataStyles);

    /** 单元格数据验证 **/
    List<DataValidation> getDataValidations();
    IGridInfo setDataValidations(List<DataValidation> dataValidations);

    /** 图片数据 **/
    List<Picture> getPictures();
    IGridInfo setPictures(List<Picture> pictures);

    /** 边框样式 **/
    List<BorderStyle> getBorderStyles();
    IGridInfo setBorderStyles(List<BorderStyle> borderStyles);
}
