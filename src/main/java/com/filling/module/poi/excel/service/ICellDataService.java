package com.filling.module.poi.excel.service;

import com.filling.module.poi.excel.domain.entity.CellData;
import com.filling.module.poi.service.IBaseMongoService;
import com.mongodb.bulk.BulkWriteResult;
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
public interface ICellDataService<M extends CellData> extends IBaseMongoService<M> {

    /**
     * 根据SheetId获取CellDatas
     * @param sheetId
     * @param randomTag
     * @return
     */
    List<M> queryBySheetId(ObjectId sheetId, int randomTag);


    UpdateResult update(M entity, int randomTag);

    /**
     * 批量更新单元格
     * @param entities
     * @param sheetId
     * @param randomTag
     * @return
     */
    boolean updateBatch(Collection<M> entities, ObjectId sheetId, int randomTag);

}
