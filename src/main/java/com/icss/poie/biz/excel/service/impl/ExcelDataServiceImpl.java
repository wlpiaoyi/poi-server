package com.icss.poie.biz.excel.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.icss.poie.biz.excel.domain.entity.SheetData;
import com.icss.poie.biz.excel.domain.vo.ExcelDataVo;
import com.icss.poie.biz.excel.domain.vo.SheetDataVo;
import com.icss.poie.biz.excel.service.ISheetDataService;
import com.icss.poie.framework.common.exception.BusinessException;
import com.icss.poie.framework.common.tools.MongoTransactional;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.biz.excel.domain.entity.ExcelData;
import com.icss.poie.biz.excel.domain.model.CellValue;
import com.icss.poie.biz.excel.domain.vo.CellDataVo;
import com.icss.poie.biz.excel.domain.wrapper.BaseWrapper;
import com.icss.poie.biz.excel.service.IExcelDataService;
import com.icss.poie.service.impl.BaseMongoServiceImpl;
import com.icss.poie.tools.excel.utils.xls.DataHSSFUtils;
import com.icss.poie.tools.excel.utils.xls.HSSFDataUtils;
import com.icss.poie.tools.excel.utils.xlsx.DataXSSFUtils;
//import com.filling.module.poi.tools.excel.utils.HSSFDataUtils;
import com.icss.poie.tools.excel.utils.xlsx.XSSFDataUtils;
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
        long pointTime = System.currentTimeMillis();
        log.info("excel start detail dataId[{}]", id.toHexString());
        ExcelDataVo excelDataVo = this.baseTemplate.findOne(new Query(Criteria.where("_id").is(id)),
                ExcelDataVo.class);
        if(excelDataVo == null){
            return null;
        }
        List<SheetData> sheetDatas = this.sheetDataService.queryByExcelId(excelDataVo.getId());
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
        log.info("excel end detail dataId[{}], duriTime:{}ms", id.toHexString(), System.currentTimeMillis() - pointTime);
        return excelDataVo;
    }

    @Override
    public ExcelDataVo getExcelDataByInputStream(InputStream inputStream, String type, ObjectId excelId) throws IOException {

        ExcelDataVo excelData = new ExcelDataVo();
        if(excelId != null){
            ExcelData db = this.findOne(excelId);
            if(db == null){
                throw new BusinessException("没有找到对应的数据");
            }
            excelData.setId(db.getId());
            excelData.setName(db.getName());
        }
        excelData.setSheetDatas(new ArrayList<>());
        Map<String, SheetData> sdMap = new HashMap<>();
        if(excelData.getId() != null){
            List<SheetData> sdDbs = this.sheetDataService.queryByExcelId(excelId);
            if(ValueUtils.isNotBlank(sdDbs)){
                sdMap = sdDbs.stream().collect(Collectors.toMap(
                        SheetData::getSheetName, Function.identity()
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
                    SheetData sdDb = sdMap.get(sheetData.getSheetName());
                    if(sdDb != null){
                        sheetData.setId(sdDb.getId());
                        sheetData.setCreateTime(sdDb.getCreateTime());
                        sheetData.setCellRandomTag(sdDb.getCellRandomTag());
                        sheetData.setGiRandomTag(sdDb.getGiRandomTag());
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
                    SheetData sdDb = sdMap.get(sheetData.getSheetName());
                    if(sdDb != null){
                        sheetData.setId(sdDb.getId());
                        sheetData.setCreateTime(sdDb.getCreateTime());
                        sheetData.setCellRandomTag(sdDb.getCellRandomTag());
                        sheetData.setGiRandomTag(sdDb.getGiRandomTag());
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
    public void putOutputStreamByExcelData(ExcelDataVo excelDataVo, String fileType, OutputStream outputStream) throws IOException {
        if(fileType.equals("xls")){
            HSSFWorkbook workbook = new HSSFWorkbook();
            for (SheetDataVo sheetDataVo : ((ExcelDataVo<SheetDataVo>) excelDataVo).getSheetDatas()){
                DataHSSFUtils.parseSheet(workbook, sheetDataVo);
            }
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workbook.close();
        }else{
            XSSFWorkbook workbook = new XSSFWorkbook();
            for (SheetDataVo sheetDataVo : ((ExcelDataVo<SheetDataVo>) excelDataVo).getSheetDatas()){
                DataXSSFUtils.parseSheet(workbook, sheetDataVo);
            }
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workbook.close();
        }
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
    @MongoTransactional
    public ExcelData insert(ExcelData entity) {
        long pointTime = System.currentTimeMillis();
        if(entity.getId() == null){
            entity.setId(ObjectId.get());
        }
        log.info("excel start insert dataId[{}]", entity.getId().toHexString());
        if(entity instanceof ExcelDataVo){
            ExcelDataVo<SheetDataVo> entityVo = (ExcelDataVo) entity;
            if(ValueUtils.isNotBlank(entityVo.getSheetDatas())){
                for (SheetDataVo sheetDataVo : entityVo.getSheetDatas()){
                    sheetDataVo.setExcelId(entity.getId());
                }
                this.sheetDataService.insertBatch(entityVo.getSheetDatas());
            }
        }
        ExcelData iEntity = BaseWrapper.parseOne(entity, ExcelData.class);
        ExcelData result = super.insert(iEntity);
        log.info("excel end insert dataId[{}], duriTime:{}ms", entity.getId().toHexString(), System.currentTimeMillis() - pointTime);
        return result;
    }
    @Override
    public ExcelData insert(ExcelData entity, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public Collection<ExcelData> insertBatch(List<ExcelData> entities) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public Collection<ExcelData> insertBatch(List<ExcelData> entities, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @MongoTransactional
    @Override
    public UpdateResult update(ExcelData entity) {
        long pointTime = System.currentTimeMillis();
        if(entity.getId() == null){
            throw new BusinessException("Id不能为空");
        }
        ExcelData db = this.findOne(entity.getId());
        if(db == null){
            throw new BusinessException("没有找到数据");
        }
        log.info("excel start update dataId[{}]", entity.getId().toHexString());
        if(entity instanceof ExcelDataVo){
            ExcelDataVo<SheetDataVo> entityVo = (ExcelDataVo) entity;
            if(ValueUtils.isNotBlank(entityVo.getSheetDatas())){
                List<SheetDataVo> saves = new ArrayList<>();
                List<SheetDataVo> updates = new ArrayList<>();
                for (SheetDataVo sheetDataVo : entityVo.getSheetDatas()){
                    sheetDataVo.setExcelId(entity.getId());
                    if(sheetDataVo.getId() != null){
                        updates.add(sheetDataVo);
                    }else{
                        saves.add(sheetDataVo);
                    }
                }
                if(ValueUtils.isNotBlank(saves)){
                    this.sheetDataService.insertBatch(saves);
                }
                if(ValueUtils.isNotBlank(updates)){
                    this.sheetDataService.updateBatch(updates);
                }
            }
        }

        ExcelData iEntity = BaseWrapper.parseOne(entity, ExcelData.class);
        UpdateResult result = super.update(iEntity);
        log.info("excel end update dataId[{}], duriTime:{}ms", entity.getId().toHexString(), System.currentTimeMillis() - pointTime);
        return result;
    }
    @Override
    public UpdateResult update(ExcelData entity, String collectionName) {
        throw new BusinessException("不支持的方法");
    }


    @Override
    public BulkWriteResult updateBatch(List<ExcelData> entities) {
        throw new BusinessException("不支持的方法");
    }
    @Override
    public BulkWriteResult updateBatch(List<ExcelData> entities, String collectionName) {
        throw new BusinessException("不支持的方法");
    }


    @Override
    public long removeBatch(List<ObjectId> ids, String collectionName) {
        throw new BusinessException("不支持的方法");
    }

    @Override
    public long removeBatch(List<ObjectId> ids) {
        Criteria criteria = Criteria.where("excelId").in(ids);
        List<SheetData> sheetDatas = this.sheetDataService.queryList(criteria);
        List<ObjectId> sheetDataIds = sheetDatas.stream().map(SheetData::getId).collect(Collectors.toList());
        this.sheetDataService.removeBatch(sheetDataIds);
        return super.removeBatch(ids);
    }
}
