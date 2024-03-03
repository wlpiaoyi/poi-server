package com.icss.poie.tools.excel.model;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/21 14:37
 * {@code @version:}:       1.0
 */
public interface ISheetData {

    /** Sheet名称 **/
    String sheetName();
    void putSheetName(String name);

    /** 单元格数据 **/
    List<ICellData> cellDatas();
    void putCellDatas(List<ICellData> cellDatas);

    /** 缓存 **/
    ICacheMap cacheMap();

    /** 网格数据 **/
    IGridInfo gridInfo();
    void putGridInfo(IGridInfo gridInfo);
    IGridInfo newInstanceGridInfo();

//    List<ICellMerge> cellMerges();
//
//    void putCellMerges(List<ICellMerge> cellMerges);


}
