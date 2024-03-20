package com.icss.poie.tools.excel.model;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/21 14:37
 * {@code @version:}:       1.0
 */
public interface ISheetDataEx<CD extends ICellData> extends ISheetData{

    /** 单元格数据 **/
    List<CD> getCellDatas();
    void setCellDatas(List<CD> cellDatas);

    /** 缓存 **/
    ICacheMap cacheMap();

    /** 网格数据 **/
    IGridInfo gridInfo();
    void putGridInfo(IGridInfo gridInfo);
    IGridInfo newInstanceGridInfo();



}
