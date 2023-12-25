package com.icss.poie.tools.excel.utils;

import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.biz.excel.domain.model.CellValue;
import com.icss.poie.tools.excel.*;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.*;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 13:13
 * {@code @version:}:       1.0
 */
public class SheetDataUtils {
    public interface CellDataRun{
        void run(Cell cell, DataStyle curDataStyle);

    }
    public interface CellDataEnd{
        void run(Sheet sheet, ISheetData sheetData);
    }


    @SneakyThrows
    public static void parseData(ISheetData sheetData, Sheet sheet,
                                 Class<? extends ICellData> cdClazz,
                                 Class<? extends ICellValue> cvClass,
                                 CellDataRun cellDataRun,
                                 CellDataEnd cellDataEnd){
        sheetData.putSheetName(sheet.getSheetName());
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<ICellData> cellDatas = new ArrayList<>();
        sheetData.putCellDatas(cellDatas);
        //TODO 数据验证 sheet.getDataValidations();
        Map<String, DataStyle> dataStyleMap = new HashMap<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                ICellData cellData = cdClazz.newInstance();
                setCellData(cellData, cell, cvClass);
                DataStyle curDataStyle = new DataStyle();
                cellDataRun.run(cell, curDataStyle);
                DataStyle dataStyle = dataStyleMap.get(curDataStyle.toString());
                if(dataStyle == null){
                    dataStyle = curDataStyle;
                    dataStyleMap.put(dataStyle.toString(), dataStyle);
                    dataStyle.setPoints(new ArrayList<>());
                }
                Point point = new Point();
                point.setR(cellData.getR());
                point.setC(cellData.getC());
                dataStyle.getPoints().add(point);
                cellDatas.add(cellData);
            }
        }
        sheetData.putGridInfo(sheetData.newInstanceGridInfo());
        sheetData.gridInfo().setCellMerges(new ArrayList<>());
        if(ValueUtils.isNotBlank(dataStyleMap)){
            sheetData.gridInfo().setDataStyles(new ArrayList(){{ addAll(dataStyleMap.values());}});
        }
        setMergedRegions(sheetData.gridInfo().getCellMerges(), sheet);
        setRcValue(sheetData.gridInfo(), sheet);
        cellDataEnd.run(sheet, sheetData);

        if(sheet.getPaneInformation() != null && sheet.getPaneInformation().isFreezePane()){
            Point point = new Point();
            point.setC(sheet.getPaneInformation().getVerticalSplitLeftColumn());
            point.setR(sheet.getPaneInformation().getHorizontalSplitTopRow());
            sheetData.gridInfo().setFrozenWindow(point);
        }else{
            sheetData.gridInfo().setFrozenWindow(null);
        }
        List<com.icss.poie.tools.excel.Picture> pictures = new ArrayList<>();
        setPicture(pictures, sheet);
        if(ValueUtils.isNotBlank(pictures)){
            sheetData.gridInfo().setPictures(pictures);
        }
    }

    /**
     * 图片
     * @param pictures
     * @param sheet
     */
    public static void setPicture(List<com.icss.poie.tools.excel.Picture> pictures, Sheet sheet){
        if(sheet.getDrawingPatriarch() == null){
            return;
        }
        Iterator iterator = sheet.getDrawingPatriarch().iterator();
        while (iterator.hasNext()){
            Object object = iterator.next();
            if(object instanceof Picture){
                Picture picture = (Picture) object;
                com.icss.poie.tools.excel.Picture pictureValue = getPicture(picture);
                pictures.add(pictureValue);
            }
        }
    }

    @NotNull
    private static com.icss.poie.tools.excel.Picture getPicture(Picture picture) {
        PictureData pictureData = picture.getPictureData();
        com.icss.poie.tools.excel.Picture pictureValue = new com.icss.poie.tools.excel.Picture();
        pictureValue.setData(pictureData.getData());
        pictureValue.setPictureType(pictureData.getPictureType());
        ClientAnchor clientAnchor = picture.getClientAnchor();
        pictureValue.setCol1(clientAnchor.getCol1());
        pictureValue.setCol2(clientAnchor.getCol2());
        pictureValue.setRow1(clientAnchor.getRow1());
        pictureValue.setRow2(clientAnchor.getRow2());
        pictureValue.setDx1(clientAnchor.getDx1());
        pictureValue.setDx2(clientAnchor.getDx2());
        pictureValue.setDy1(clientAnchor.getDy1());
        pictureValue.setDy2(clientAnchor.getDy2());
        return pictureValue;
    }

    /**
     * 行列高宽、隐藏、只读
     * @param gridInfo
     * @param sheet
     */
    public static void setRcValue(IGridInfo gridInfo, Sheet sheet){
        Iterator<Row> rowIterator = sheet.rowIterator();
        gridInfo.setRowHeights(new ArrayList<>());
        gridInfo.setColumnWidths(new ArrayList<>());
        gridInfo.setHiddenColumns(new ArrayList<>());
        gridInfo.setHiddenRows(new ArrayList<>());
        int maxC = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            maxC = Math.max(maxC, row.getLastCellNum());
            short value = (short) Math.max(10, row.getHeight());
            gridInfo.getRowHeights().add(value);
            if(row.getZeroHeight()){
                gridInfo.getHiddenRows().add(row.getRowNum());
            }
        }
        for (int i = 0; i <= maxC; i ++){
            gridInfo.getColumnWidths().add((short) sheet.getColumnWidth(i));
            if (sheet.isColumnHidden(i)){
                gridInfo.getHiddenColumns().add(i);
            }
        }
    }



    public static boolean setCellData(ICellData cellData, Cell cell, Class<? extends ICellValue> cvClass) throws InstantiationException, IllegalAccessException {
        cellData.setR(cell.getRowIndex());
        cellData.setC(cell.getColumnIndex());
        cellData.putV(cvClass.newInstance());
        if(cellData.v() == null){
            cellData.putV(new CellValue());
        }
        switch (cell.getCellType()) {
            case NUMERIC:{
                cellData.v().setType(1);
                cellData.v().setV(new BigDecimal(cell.getNumericCellValue()).toString());
            }
            break;
            case STRING:{
                cellData.v().setType(0);
                cellData.v().setV(cell.getStringCellValue());
            }
            break;
            case BOOLEAN:{
                cellData.v().setType(1);
                cellData.v().setV(new Boolean(cell.getStringCellValue()).toString());
            }
            break;
            case FORMULA:{
                cellData.v().setF("=" + cell.getCellFormula());
            }
            break;
            case BLANK:
                cellData.v().setV(null);
                break;
            default:
                cellData.v().setV("非法字符");
                break;
        }
        return true;
    }

    /**
     * 合并单元格
     * @param cellMerges
     * @param sheet
     */
    public static void setMergedRegions(List<Scope> cellMerges, Sheet sheet) {

        int count = sheet.getNumMergedRegions();
        for (int i = 0; i < count; i++) {
            CellRangeAddress rangeAddress = sheet.getMergedRegion(i);
            Scope cellMerge = new Scope();
            cellMerge.setC(rangeAddress.getFirstColumn());
            cellMerge.setR(rangeAddress.getFirstRow());
            cellMerge.setCs(rangeAddress.getLastColumn() - cellMerge.getC());
            cellMerge.setRs(rangeAddress.getLastRow() - cellMerge.getR());
            cellMerges.add(cellMerge);
        }
    }


}
