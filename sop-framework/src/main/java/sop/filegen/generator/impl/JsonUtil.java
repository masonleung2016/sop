package sop.filegen.generator.impl;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import sop.util.DateTypeAdapter;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:57
 * @Package: sop.filegen.generator.impl
 */

public class JsonUtil {
    public static String toJson(Object request) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
        Gson gson = gsonBuilder.create();
        return gson.toJson(request);
    }

    public static Object fromJson(String json, Type type) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapter(Object.class, new NaturalDeserializer());
        gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
        Gson gson = gsonBuilder.create();

        return gson.fromJson(json, type);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        //Gson gson = new Gson();
        //return gson.toJson(request);

        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapter(Object.class, new NaturalDeserializer());
        gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
        Gson gson = gsonBuilder.create();

        return gson.fromJson(json, clazz);
    }

    public static String toJsonForDate(Object request) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
        gsonBuilder.setDateFormat(DateTypeAdapter.JSON_DATE_FORMAT);
        Gson gson = gsonBuilder.create();
        return gson.toJson(request);
    }

    public static Object fromJsonForDate(String json, Type type) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapter(Object.class, new NaturalDeserializer());
        gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
        gsonBuilder.setDateFormat(DateTypeAdapter.JSON_DATE_FORMAT);
        Gson gson = gsonBuilder.create();

        return gson.fromJson(json, type);
    }
}
