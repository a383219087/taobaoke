package com.starnet.cqj.taobaoke.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mini on 18/2/10.
 */

public class SharedPreferenceUtil {

    public static final String CLIP_SEARCH_HISTORY = "clip_search_history";

    public static String getString(Context context, String key) {
        SharedPreferences searchHistory = context.getSharedPreferences(CLIP_SEARCH_HISTORY, Context.MODE_PRIVATE);
        return searchHistory.getString(key, "");
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences searchHistory = context.getSharedPreferences(CLIP_SEARCH_HISTORY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = searchHistory.edit();
        edit.putString(key, value);
        edit.apply();
    }
}
