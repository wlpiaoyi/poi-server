package com.icss.poie.biz.excel.domain.wrapper;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/28 13:18
 * {@code @version:}:       1.0
 */
public class BaseWrapper {
    @SneakyThrows
    public static <T> T parseOne(Object orgObj, Class<T> resClazz) {
        T resObj = resClazz.newInstance();
        BeanUtils.copyProperties(orgObj, resObj);
        return resObj;
    }

    public static <T> List<T> parseList(List orgObjs, Class<T> resClazz) {
        List<T> resList = new ArrayList<>(orgObjs.size());
        for (Object orgObj : orgObjs){
            resList.add(parseOne(orgObj, resClazz));
        }
        return resList;
    }

    public static <T> Set<T> parseList(Set orgObjs, Class<T> resClazz) {
        Set<T> resList = new HashSet<>(orgObjs.size());
        for (Object orgObj : orgObjs){
            resList.add(parseOne(orgObj, resClazz));
        }
        return resList;
    }
}
