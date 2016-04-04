package com.woozam.wdthelper.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.woozam.wdthelper.D3Application;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by woozam on 2016-01-31.
 */

public class PreferenceUtils {

    private static final String PREFERENCE = "diablo3_preferences";

    public static final String KEY_LAST_BATTLE_TAG = "last_battle_tag";
    public static final String KEY_LAST_BATTLE_TAG_SERVER = "last_battle_tag_server";
    public static final String KEY_LAST_RANKING_PARAMETER_SERVER = "last_ranking_parameter_server";
    public static final String KEY_LAST_RANKING_PARAMETER_TYPE = "last_ranking_parameter_type";
    public static final String KEY_LAST_RANKING_PARAMETER_SEASON = "last_ranking_parameter_season";
    public static final String KEY_LAST_RANKING_PARAMETER_SEASONAL = "last_ranking_parameter_seasonal";
    public static final String KEY_LAST_RANKING_PARAMETER_HARDCORE = "last_ranking_parameter_hardcore";
    public static final String KEY_PURCHASED_REMOVE_ADS = "purchased_remove_ads";
    public static final String KEY_BONUS_BOUNTY_ORDER_SHOW_STATUS_BAR = "bonus_bounty_order_show_status_bar";

    private static HashMap<String, Object> PREF_HASH_MAP = new HashMap<>();
    private static LinkedBlockingQueue<PrefJob> PREF_JOB_QUEUE = new LinkedBlockingQueue<>();
    private static PrefThread PREF_THREAD = null;

    private static class PrefJob {

        private enum Type {
            INTEGER, LONG, BOOLEAN, STRING
        }

        private Type type;
        private String key;
        private Object value;
        private int mode;

        public PrefJob(String key, Object value, int mode, Type type) {
            this.type = type;
            this.key = key;
            this.value = value;
            this.mode = mode;
        }
    }

    private static class PrefThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    PrefJob job = PREF_JOB_QUEUE.take();
                    SharedPreferences pref = D3Application.getContext().getSharedPreferences(PREFERENCE, job.mode);
                    SharedPreferences.Editor editor = pref.edit();
                    switch (job.type) {
                        case INTEGER:
                            editor.putInt(job.key, (Integer) job.value);
                            break;
                        case LONG:
                            editor.putLong(job.key, (Long) job.value);
                            break;
                        case BOOLEAN:
                            editor.putBoolean(job.key, (Boolean) job.value);
                            break;
                        case STRING:
                            editor.putString(job.key, job.value == null ? null : (String) job.value);
                            break;
                    }
                    editor.apply();
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (Error e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void clearAllValues() {
        SharedPreferences pref = D3Application.getContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    private static void putJob(PrefJob job) {
        if (PREF_THREAD == null) {
            synchronized (PreferenceUtils.class) {
                if (PREF_THREAD == null) {
                    PREF_THREAD = new PrefThread();
                    PREF_THREAD.start();
                }
            }
        }
        PREF_JOB_QUEUE.add(job);
    }

    public static void setValue(String key, int value) {
        Object current = PREF_HASH_MAP.put(key, value);
        if (current == null || !current.equals(value)) {
            putJob(new PrefJob(key, value, Context.MODE_PRIVATE, PrefJob.Type.INTEGER));
        }
    }

    public static void setValue(String key, long value) {
        Object current = PREF_HASH_MAP.put(key, value);
        if (current == null || !current.equals(value)) {
            putJob(new PrefJob(key, value, Context.MODE_PRIVATE, PrefJob.Type.LONG));
        }
    }

    public static void setValue(String key, boolean value) {
        Object current = PREF_HASH_MAP.put(key, value);
        if (current == null || !current.equals(value)) {
            putJob(new PrefJob(key, value, Context.MODE_PRIVATE, PrefJob.Type.BOOLEAN));
        }
    }

    public static void setValue(String key, String value) {
        Object current = PREF_HASH_MAP.put(key, value);
        if (current == null || value == null || !current.equals(value)) {
            putJob(new PrefJob(key, value, Context.MODE_PRIVATE, PrefJob.Type.STRING));
        }
    }

    public static int getIntValue(String key, int defalut_value) {
        Object result = PREF_HASH_MAP.get(key);
        if (result == null || !(result instanceof Integer)) {
            SharedPreferences pref = D3Application.getContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
            try {
                result = pref.getInt(key, defalut_value);
            } catch (ClassCastException e) {
                e.printStackTrace();
                try {
                    result = Integer.valueOf(pref.getString(key, String.valueOf(defalut_value)));
                    setValue(key, (Integer) result);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    setValue(key, defalut_value);
                    result = defalut_value;
                }
            }
            PREF_HASH_MAP.put(key, result);
        }
        try {
            return (int) result;
        } catch (Exception e) {
            return defalut_value;
        }
    }

    public static long getLongValue(String key, long defalut_value) {
        Object result = PREF_HASH_MAP.get(key);
        if (result == null || !(result instanceof Integer)) {
            SharedPreferences pref = D3Application.getContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
            try {
                result = pref.getLong(key, defalut_value);
            } catch (ClassCastException e) {
                e.printStackTrace();
                try {
                    result = Long.valueOf(pref.getString(key, String.valueOf(defalut_value)));
                    setValue(key, (Long) result);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    setValue(key, defalut_value);
                    result = defalut_value;
                }
            }
            PREF_HASH_MAP.put(key, result);
        }
        try {
            return (long) result;
        } catch (Exception e) {
            return defalut_value;
        }
    }

    public static boolean getBooleanValue(String key, boolean defalut_value) {
        Object result = PREF_HASH_MAP.get(key);
        if (result == null || !(result instanceof Boolean)) {
            SharedPreferences pref = D3Application.getContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
            try {
                result = pref.getBoolean(key, defalut_value);
            } catch (ClassCastException e) {
                e.printStackTrace();
                try {
                    result = Boolean.valueOf(pref.getString(key, String.valueOf(defalut_value)));
                    setValue(key, (Boolean) result);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    setValue(key, defalut_value);
                    result = defalut_value;
                }
            }
            PREF_HASH_MAP.put(key, result);
        }
        try {
            return (boolean) result;
        } catch (Exception e) {
            return defalut_value;
        }
    }

    public static String getStringValue(String key, String defalut_value) {
        Object result = PREF_HASH_MAP.get(key);
        if (result == null || !(result instanceof String)) {
            SharedPreferences pref = D3Application.getContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
            result = pref.getString(key, defalut_value);
            PREF_HASH_MAP.put(key, result);
        }
        try {
            return (String) result;
        } catch (Exception e) {
            return defalut_value;
        }
    }
}