package com.icss.poie.tools.excel;

import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/12/15 14:59
 * {@code @version:}:       1.0
 */
@Data
public class Picture {

    private int dx1;
    private int dy1;
    private int dx2;
    private int dy2;
    private int col1;
    private int row1;
    private int col2;
    private int row2;

    private byte[] data;

    /**
     * @return the POI internal image type, 0 if unknown image type
     *
     * @see Workbook#PICTURE_TYPE_DIB
     * @see Workbook#PICTURE_TYPE_EMF
     * @see Workbook#PICTURE_TYPE_JPEG
     * @see Workbook#PICTURE_TYPE_PICT
     * @see Workbook#PICTURE_TYPE_PNG
     * @see Workbook#PICTURE_TYPE_WMF
     */
    int pictureType;

}
