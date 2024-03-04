package com.icss.poie.biz.excel.service;

import com.icss.poie.biz.excel.domain.entity.CellData;
import com.icss.poie.service.IBaseMongoService;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/24 10:44
 * {@code @version:}:       1.0
 */
public interface ICellDataService extends IBaseMongoService<CellData> {

    /**
     * 根据SheetId获取CellDatas
     * @param sheetId
     * @param randomTag
     * @return
     */
    List<CellData> queryBySheetId(ObjectId sheetId, int randomTag);

    Collection<CellData> insertBatch(List<CellData> entities, ObjectId sheetId, int randomTag);

    UpdateResult update(CellData entity, int randomTag);

    /**
     * 批量更新单元格
     * @param entities
     * @param sheetId
     * @param randomTag
     * @return
     */
    boolean updateBatch(List<CellData> entities, ObjectId sheetId, int randomTag);

}
