package com.filling.module.poi.excel.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.filling.framework.common.exception.BusinessException;
import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.excel.domain.entity.ExcelData;
import com.filling.module.poi.excel.domain.entity.SheetData;
import com.filling.module.poi.excel.domain.model.CellValue;
import com.filling.module.poi.excel.domain.vo.CellDataVo;
import com.filling.module.poi.excel.domain.vo.ExcelDataVo;
import com.filling.module.poi.excel.domain.vo.SheetDataVo;
import com.filling.module.poi.excel.domain.wrapper.BaseWrapper;
import com.filling.module.poi.excel.service.IExcelDataService;
import com.filling.module.poi.excel.service.ISheetDataService;
import com.filling.module.poi.service.impl.BaseMongoServiceImpl;
import com.filling.module.poi.tools.excel.utils.xls.HSSFDataUtils;
import com.filling.module.poi.tools.excel.utils.xlsx.DataXSSFUtils;
//import com.filling.module.poi.tools.excel.utils.HSSFDataUtils;
import com.filling.module.poi.tools.excel.utils.xlsx.XSSFDataUtils;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/24 14:10
 * {@code @version:}:       1.0
 */
@Slf4j
@Service
@Primary
public class ExcelDataServiceImpl extends BaseMongoServiceImpl<ExcelData> implements IExcelDataService<ExcelData> {

    @Autowired
    private ISheetDataService sheetDataService;

    @Override
    public ExcelDataVo detail(ObjectId id) {
        ExcelDataVo excelDataVo = this.baseTemplate.findOne(new Query(Criteria.where("_id").is(id)),
                ExcelDataVo.class, ExcelData.collectionName());
        if(excelDataVo == null){
            return null;
        }
        List<SheetData> sheetDatas = this.sheetDataService.queryByExcelId(excelDataVo.getId(), SheetData.class);
        excelDataVo.setSheetDatas(new ArrayList<>());
        if(ValueUtils.isNotBlank(sheetDatas)){
            for (SheetData sheetData : sheetDatas){
                SheetDataVo sheetDataVo = this.sheetDataService.detail(sheetData.getId());
                if(sheetDataVo == null){
                    continue;
                }
                excelDataVo.getSheetDatas().add(sheetDataVo);
            }
        }
        return excelDataVo;
    }

    @Override
    public ExcelDataVo getExcelDataByInputStream(InputStream inputStream, String type, ObjectId excelId) throws IOException {

        ExcelDataVo excelData = new ExcelDataVo();
        if(excelId != null){
            ExcelData db = this.findOne(excelId, ExcelData.collectionName());
            if(db == null){
                throw new BusinessException("没有找到对应的数据");
            }
            excelData.setId(db.getId());
            excelData.setName(db.getName());
        }
        excelData.setSheetDatas(new ArrayList<>());
        Map<String, SheetData> sdMap = new HashMap<>();
        if(excelData.getId() != null){
            List<SheetData> sdDbs = this.sheetDataService.queryByExcelId(excelId, SheetData.class);
            if(ValueUtils.isNotBlank(sdDbs)){
                sdMap = sdDbs.stream().collect(Collectors.toMap(
                        SheetData::getName, Function.identity()
                ));
            }
        }
        if("xlsx".equals(type.toLowerCase())){
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            try{
                Iterator<Sheet> iterator = workbook.iterator();
                while (iterator.hasNext()){
                    XSSFSheet sheet = (XSSFSheet)iterator.next();
                    SheetDataVo sheetData = new SheetDataVo();
                    XSSFDataUtils.parseData(sheetData, sheet, CellDataVo.class, CellValue.class);
                    SheetData sdDb = sdMap.get(sheetData.getName());
                    if(sdDb != null){
                        sheetData.setId(sdDb.getId());
                        sheetData.setCreateTime(sdDb.getCreateTime());
                        sheetData.setRandomTag(sdDb.getRandomTag());
                    }
                    excelData.getSheetDatas().add(sheetData);
                }
                return excelData;
            }finally {
                workbook.close();
            }

        }else if("xls".equals(type.toLowerCase())){
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            try{
                excelData.setSheetDatas(new ArrayList<>());
                Iterator<Sheet> iterator = workbook.iterator();
                while (iterator.hasNext()){
                    HSSFSheet sheet = (HSSFSheet)iterator.next();
                    SheetDataVo sheetData = new SheetDataVo();
                    HSSFDataUtils.parseData(sheetData, sheet, CellDataVo.class, CellValue.class);
                    SheetData sdDb = sdMap.get(sheetData.getName());
                    if(sdDb != null){
                        sheetData.setId(sdDb.getId());
                        sheetData.setCreateTime(sdDb.getCreateTime());
                        sheetData.setRandomTag(sdDb.getRandomTag());
                    }
                    excelData.getSheetDatas().add(sheetData);
                }
                return excelData;
            }finally {
                workbook.close();
            }
        }
        throw new BusinessException("不支持的格式");
    }

