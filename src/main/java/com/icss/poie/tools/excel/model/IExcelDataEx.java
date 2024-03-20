package com.icss.poie.tools.excel.model;


import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/22 10:58
 * {@code @version:}:       1.0
 */
public interface IExcelDataEx<SD extends ISheetData> extends IExcelData{

    List<SD> getSheetDatas();

    void setSheetDatas(List<SD> sheetDatas);

}
