package com.icss.poie.biz.form.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/23 17:04
 * {@code @version:}:       1.0
 */
@Data
@Schema(description = "表单值")
@Builder
public class Fv<V> {
    private V v;

    private int r;

    private int c;
}
