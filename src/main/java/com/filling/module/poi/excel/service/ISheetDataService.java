package com.filling.module.poi.excel.service;

import com.filling.module.poi.excel.domain.entity.SheetData;
import com.filling.module.poi.excel.domain.vo.SheetDataVo;
import com.filling.module.poi.service.IBaseMongoService;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/24 12:35
 * {@code @version:}:       1.0
 */
public interface ISheetDataService<M extends SheetData> extends IBaseMongoService<M> {


     SheetDataVo detail(ObjectId id);

     List<M> queryByExcelId(ObjectId excelId, Class<M> entityClazz);

}
