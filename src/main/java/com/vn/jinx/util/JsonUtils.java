package com.vn.jinx.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.net.URLEncoder;

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

  public static String encodeURI(String s) {
    String result;
    try {
      result = URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20").replaceAll("\\%21", "!")
          .replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")")
          .replaceAll("\\%7E", "~");
    } // This exception should never occur.
    catch (Exception e) {
      result = s;
    }

    return result;
  }
}
