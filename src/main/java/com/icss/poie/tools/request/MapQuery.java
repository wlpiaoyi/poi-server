package com.icss.poie.tools.request;

import lombok.Data;

import java.util.Map;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/10/1 10:14
 * {@code @version:}:       1.0
 */
@Data
public class MapQuery extends Query {
    private Map condition;
}
