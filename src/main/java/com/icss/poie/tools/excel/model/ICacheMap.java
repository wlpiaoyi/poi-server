package com.icss.poie.tools.excel.model;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFColor;

public interface ICacheMap {


    /**
     * <p><b>{@code @description:}</b>
     * 清理缓存
     * </p>
     *
     * <p><b>@param</b> <b></b>
     * {@link }
     * </p>
     *
     * <p><b>{@code @date:}</b>2024/2/27 19:18</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    void clearCacheMap();

    /**
     * <p><b>{@code @description:}</b>
     * 获取颜色
     * </p>
     *
     * <p><b>@param</b> <b>rgb</b>
     * {@link String}
     * </p>
     *
     * <p><b>{@code @date:}</b>2024/2/27 19:18</p>
     * <p><b>{@code @return:}</b>{@link XSSFColor}</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    XSSFColor getCacheXSSFColor(String rgb);


    /**
     * <p><b>{@code @description:}</b>
     * 获取颜色
     * </p>
     *
     * <p><b>@param</b> <b>rgb</b>
     * {@link String}
     * </p>
     *
     * <p><b>{@code @date:}</b>2024/3/7 11:00</p>
     * <p><b>{@code @return:}</b>{@link HSSFColor}</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    HSSFColor getCacheHSSFColor(String rgb);
}
