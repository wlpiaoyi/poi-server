package com.icss.poie.tools.excel.utils;

import com.icss.poie.biz.excel.domain.entity.CellData;
import com.icss.poie.framework.common.tools.MapUtils;
import com.icss.poie.framework.common.tools.PatternUtils;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.model.*;
import com.icss.poie.tools.excel.model.BorderStyle;
import com.icss.poie.tools.excel.model.Picture;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.wlpiaoyi.framework.utils.exception.BusinessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/30 12:49
 * {@code @version:}:       1.0
 */
public class DataToSheetUtils {

    public interface CellDataRun{
        void start(Sheet sheet, ISheetData sheetData);
        void doing(Cell cell, ICellData cellData, Map<String, StyleBase> styleBaseMap);
        void end(Sheet sheet, ISheetData sheetData);

    }

    public interface CellDataEnd{

    }


    @SneakyThrows
    public static Sheet parseSheet(Workbook workbook,
                                   ISheetData sheetData,
                                   CellDataRun cellDataRun){
        Sheet sheet = workbook.createSheet(sheetData.sheetName());
        DataStyle defaultDataStyle = new DataStyle();
        Map<Point, Map<String, Object>> cellDataMap = new HashMap<>();
        Map<Integer, Row> rowMap = new HashMap<>();
        cellDataRun.start(sheet, sheetData);
        if(sheetData.gridInfo() != null){
            List<DataStyle> dataStyles = sheetData.gridInfo().getDataStyles();
            List<BorderStyle> borderStyles = sheetData.gridInfo().getBorderStyles();
            if(ValueUtils.isNotBlank(dataStyles)){
                for(DataStyle item : dataStyles){
                    if(ValueUtils.isBlank(item.getScopes())){
                        continue;
                    }
                    item.setPoints(StyleBase.parseScopesToPoints(item.getScopes()));
                    for (Point point : item.getPoints()){
                        Map<String, Object> cdMap = cellDataMap.get(point);
                        if(cdMap == null){
                            cdMap = new HashMap<>();
                            cellDataMap.put(point, cdMap);
                        }
                        cdMap.put("dataStyle", item);
                        Row row = rowMap.get(point.getR());
                        if(row == null){
                            row = sheet.createRow(point.getR());
                            rowMap.put(point.getR(), row);
                        }
                        Cell cell = MapUtils.get(cdMap, "cell");
                        if(cell == null){
                            cell = row.createCell(point.getC());
                            cdMap.put("cell", cell);
                        }
                    }
                    item.getPoints().clear();
                }
            }
            if(ValueUtils.isNotBlank(borderStyles)){
                for(BorderStyle item : borderStyles){
                    if(ValueUtils.isBlank(item.getScopes())){
                        continue;
                    }
                    item.setPoints(StyleBase.parseScopesToPoints(item.getScopes()));
                    for (Point point : item.getPoints()){
                        Map<String, Object> cdMap = cellDataMap.get(point);
                        if(cdMap == null){
                            cdMap = new HashMap<>();
                            cellDataMap.put(point, cdMap);
                        }
                        cdMap.put("borderStyle", item);
                        Row row = rowMap.get(point.getR());
                        if(row == null){
                            row = sheet.createRow(point.getR());
                            rowMap.put(point.getR(), row);
                        }
                        Cell cell = MapUtils.get(cdMap, "cell");
                        if(cell == null){
                            cell = row.createCell(point.getC());
                            cdMap.put("cell", cell);
                        }
                    }
                    item.getPoints().clear();
                }
            }
        }
        if(ValueUtils.isNotBlank(sheetData.cellDatas())){
            for (ICellData cellData : sheetData.cellDatas()){
                if(cellData.v() == null){
                    continue;
                }
                if(ValueUtils.isBlank(cellData.v().getF()) && ValueUtils.isBlank(cellData.v().getV())){
                    continue;
                }
                Point point = new Point().setC(cellData.getC()).setR(cellData.getR());
                Map<String, Object> cdMap = cellDataMap.get(point);
                if(cdMap == null){
                    cdMap = new HashMap<>();
                    cellDataMap.put(point, cdMap);
                }
                cdMap.put("cellData", cellData);
            }
        }

        if(ValueUtils.isNotBlank(cellDataMap)){
            Map<String, StyleBase> styleBaseMap = new HashMap<>();
            for (Map.Entry<Point, Map<String, Object>> entry : cellDataMap.entrySet()){
                ICellData cellData = MapUtils.get(entry.getValue(), "cellData");
                Cell cell = MapUtils.get(entry.getValue(), "cell");;
                if(cellData != null){
                    Row row = rowMap.get(cellData.getR());
                    if(row == null){
                        row = sheet.createRow(cellData.getR());
                        rowMap.put(cellData.getR(), row);
                    }
                    if(cell == null){
                        cell = row.createCell(cellData.getC());
                        entry.getValue().put("cell", cell);
                    }
                    setCellData(cell, cellData);
                }
                DataStyle curDataStyle = MapUtils.get(entry.getValue(), "dataStyle");
                BorderStyle curBorderStyle = MapUtils.get(entry.getValue(), "borderStyle");;
                if(curDataStyle == null){
                    curDataStyle = defaultDataStyle;
                }
                styleBaseMap.put(StyleBase.KEY_CUR_DATA_STYLE_CACHE, curDataStyle);
                if(curBorderStyle != null){
                    styleBaseMap.put(StyleBase.KEY_CUR_BORDER_DATA_CACHE, curBorderStyle);
                }
                cellDataRun.doing(cell, cellData, styleBaseMap);
                styleBaseMap.clear();

            }
        }
        cellDataRun.end(sheet, sheetData);
        if(sheetData.gridInfo() != null){
            DataToSheetUtils.setRCValue(sheet, sheetData.gridInfo());
            if(sheetData.gridInfo().getFrozenWindow() != null){
                sheet.createFreezePane(sheetData.gridInfo().getFrozenWindow().getC(), sheetData.gridInfo().getFrozenWindow().getR());
            }
            if(ValueUtils.isNotBlank(sheetData.gridInfo().getPictures())){
                setPictures(sheet, sheetData.gridInfo().getPictures());
            }
        }
        return sheet;
    }

