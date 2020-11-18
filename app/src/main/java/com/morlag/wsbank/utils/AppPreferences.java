package com.morlag.wsbank.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* Класс, сохраняющий токен и другую информацию в систему
 *  Понадобится в будущем*/

public class AppPreferences {
    private static final String PREF_TOKEN = "token";
    public static String getToken(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_TOKEN, null);
    }
    public static void setToken(Context context, String token) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_TOKEN, token)
                .apply();
    }
}

