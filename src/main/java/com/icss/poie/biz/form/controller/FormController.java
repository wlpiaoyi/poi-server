package com.icss.poie.biz.form.controller;

import com.icss.poie.biz.form.domain.vo.FormSheetVo;
import com.icss.poie.biz.form.service.IFormService;
import com.icss.poie.tools.response.R;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/28 17:54
 * {@code @version:}:       1.0
 */
@Slf4j
@Controller
@RestController
@RequestMapping("/form")
@Tag(name = "表单")
public class FormController {

    @Autowired
    private IFormService formService;


    /**
     * Excel基础内容 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @Operation(summary ="详情")
    public R<FormSheetVo.Detail> detail(@RequestParam String id) {
        ObjectId objId = new ObjectId(id);
        FormSheetVo.Detail detail = this.formService.detail(objId);
        return R.success(detail);
    }

    @SneakyThrows
    @PostMapping("/save")
    @ApiOperationSupport(order = 2)
    @Operation(summary ="新增")
    @ResponseBody
    public R save(@RequestBody FormSheetVo.FormExcelData body) {
        this.formService.insert(body);
        return R.success(body);
    }

    @SneakyThrows
    @PostMapping("/update")
    @ApiOperationSupport(order = 2)
    @Operation(summary ="修改")
    @ResponseBody
    public R update(@RequestBody FormSheetVo.FormExcelData body) {
        this.formService.update(body);
        return R.success(body);
    }


}
