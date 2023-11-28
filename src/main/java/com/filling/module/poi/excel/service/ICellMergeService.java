package com.filling.module.poi.excel.service;

import com.filling.module.poi.excel.domain.entity.CellMerge;
import com.filling.module.poi.service.IBaseMongoService;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/24 12:59
 * {@code @version:}:       1.0
 */
public interface ICellMergeService <M extends CellMerge> extends IBaseMongoService<M> {
    /**
     * 根据SheetId获取CellMerges
     * @param sheetId
     * @return
     */
    List<M> queryBySheetId(ObjectId sheetId);

    /**
     * 批量更新单元合并
     * @param entities
     * @param sheetId
     * @return
     */
    boolean updateBatch(Collection<M> entities, ObjectId sheetId);
}
