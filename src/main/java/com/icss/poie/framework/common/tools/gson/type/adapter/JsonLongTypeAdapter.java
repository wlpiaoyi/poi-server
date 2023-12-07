package com.icss.poie.framework.common.tools.gson.type.adapter;

import com.icss.poie.framework.common.tools.gson.GsonBuilder;
import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonLongTypeAdapter implements GsonBuilder.JsonSerializer<Long>, JsonDeserializer<Long> {

    public static Class getType(){
        return Long.class;
    }

    public JsonLongTypeAdapter(){

    }

    @Override
    public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.getAsLong();
    }

    @Override
    public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
