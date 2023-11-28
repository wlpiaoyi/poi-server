package com.filling.module.poi.service.impl;


import com.filling.framework.common.exception.BusinessException;
import com.filling.framework.common.tools.ValueUtils;
import com.filling.module.poi.service.IFileService;
import com.filling.module.poi.tools.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/16 15:40
 * {@code @version:}:       1.0
 */
@Slf4j
@Primary
@Service
public class FileServiceImpl implements IFileService {

    @Value("${v2.filetemp}")
    private String tempPath;

    @Value("${v2.fileurl}")
    private String filePath;

    public String upload(MultipartFile file) throws IOException {
        long size = file.getSize();
        if(size > FileUtils.getMaxFileSize()) throw new BusinessException("文件过大");

        return FileUtils.moveFileMD5(file, this.tempPath, this.filePath);
    }

    private static Map<String, String> contentTypeMap = new HashMap(){{
        put("jpg", "image/jpeg");
        put("jpeg", "image/jpeg");
        put("png", "image/png");
        put("pdf", "application/pdf");
        put("docx", "application/msword");
        put("doc", "application/msword");
        put("default", "application/octet-stream");
    }};

    public void loading(String fileName, int type, HttpServletResponse response) throws IOException {
        try{
            String fn = fileName;
            String ft = null;
            if(fileName.contains(".")){
                fn = fileName.substring(0, fileName.lastIndexOf('.'));
                ft = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase(Locale.ROOT);
            }
            String ogPath = this.filePath + "/" + FileUtils.toMd5Path(fn) + "/" + ".data";
            String contentType = contentTypeMap.get(ft);
            if(ValueUtils.isBlank(contentType)){
                contentType = contentTypeMap.get("default");
            }
            response.setContentType(contentType);
            switch (type){
                case 1:{
                    response.setCharacterEncoding(Charsets.UTF_8.name());
                    fileName = URLEncoder.encode(fileName, Charsets.UTF_8.name());
                    response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                }
                break;
            }

            response.setStatus(200);
            ServletOutputStream sos = response.getOutputStream();
            FileInputStream fis = new FileInputStream(ogPath);
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = fis.read(data, 0, data.length)) != -1) {
                sos.write(data, 0, nRead);;
                sos.flush();
            }
        }catch (Exception e){
            throw new BusinessException("文件读取异常");
//            ResponseUtils.writeResponseData("file io erro", contentTypeMap.get("default"), 500, response);
        }
    }



}
