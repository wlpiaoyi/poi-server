package com.filling.module.poi.tools.utils.excel;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/21 14:37
 * {@code @version:}:       1.0
 */
public interface ISheetData {

    String sheetName();
    void putSheetName(String name);

    List<ICellData> cellDatas();
    void putCellDatas(List<ICellData> cellDatas);

    List<ICellMerge> cellMerges();

    void putCellMerges(List<ICellMerge> cellMerges);

    IGridInfo gridInfo();

    void putGridInfo(IGridInfo gridInfo);

}
