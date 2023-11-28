package com.filling.module.poi.tools.request;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/16 12:39
 * {@code @version:}:       1.0
 */

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "查询条件"
)
public class Query {
    @Schema(description = "当前页")
    private Integer current;
    @Schema(description = "每页的数量")
    private Integer size;
    private String ascs;
    private String descs;


    public Integer getCurrent() {
        return this.current;
    }

    public Integer getSize() {
        return this.size;
    }

    public String getAscs() {
        return this.ascs;
    }

    public String getDescs() {
        return this.descs;
    }

    public Query setCurrent(final Integer current) {
        this.current = current;
        return this;
    }

    public Query setSize(final Integer size) {
        this.size = size;
        return this;
    }

    public Query setAscs(final String ascs) {
        this.ascs = ascs;
        return this;
    }

    public Query setDescs(final String descs) {
        this.descs = descs;
        return this;
    }

}