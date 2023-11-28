package com.filling.module.poi.tools.utils.excel;


import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/22 10:58
 * {@code @version:}:       1.0
 */
public interface IExcelData {


    String getName();

    void setName(String name);

    List<ISheetData> sheetDatas();

    void putSheetDatas(List<ISheetData> sheetDatas);
}
