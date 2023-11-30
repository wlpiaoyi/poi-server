package com.filling.module.poi.excel.service;

import com.filling.module.poi.excel.domain.entity.ExcelData;
import com.filling.module.poi.excel.domain.vo.ExcelDataVo;
import com.filling.module.poi.service.IBaseMongoService;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/24 14:09
 * {@code @version:}:       1.0
 */
public interface IExcelDataService <M extends ExcelData> extends IBaseMongoService<M> {

    ExcelDataVo detail(ObjectId id);


    /**
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    ExcelDataVo getExcelDataByInputStream(InputStream inputStream, String type, ObjectId excelId) throws IOException;

    /**
     *
     * @param excelDataVo
     * @param outputStream
     * @throws IOException
     */
    void putOutputStreamByExcelData(ExcelDataVo excelDataVo, OutputStream outputStream) throws IOException;
    void putOutputStreamByExcelData(Collection<ExcelDataVo> excelDataVos, OutputStream outputStream) throws IOException;

}
