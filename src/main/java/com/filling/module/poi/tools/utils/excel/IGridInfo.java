package com.filling.module.poi.tools.utils.excel;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/23 14:23
 * {@code @version:}:       1.0
 */
public interface IGridInfo {


    List<Short> getRowHeights();

    void setRowHeights(List<Short> rowHeights);

    List<Short> getColumnWidths();

    void setColumnWidths(List<Short> columnWidths);

    List<Integer> getFrozenRows();

    void setFrozenRows(List<Integer> frozenRows);
    List<Integer> getFrozenColumns();

    void setFrozenColumns(List<Integer> frozenColumns);

    List<Integer> getHiddenRows();

    void setHiddenRows(List<Integer> hiddenRows);

    List<Integer> getHiddenColumns();

    void setHiddenColumns(List<Integer> hiddenColumns);
}
