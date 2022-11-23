package com.vn.jinx.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;

public class JsonUtils {
  private static final Gson GSON =
      (new GsonBuilder()).excludeFieldsWithModifiers(8).disableHtmlEscaping().create();

  private JsonUtils() {}

  public static <T> T parse(String json, Class<T> clazz) {
    return GSON.fromJson(json, clazz);
  }

  public static <T> T parse(String json, Type type) {
    return GSON.fromJson(json, type);
  }
}