    @Override
    public void putOutputStreamByExcelData(ExcelDataVo excelDataVo, OutputStream outputStream) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        for (SheetDataVo sheetDataVo : ((ExcelDataVo<SheetDataVo>) excelDataVo).getSheetDatas()){
            DataXSSFUtils.parseSheet(workbook, sheetDataVo);
        }
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        workbook.close();
    }


    public static File xssfWorkbookToFile(XSSFWorkbook wb, String name) {
        File file = new File(name);
        try {
            OutputStream os = new FileOutputStream(file);
            wb.write(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public void putOutputStreamByExcelData(Collection<ExcelDataVo> excelDataVos, OutputStream outputStream) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        int index = 0;
        for (ExcelDataVo<SheetDataVo> excelDataVo : excelDataVos){
            index ++;
            XSSFWorkbook workbook = null;
            try {
                workbook = new XSSFWorkbook();
                for (SheetDataVo sheetData : excelDataVo.getSheetDatas()){
                    DataXSSFUtils.parseSheet(workbook, sheetData);
                }
                File file = xssfWorkbookToFile(workbook, excelDataVo.getName() + RandomUtil.randomString(8) + ".xlsx");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                FileInputStream fileInputStream = new FileInputStream(file);
                workbook.write(fileOutputStream);
                String organizationName = excelDataVo.getName() + "_" + index;
                ZipEntry zipEntry = new ZipEntry(organizationName + ".xlsx");
                zipOutputStream.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024 * 1024];
                int length = 0;
                while ((length = fileInputStream.read(bytes)) != -1) {
                    zipOutputStream.write(bytes, 0, length);
                }
                zipOutputStream.closeEntry();
                fileOutputStream.close();
                fileInputStream.close();
                file.delete();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
                if(workbook != null){
                    workbook.close();
                }
            }
        }
        zipOutputStream.flush();
        zipOutputStream.close();
    }

    @Override
    public ExcelData insert(ExcelData entity, String collectionName) {
        if(entity.getId() == null){
            entity.setId(ObjectId.get());
        }
        if(entity instanceof ExcelDataVo){
            ExcelDataVo<SheetDataVo> entityVo = (ExcelDataVo) entity;
            if(ValueUtils.isNotBlank(entityVo.getSheetDatas())){
                for (SheetDataVo sheetDataVo : entityVo.getSheetDatas()){
                    sheetDataVo.setExcelId(entity.getId());
                    this.sheetDataService.insert(sheetDataVo, SheetData.collectionName());
                }
            }
        }

        ExcelData iEntity = BaseWrapper.parseOne(entity, ExcelData.class);
        return super.insert(iEntity, collectionName);
    }

    @Override
    public Collection<ExcelData> insertBatch(List<ExcelData> entities, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public UpdateResult update(ExcelData entity, String collectionName) {
        if(entity.getId() == null){
            throw new BusinessException("Id不能为空");
        }
        ExcelData db = this.findOne(entity.getId(), ExcelData.collectionName());
        if(db == null){
            throw new BusinessException("没有找到数据");
        }
        if(entity instanceof ExcelDataVo){
            ExcelDataVo<SheetDataVo> entityVo = (ExcelDataVo) entity;
            if(ValueUtils.isNotBlank(entityVo.getSheetDatas())){
                for (SheetDataVo sheetDataVo : entityVo.getSheetDatas()){
                    sheetDataVo.setExcelId(entity.getId());
                    this.sheetDataService.update(sheetDataVo, SheetData.collectionName());
                }
            }
        }

        ExcelData iEntity = BaseWrapper.parseOne(entity, ExcelData.class);
        return super.update(iEntity, collectionName);
    }

    @Override
    public BulkWriteResult updateBatch(List<ExcelData> entities, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public long removeBatch(List<ObjectId> ids, String collectionName) {
        Criteria criteria = Criteria.where("excelId").in(ids);
        List<SheetData> sheetDatas = this.sheetDataService.queryList(criteria, SheetData.collectionName());
        List<ObjectId> sheetDataIds = sheetDatas.stream().map(SheetData::getId).collect(Collectors.toList());
        this.sheetDataService.removeBatch(sheetDataIds, SheetData.collectionName());
        return super.removeBatch(ids, collectionName);
    }
}
