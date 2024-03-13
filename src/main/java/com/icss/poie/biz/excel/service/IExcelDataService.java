package com.icss.poie.biz.excel.service;

import com.icss.poie.biz.excel.domain.entity.ExcelData;
import com.icss.poie.biz.excel.domain.vo.ExcelDataVo;
import com.icss.poie.biz.excel.domain.vo.SheetDataVo;
import com.icss.poie.service.IBaseMongoService;
import org.apache.poi.ss.usermodel.Workbook;
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
     * <p><b>{@code @description:}</b>
     * 解析公式
     * </p>
     *
     * <p><b>@param</b> <b>workbook</b>
     * {@link Workbook}
     * </p>
     *
     * <p><b>@param</b> <b>excelDataVo</b>
     * {@link ExcelDataVo<SheetDataVo>}
     * </p>
     *
     * <p><b>{@code @date:}</b>2024/3/5 13:57</p>
     * <p><b>{@code @author:}</b>wlpia</p>
     */
    void formulaEvaluatorAll(Workbook workbook, ExcelDataVo<SheetDataVo> excelDataVo);
    /**
     *
     * @param excelDataVo
     * @param outputStream
     * @throws IOException
     */
    void putOutputStreamByExcelData(ExcelDataVo<SheetDataVo> excelDataVo, String fileType, int isFormulaEvaluator,OutputStream outputStream) throws IOException;

}
