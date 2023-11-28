package com.filling.module.poi.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/16 16:00
 * {@code @version:}:       1.0
 */
public interface IFileService {

    String upload(MultipartFile file) throws IOException;

    void loading(String fileName, int type, HttpServletResponse response) throws IOException;

}
