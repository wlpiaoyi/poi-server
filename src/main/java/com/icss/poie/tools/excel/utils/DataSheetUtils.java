package com.icss.poie.tools.excel.utils;

import com.icss.poie.framework.common.tools.PatternUtils;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.tools.excel.model.*;
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
public class DataSheetUtils {

    public interface CellDataRun{
        void run(Cell cell, DataStyle dataStyle, ICellData cellData);

    }

    public interface CellDataEnd{
        void run(Sheet sheet, ISheetData sheetData);

    }


    @SneakyThrows
    public static void parseSheet(Workbook workbook, ISheetData sheetData, CellDataRun cellDataRun, CellDataEnd cellDataEnd){

        Sheet sheet = workbook.createSheet(sheetData.sheetName());
        Map<Integer, Row> rowMap = new HashMap<>();
        DataStyle defaultDataStyle = new DataStyle();
        if(ValueUtils.isNotBlank(sheetData.cellDatas())){
            for (ICellData cellData : sheetData.cellDatas()){
                if(cellData.v() == null){
                    continue;
                }
                if(ValueUtils.isBlank(cellData.v().getF()) && ValueUtils.isBlank(cellData.v().getV())){
                    continue;
                }
                Row row = rowMap.get(cellData.getR());
                if(row == null){
                    row = sheet.createRow(cellData.getR());
                    rowMap.put(cellData.getR(), row);
                }
                Cell cell = row.createCell(cellData.getC());
                DataStyle dataStyle = null;
                if(sheetData.gridInfo() != null && ValueUtils.isNotBlank(sheetData.gridInfo().getDataStyles())){
                    for(DataStyle item : sheetData.gridInfo().getDataStyles()){
                        if(item.getPoints() == null || ValueUtils.isBlank(item.getPoints())){
                            dataStyle = item;
                            break;
                        }
                        for (Point point : item.getPoints()){
                            if(point.getC() == cellData.getC() && point.getR() == cellData.getR()){
                                dataStyle = item;
                                break;
                            }
                        }
                        if(dataStyle != null){
                            break;
                        }
                    }
                }
                if(dataStyle == null){
                    dataStyle = defaultDataStyle;
                }
                setCellData(cell, cellData);
                cellDataRun.run(cell, dataStyle, cellData);

            }
        }

        if(sheetData.gridInfo() != null){
            cellDataEnd.run(sheet, sheetData);
            DataSheetUtils.setRCValue(sheet, sheetData.gridInfo());
            if(sheetData.gridInfo().getFrozenWindow() != null){
                sheet.createFreezePane(sheetData.gridInfo().getFrozenWindow().getC(), sheetData.gridInfo().getFrozenWindow().getR());
            }
            if(ValueUtils.isNotBlank(sheetData.gridInfo().getPictures())){
                setPictures(sheet, sheetData.gridInfo().getPictures());
            }
        }
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
