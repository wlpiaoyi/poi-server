package com.filling.module.poi.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.filling.framework.common.tools.ValueUtils;
import com.filling.framework.common.tools.data.DataUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.springframework.format.annotation.DateTimeFormat;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/21 17:05
 * {@code @version:}:       1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity {

    public static final String ZONE = "GMT+8";

    private static final MessageDigest MESSAGE_DIGEST;

    static {
        try {
            MESSAGE_DIGEST = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "创建人")
    private Long createUser;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "创建部门")
    private Long createDept;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss", timezone = BaseEntity.ZONE)
    @Schema(description = "创建时间")
    private Date createTime;

    @JsonSerialize( using = ToStringSerializer.class)
    @Schema(description = "更新人")
    private Long updateUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = BaseEntity.ZONE)
    @Schema(description = "更新时间")
    private Date updateTime;
    public void clearDb(){
        this.setCreateTime(null);
        this.setUpdateTime(null);
        this.setCreateUser(null);
        this.setUpdateUser(null);
    }

    public void synProperties() {

    }

    @SneakyThrows
    public static String createPropertyCode(String property){
        if(ValueUtils.isBlank(property)){
            return null;
        }
        String vp = property + new Random().nextInt() % 1000;
        MESSAGE_DIGEST.update(vp.getBytes(StandardCharsets.UTF_8));
        byte[] md5b = MESSAGE_DIGEST.digest();
        String propertyCode = new String(DataUtils.base64Encode(md5b))
                .replaceAll("\\+", "")
                .replaceAll("/", "")
                .replaceAll("=", "");
        return propertyCode;
    }
}
