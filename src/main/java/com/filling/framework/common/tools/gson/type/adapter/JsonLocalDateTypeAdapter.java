package com.filling.framework.common.tools.gson.type.adapter;

import com.filling.framework.common.tools.gson.GsonBuilder;
import com.filling.framework.common.tools.DateUtils;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class JsonLocalDateTypeAdapter implements GsonBuilder.JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {


    public static Class getType(){
        return LocalDate.class;
    }

    public JsonLocalDateTypeAdapter(){
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!(json instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }

        Long epochDay = json.getAsLong();
        LocalDate dateTime = DateUtils.toLocalDate(epochDay);
        return dateTime;
    }

    @Override
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(DateUtils.toEpochDay(date));
    }
}
