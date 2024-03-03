package com.icss.poie.biz.excel.service;

import com.icss.poie.biz.excel.domain.entity.SheetData;
import com.icss.poie.biz.excel.domain.vo.SheetDataVo;
import com.icss.poie.service.IBaseMongoService;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/24 12:35
 * {@code @version:}:       1.0
 */
public interface ISheetDataService extends IBaseMongoService<SheetData> {


     SheetDataVo detail(ObjectId id);

     List<SheetData> queryByExcelId(ObjectId excelId);

}
