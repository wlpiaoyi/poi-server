package com.filling.module.poi.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.filling.framework.common.tools.ValueUtils;
import com.filling.framework.common.tools.data.DataUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Update;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/21 15:28
 * {@code @version:}:       1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseMongoEntity extends BaseEntity{

    public static String collectionName(){
        return BaseMongoEntity.class.getName();
    }

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "主键id")
    private ObjectId id;
    public String mapKey(){
        if(this.getId() == null){
            return null;
        }
        return this.getId().toHexString();
    }

    public void clearDb(){
        this.setId(null);
        super.clearDb();
    }

    public void synProperties(){

    }

    @SneakyThrows
    public static String createPropertyCode(String property){
        if(ValueUtils.isBlank(property)){
            return null;
        }

        String vp = property + new Random().nextInt() % 1000;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(vp.getBytes(StandardCharsets.UTF_8));
        byte[] md5b = md.digest();
        String propertyCode = new String(DataUtils.base64Encode(md5b))
                .replaceAll("\\+", "")
                .replaceAll("/", "")
                .replaceAll("=", "");
        return propertyCode;
    }

    public Update parseForUpdate(Update update){
        if(update == null){
            update = Update.update("_id", this.getId());
        }
        this.setUpdateTime(new Date());
        update.set("updateTime", this.getUpdateTime());
        return update;
    }
}
