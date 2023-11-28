package com.filling.module.poi.tools.utils;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.wlpiaoyi.framework.utils.data.DataUtils;
import org.wlpiaoyi.framework.utils.exception.BusinessException;
import org.wlpiaoyi.framework.utils.StringUtils;

import java.io.IOException;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/16 15:43
 * {@code @version:}:       1.0
 */
public class FileUtils extends DataUtils{


    @Setter @Getter
    private static int maxFileSize = 1024 * 1024 * 50;

    /**
     * Store file base on file fingerprints
     * @return
     */
    public static String moveFileMD5(MultipartFile file, String tempPath, String savePath) throws IOException {
        if(file.isEmpty()){
            throw new BusinessException("File.EmptyError");
        }
        String fileName = StringUtils.getUUID32();
        FileUtils.makeDir(tempPath);

        java.io.File tempFile = new java.io.File(tempPath + "/" + fileName);
        file.transferTo(tempFile);

        String md5Path = FileUtils.moveFileMD5(tempFile, savePath);
        return md5Path;

    }

    public static String toMd5Path(String md5Value){
        String oPath = "";
        for (int i = 0; i < md5Value.length(); i+=2) {
            String fn = md5Value.substring(i, i+2);
            oPath +=  fn + "/";
        }
        return oPath;
    }


    /**
     * Store file base on file fingerprints
     * @param orgFile
     * @return
     */
    public static String moveFileMD5(java.io.File orgFile, String savePath) {
        try{
            final String fileName = DataUtils.MD5(orgFile);
            String oPath = FileUtils.toMd5Path(fileName);
            java.io.File md5File = new java.io.File(savePath + "/" + oPath);
            if (!md5File.exists()) {// 判断目录是否存在
                md5File.mkdirs();
            }
            md5File = new java.io.File(savePath + "/" + oPath + "/.data");
            if(md5File.exists()) return fileName;
            if(!orgFile.renameTo(md5File)){
                throw new BusinessException("File.MoveError");
            }
            return fileName;
        } finally {
            if(orgFile.exists()) orgFile.delete();
        }
    }

}