    /**
     * 图片
     * @param sheet
     * @param pictures
     */
    public static void setPictures(Sheet sheet, List<Picture> pictures){
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        for (Picture pictureValue : pictures){
            ClientAnchor anchor;
            if(sheet instanceof XSSFSheet){
                anchor = new XSSFClientAnchor(pictureValue.getDx1(),
                        pictureValue.getDy1(),
                        pictureValue.getDx2(),
                        pictureValue.getDy2(),
                        pictureValue.getCol1(),
                        pictureValue.getRow1(),
                        pictureValue.getCol2(),
                        pictureValue.getRow2());
            }else if(sheet instanceof HSSFSheet){
                anchor = new HSSFClientAnchor(pictureValue.getDx1(),
                        pictureValue.getDy1(),
                        pictureValue.getDx2(),
                        pictureValue.getDy2(),
                        (short) pictureValue.getCol1(),
                        pictureValue.getRow1(),
                        (short) pictureValue.getCol2(),
                        pictureValue.getRow2());

            }else throw new BusinessException("不支持的格式");
            drawing.createPicture(anchor, sheet.getWorkbook().addPicture(pictureValue.getData(), pictureValue.getPictureType()));
        }
    }

    /**
     * 单元格数据
     * @param cell
     * @param cellData
     * @return
     */
    public static boolean setCellData(Cell cell, ICellData cellData){
        // 判断数据的类型
        if(ValueUtils.isNotBlank(cellData.v().getF())){
            cell.setCellFormula(cellData.v().getF().substring(1));
        }
        if(ValueUtils.isNotBlank(cellData.v().getV())){
            String v = cellData.v().getV();
            if(ValueUtils.isNotBlank(cellData.v().getM())){
                v = cellData.v().getM();
            }
            switch (cellData.v().getType()){
                case 1:{
//                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue(new Double(v));
                }
                break;
                default:{
                    if(PatternUtils.isNumber(v) || PatternUtils.isFloat(v)){
//                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue(new Double(v));
                    }else{
//                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(v);
                    }
                }
                break;
            }
        }else{
            return false;
        }
        return true;
    }

    /**
     * 设置行高列宽
     * @param sheet
     * @param gridInfo
     */
    public static void setRCValue(Sheet sheet, IGridInfo gridInfo){
        if(ValueUtils.isNotBlank(gridInfo.getRowHeights())){
            int index = 0;
            for (Short value : gridInfo.getRowHeights()){
                if(sheet.getRow(index) != null){
                    sheet.getRow(index).setHeight(value);
                }
                index ++;
            }
        }
        if(ValueUtils.isNotBlank(gridInfo.getColumnWidths())){
            int index = 0;
            for (Short value : gridInfo.getColumnWidths()){
                sheet.setColumnWidth(index, value);
                index ++;
            }
        }
        if(ValueUtils.isNotBlank(gridInfo.getHiddenColumns())){
            for (Integer value : gridInfo.getHiddenColumns()){
                sheet.setColumnHidden(value, true);
            }
        }
        if(ValueUtils.isNotBlank(gridInfo.getHiddenRows())){
            for (Integer value : gridInfo.getHiddenRows()){
                Row row = sheet.getRow(value);
                row.setZeroHeight(true);
            }
        }
    }



    public static byte[] hexToBytes(String fc) {
        if(fc.startsWith("#")){
            try{
                byte[] bytes = ValueUtils.hexToBytes(fc.substring(1));
                return bytes;
            }catch (Exception e){
                throw e;
            }
        }else if(fc.startsWith("rgb(")){
            Integer[] ints = ValueUtils.toIntegerArray(fc.substring(4, fc.length() - 1).replaceAll(" ", ""));
            byte[] bytes = new byte[]{0,0,0};
            int j = 0;
            for (int i : ints){
                bytes[j] = (byte) i;
                j++;
            }
            return bytes;
        }else{
            return new byte[]{0,0,0};
        }
    }
}