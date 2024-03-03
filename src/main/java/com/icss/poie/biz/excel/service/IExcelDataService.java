package com.icss.poie.biz.excel.service;

import com.icss.poie.biz.excel.domain.entity.ExcelData;
import com.icss.poie.biz.excel.domain.vo.ExcelDataVo;
import com.icss.poie.biz.excel.domain.vo.SheetDataVo;
import com.icss.poie.service.IBaseMongoService;
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
public interface IExcelDataService extends IBaseMongoService<ExcelData> {

    ExcelDataVo<SheetDataVo> detail(ObjectId id);


    /**
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    ExcelDataVo<SheetDataVo> getExcelDataByInputStream(InputStream inputStream, String type, ObjectId excelId) throws IOException;

    /**
     *
     * @param excelDataVo
     * @param outputStream
     * @throws IOException
     */
    void putOutputStreamByExcelData(ExcelDataVo<SheetDataVo> excelDataVo, String fileType, OutputStream outputStream) throws IOException;
    void putOutputStreamByExcelData(Collection<ExcelDataVo<SheetDataVo>> excelDataVos, OutputStream outputStream) throws IOException;

}
