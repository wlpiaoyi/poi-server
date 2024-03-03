package com.icss.poie.biz.excel.controller;

import cn.hutool.core.io.FileTypeUtil;
import com.icss.poie.biz.excel.domain.entity.ExcelData;
import com.icss.poie.biz.excel.domain.vo.ExcelDataVo;
import com.icss.poie.biz.excel.domain.vo.SheetDataVo;
import com.icss.poie.biz.excel.domain.wrapper.BaseWrapper;
import com.icss.poie.framework.common.exception.BusinessException;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.biz.excel.service.IExcelDataService;
import com.icss.poie.tools.response.R;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/24 14:28
 * {@code @version:}:       1.0
 */
@Slf4j
@Controller
@RestController
@RequestMapping("/excel_data")
@Tag(name = "Excel基础内容接口")
public class ExcelDataController {

    @Autowired
    private IExcelDataService excelDataService;

    /**
     * Excel基础内容 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @Operation(summary ="详情")
    public R<ExcelDataVo<SheetDataVo>> detail(@RequestParam(name = "id") String hexId) {
        ObjectId objId = new ObjectId(hexId);
        ExcelDataVo<SheetDataVo> excelData = this.excelDataService.detail(objId);
        return R.success(excelData);
    }

    @SneakyThrows
    @PostMapping("/save")
    @ApiOperationSupport(order = 2)
    @Operation(summary ="新增Excel")
    @ResponseBody
    public R<ExcelData> save(@RequestBody ExcelDataVo<SheetDataVo> body) {
        return R.success(this.excelDataService.insert(body));
    }

    @SneakyThrows
    @PostMapping("/uploadExcel")
    @ApiOperationSupport(order = 2)
    @Operation(summary ="上传Excel")
    @ResponseBody
    public R<String> uploadExcel(@RequestParam("file") MultipartFile file,
                         @RequestParam(name = "id", required = false) String excelId) {
        if(ValueUtils.isBlank(file.getOriginalFilename())){
            throw new BusinessException("源文件名称为空");
        }
        String[] args = file.getOriginalFilename().split("\\.");
        ExcelDataVo<SheetDataVo> excelData =  this.excelDataService.getExcelDataByInputStream(file.getInputStream(),
                args[args.length - 1],
                ValueUtils.isNotBlank(excelId) ? new ObjectId(excelId) : null);
        excelData.setName(args[0]);
        if(excelData.getId() != null){
            this.excelDataService.update(excelData);
        }else{
            this.excelDataService.insert(excelData);
        }
        return R.success(excelData.getId().toHexString());
    }

    @SneakyThrows
    @PostMapping("/update")
    @ApiOperationSupport(order = 2)
    @Operation(summary ="修改Excel")
    @ResponseBody
    public R<String> update(@RequestBody ExcelDataVo<SheetDataVo> body) {
        this.excelDataService.update(body);
        return R.success(body.getId().toHexString());
    }

    @GetMapping("/downloadExcel")
    @ApiOperationSupport(order = 3)
    @Operation(summary ="下载Excel")
    public void downloadExcel(@RequestParam(name = "id") String hexId,
                              @RequestParam(required = false, defaultValue = "xlsx") String fileType,
                              HttpServletResponse response) throws IOException {
        ObjectId objId = new ObjectId(hexId);
        ExcelDataVo<SheetDataVo> excelData = this.excelDataService.detail(objId);
        if(excelData == null){
            throw new BusinessException("没有找到Excel数据");
        }
        if(fileType.equals("xls")){

        }else{
            fileType = "xlsx";
        }
        response.setCharacterEncoding(Charsets.UTF_8.name());
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("content-Disposition","attachment;filename=" + URLEncoder.encode(excelData.getName(), response.getCharacterEncoding()) + "." + fileType);
        response.setStatus(200);
        this.excelDataService.putOutputStreamByExcelData(excelData, fileType, response.getOutputStream());
    }

    /**
     * Excel基础内容 删除
     */
    @GetMapping("/remove")
    @ApiOperationSupport(order = 4)
    @Operation(summary ="删除Excel")
    public R<Long> remove(@RequestParam String hexIds) {
        List<ObjectId> objIds = new ArrayList<>();
        for(String hexId : hexIds.split(",")){
            objIds.add(new ObjectId(hexId));
        }
        return R.success(excelDataService.removeBatch(objIds));
    }
}
