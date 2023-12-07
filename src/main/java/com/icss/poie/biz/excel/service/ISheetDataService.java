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
public interface ISheetDataService<M extends SheetData> extends IBaseMongoService<M> {


     SheetDataVo detail(ObjectId id);

     List<M> queryByExcelId(ObjectId excelId);

}
