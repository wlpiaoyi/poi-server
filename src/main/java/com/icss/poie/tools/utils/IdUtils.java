package com.icss.poie.tools.utils;

import lombok.SneakyThrows;
import org.wlpiaoyi.framework.utils.StringUtils;
import org.wlpiaoyi.framework.utils.ValueUtils;
import org.wlpiaoyi.framework.utils.data.DataUtils;
import org.wlpiaoyi.framework.utils.encrypt.rsa.Coder;
import org.wlpiaoyi.framework.utils.snowflake.IdWorker;

import java.nio.charset.StandardCharsets;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/16 15:12
 * {@code @version:}:       1.0
 */
public class IdUtils {

    private static final IdWorker idWorker = new IdWorker((byte) 0, (byte) 0, 1694767192413L);
    public static long nextId(){
        return idWorker.nextId();
    }

//    @SneakyThrows
//    public static void main(String[] args) {
//        String v = "我的";
//        DataUtils.base64Encode("我的", StandardCharsets.UTF_8)
//        String md5 = new String(Coder.encryptMD5(v.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
//    }
}
