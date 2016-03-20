package com.zenus.gistreader.util;

import android.content.Context;
import android.widget.Toast;

import com.zenus.gistreader.application.GistApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Utilities {

    public Utilities() {
    }

    public static void toast(String msg) {
        toast(GistApplication.getContext(), msg);
        Logger.log("toast", msg);
    }

    public static void toast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static void log(String msg) {
        Logger.log("debug", msg);
    }

    /**
     * convert a {@link JSONObject} to a {@link Map}
     */
    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        Iterator<String> keysItr = json.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = json.get(key);

            if (value instanceof JSONArray)
                value = toList((JSONArray) value);
            else if (value instanceof JSONObject)
                value = jsonToMap((JSONObject) value);
            map.put(key, value);
        }
        return map;
    }


    /**
     * parse a {@link JSONArray} to a list of objects
     */
    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = jsonToMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public static void longToast(String message) {
        Toast.makeText(GistApplication.getContext(), message, Toast.LENGTH_LONG).show();
    }

    public static void shortToast(String message) {
        Toast.makeText(GistApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * returns if the given string is empty, i.e. the string either null or has no letters
     */
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * convert an object to map
     */
    public static Map<String, Object> toMap(Object object) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(object);
            map.put(name, value);
        }
        return map;
    }
}
