package com.example.myapplication.utils;

import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.preferences.Preferences;

import java.util.Map;

/**
 * @author ITApeDeHao
 * @date 2022/10/13 22 44
 * discription
 */
public abstract class   LocateDataUtil {
    private static final String preferenceFile = "preferences";
    static {
    }

    public static Map<String,String> readData(Context context, Map<String,String> map){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        Preferences preferences = databaseHelper.getPreferences(preferenceFile);
        map.replaceAll((k, v) -> preferences.getString(k, ""));
        return map;
    }

    public static boolean writeData(Context context, Map<String,String> map){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        Preferences preferences = databaseHelper.getPreferences(preferenceFile);
        for (String key: map.keySet()){
            preferences.putString(key, map.get(key));
        }
        preferences.flush();
        return true;
    }
}
