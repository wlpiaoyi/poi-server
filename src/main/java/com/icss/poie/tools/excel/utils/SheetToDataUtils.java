package com.icss.poie.tools.excel.utils;

import com.icss.poie.framework.common.tools.MapUtils;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.biz.excel.domain.model.CellValue;
import com.icss.poie.tools.excel.model.*;
import com.icss.poie.tools.excel.model.BorderStyle;
import com.icss.poie.tools.excel.model.Comment;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.*;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 13:13
 * {@code @version:}:       1.0
 */
public class SheetToDataUtils {
    public interface CellDataRun{
        void start(ISheetDataEx sheetData, Sheet sheet);
        void doing(ICellData cellData, Cell cell, Map<String, StyleBase> styleBaseMap);
        void end(ISheetDataEx sheetData, Sheet sheet);

    }


    @SneakyThrows
    public static void parseToData(ISheetDataEx sheetData, Sheet sheet,
                                   Class<? extends ICellData> cdClazz,
                                   Class<? extends ICellValue> cvClass,
                                   CellDataRun cellDataRun){
        sheetData.setSheetName(sheet.getSheetName());
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<ICellData> cellDatas = new ArrayList<>();
        sheetData.setCellDatas(cellDatas);
        IGridInfo gridInfo = sheetData.newInstanceGridInfo();
        sheetData.putGridInfo(gridInfo);
        gridInfo.setDataStyles(new ArrayList<>());
        gridInfo.setBorderStyles(new ArrayList<>());
        gridInfo.setComments(new ArrayList<>());
        Map<String, StyleBase> styleBaseMap = new HashMap<>();
        cellDataRun.start(sheetData, sheet);
        //遍历行
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            int rowIndex = row.getRowNum();
            int rowFlag = 7;
            if(rowFlag == rowIndex){
                System.out.printf("");
            }
            //遍历单元格
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                ICellData cellData = cdClazz.newInstance();
                //设置单元格类容
                setCellData(cellData, cell, cvClass);
                cellDataRun.doing(cellData, cell, styleBaseMap);
                DataStyle curDataStyle = MapUtils.get(styleBaseMap, StyleBase.KEY_CUR_DATA_STYLE_CACHE);
                BorderStyle curBorderStyle = MapUtils.get(styleBaseMap, StyleBase.KEY_CUR_BORDER_DATA_CACHE);
                styleBaseMap.clear();
                Point point = new Point(cell.getColumnIndex(), rowIndex);
                if(curDataStyle != null){
                    StyleBase.mergeIn(gridInfo.getDataStyles(), curDataStyle, point);
                }
                if(curBorderStyle != null && !curBorderStyle.isEmpty()){
                    StyleBase.mergeIn(gridInfo.getBorderStyles(), curBorderStyle, point);
                }
                Comment comment = ExcelUtils.getCellComment(cell);
                if(comment != null){
                    gridInfo.getComments().add(comment);
                }
                cellDatas.add(cellData);
            }
        }
        if(ValueUtils.isNotBlank(gridInfo.getDataStyles())){
            for (DataStyle dataStyle : gridInfo.getDataStyles()){
                if(ValueUtils.isBlank(dataStyle.getPoints())){
                    continue;
                }
                dataStyle.setScopes(StyleBase.parsePointsToScopes(dataStyle.getPoints()));
                dataStyle.getPoints().clear();
            }
        }
        if(ValueUtils.isNotBlank(gridInfo.getBorderStyles())){
            for (BorderStyle borderStyle : gridInfo.getBorderStyles()){
                if(ValueUtils.isBlank(borderStyle.getPoints())){
                    continue;
                }
                borderStyle.setScopes(StyleBase.parsePointsToScopes(borderStyle.getPoints()));
                borderStyle.getPoints().clear();
            }
        }
        sheetData.gridInfo().setCellMerges(new ArrayList<>());
        setMergedRegions(sheetData.gridInfo().getCellMerges(), sheet);
        setRcValue(sheetData.gridInfo(), sheet);
        cellDataRun.end(sheetData, sheet);

        if(sheet.getPaneInformation() != null && sheet.getPaneInformation().isFreezePane()){
            Point point = new Point();
            point.setC(sheet.getPaneInformation().getVerticalSplitLeftColumn());
            point.setR(sheet.getPaneInformation().getHorizontalSplitPosition());
            sheetData.gridInfo().setFrozenWindow(point);
        }else{
            sheetData.gridInfo().setFrozenWindow(null);
        }
        List<com.icss.poie.tools.excel.model.Picture> pictures = new ArrayList<>();
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
    public static void setPicture(List<com.icss.poie.tools.excel.model.Picture> pictures, Sheet sheet){
        if(sheet.getDrawingPatriarch() == null){
            return;
        }
        Iterator<?> iterator = sheet.getDrawingPatriarch().iterator();
        while (iterator.hasNext()){
            Object object = iterator.next();
            if(object instanceof Picture){
                Picture picture = (Picture) object;
                com.icss.poie.tools.excel.model.Picture pictureValue = getPicture(picture);
                pictures.add(pictureValue);
            }
        }
    }

    @NotNull
    private static com.icss.poie.tools.excel.model.Picture getPicture(Picture picture) {
        PictureData pictureData = picture.getPictureData();
        com.icss.poie.tools.excel.model.Picture pictureValue = new com.icss.poie.tools.excel.model.Picture();
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
        List<Short> rowHeights = new ArrayList<>();
        List<Short> columnWidths = new ArrayList<>();
        List<Integer> hiddenColumns = new ArrayList<>();
        List<Integer> hiddenRows = new ArrayList<>();
        gridInfo.setRowHeights(rowHeights);
        gridInfo.setColumnWidths(columnWidths);
        gridInfo.setHiddenColumns(hiddenColumns);
        gridInfo.setHiddenRows(hiddenRows);
        int maxC = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int rowNum = row.getRowNum();
            while (rowNum > rowHeights.size()){
                rowHeights.add(null);
            }
            maxC = Math.max(maxC, row.getLastCellNum());
            short value = (short) Math.max(10, row.getHeight());
            rowHeights.add(value);
            if(row.getZeroHeight()){
                hiddenRows.add(row.getRowNum());
            }
        }
        for (int i = 0; i <= maxC; i ++){
            columnWidths.add((short) sheet.getColumnWidth(i));
            if (sheet.isColumnHidden(i)){
                gridInfo.getHiddenColumns().add(i);
            }
        }
    }



    private static boolean setCellData(ICellData cellData, Cell cell, Class<? extends ICellValue> cvClass) throws InstantiationException, IllegalAccessException {
        cellData.setR(cell.getRowIndex());
        cellData.setC(cell.getColumnIndex());
        cellData.setV(cvClass.newInstance());
        if(cellData.getV() == null){
            cellData.setV(new CellValue());
        }
        switch (cell.getCellType()) {
            case STRING:{
                cellData.getV().setType(0);
                cellData.getV().setV(cell.getStringCellValue());
            }
            break;
            case NUMERIC:{
                cellData.getV().setType(1);
                cellData.getV().setV(BigDecimal.valueOf(cell.getNumericCellValue()).toString());
            }
            break;
            case BOOLEAN:{
                cellData.getV().setType(1);
                cellData.getV().setV(Boolean.valueOf(cell.getStringCellValue()).toString());
            }
            break;
            case FORMULA:{
                cellData.getV().setF("=" + cell.getCellFormula());
            }
            break;
            case BLANK:
                cellData.getV().setV(null);
                break;
            default:
                cellData.getV().setV("非法字符");
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
