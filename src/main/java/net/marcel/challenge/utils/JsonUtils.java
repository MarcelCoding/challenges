package net.marcel.challenge.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonUtils {

    public static final Gson GSON = new Gson();

    private JsonUtils() {
    }

    public static void set(final JsonObject json, final String key, final Object value) {
        json.add(key, GSON.toJsonTree(value));
    }

    public static <T> T get(final JsonObject json, final String key, final Class<T> type) {
        if (json.has(key)) return GSON.fromJson(json.get(key), type);
        else return null;
    }
}
