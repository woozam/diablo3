package com.woozam.wdthelper.common;

import org.json.JSONObject;

public class JSONUtils {

    public static Object get(JSONObject json, String name, Object defValue) {
        try {
            return json.get(name);
        } catch (Exception ignored) {
        }
        return defValue;
    }

    public static int getInt(JSONObject json, String name, int defValue) {
        try {
            return json.getInt(name);
        } catch (Exception ignored) {
        }
        return defValue;
    }

    public static float getFloat(JSONObject json, String name, float defValue) {
        try {
            return Float.parseFloat(json.getString(name));
        } catch (Exception ignored) {
        }
        return defValue;
    }

    public static boolean getBoolean(JSONObject json, String name, boolean defValue) {
        try {
            return json.getBoolean(name);
        } catch (Exception ignored) {
        }
        return defValue;
    }

    public static String getString(JSONObject json, String name, String defValue) {
        try {
            return json.isNull(name) ? defValue : json.getString(name);
        } catch (Exception ignored) {
        }
        return defValue;
    }

    public static Double getDouble(JSONObject json, String name, Double defValue) {
        try {
            return json.getDouble(name);
        } catch (Exception ignored) {
        }
        return defValue;
    }

    public static Long getLong(JSONObject json, String name, Long defValue) {
        try {
            return json.getLong(name);
        } catch (Exception ignored) {
        }
        return defValue;
    }
}