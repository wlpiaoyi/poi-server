package com.icss.poie.biz.excel.service;

import com.icss.poie.biz.excel.domain.entity.GridInfo;
import com.icss.poie.biz.excel.domain.entity.SheetData;
import com.icss.poie.service.IBaseMongoService;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/12/15 16:22
 * {@code @version:}:       1.0
 */
public interface IGridInfoService <M extends GridInfo> extends IBaseMongoService<M> {

    /**
     * 根据SheetId获取GridInfo
     * @param sheetId
     * @param randomTag
     * @return
     */
    GridInfo findOneBySheetId(ObjectId sheetId, int randomTag);
    List<GridInfo> queryBySheets(List<SheetData> sheetDatas);


    UpdateResult update(M entity, int randomTag);

}
