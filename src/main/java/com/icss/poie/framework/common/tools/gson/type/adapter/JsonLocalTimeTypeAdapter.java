package com.icss.poie.framework.common.tools.gson.type.adapter;

import com.icss.poie.framework.common.tools.gson.GsonBuilder;
import com.icss.poie.framework.common.tools.DateUtils;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalTime;

public class JsonLocalTimeTypeAdapter implements GsonBuilder.JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

    public static Class getType(){
        return LocalTime.class;
    }

    public JsonLocalTimeTypeAdapter(){

    }

    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!(json instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }

        long nanoOfDay = json.getAsLong();
        LocalTime dateTime = DateUtils.toLocalTime(nanoOfDay);
        return dateTime;
    }

    @Override
    public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(DateUtils.toNanoOfDay(src));
    }
}
