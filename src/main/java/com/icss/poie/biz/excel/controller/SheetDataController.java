package com.icss.poie.biz.excel.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.icss.poie.biz.excel.domain.ro.SheetDataRo;
import com.icss.poie.biz.excel.service.ISheetDataService;
import com.icss.poie.tools.response.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p><b>{@code @description:}</b>  Sheet基础内容接口</p>
 * <p><b>{@code @date:}</b>         2024-03-04 11:21:02</p>
 * <p><b>{@code @author:}</b>       wlpiaoyi</p>
 * <p><b>{@code @version:}</b>      1.0</p>
 */
@Slf4j
@Controller
@RestController
@RequestMapping("/sheet_data")
@Tag(name = "Sheet基础内容接口")
public class SheetDataController {

    @Autowired
    private ISheetDataService sheetDataService;

    @SneakyThrows
    @PostMapping("/update")
    @ApiOperationSupport(order = 2)
    @Operation(summary ="修改Sheet")
    @ResponseBody
    public R<Boolean> update(@RequestBody SheetDataRo.Update body) {
        this.sheetDataService.update(body);
        return R.success(true);
    }

}
