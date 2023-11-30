package com.filling.module.poi.controller;


import com.filling.module.poi.service.IFileService;
import com.filling.module.poi.tools.response.R;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/16 15:38
 * {@code @version:}:       1.0
 */
@Slf4j
@Controller
@RestController
@RequestMapping("/file")
@Tag(name = "文件上传")
public class FileController {

    @Autowired
    private IFileService fileService;

    @Value("${v2.fileurl}")
    private String filePath;

    @SneakyThrows
    @PostMapping("/upload")
    @ApiOperationSupport(order = 1)
    @Operation(summary = "上传单个文件 请求", description = "上传单个文件")
    @ResponseBody
    public R upload(@RequestParam("file") MultipartFile file) {
        Object fileMD5 = this.fileService.upload(file);
        return R.success(fileMD5 + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')));
    }

    @SneakyThrows
    @GetMapping("/loading/{fileName}")
    @ApiOperationSupport(order = 2)
    @Operation(summary = "上传单个文件 请求", description = "加载文件")
    @ResponseBody
    @PermitAll
    public void loading(@PathVariable String fileName,
                        @RequestParam(value = "type", defaultValue = "0") Integer type,
                        HttpServletResponse response) {
        this.fileService.loading(fileName, type, response);
    }
}
