package com.example.quiz2.data;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;

import java.util.ArrayList;

public class StorageSharedPreference implements IStorage {
    @Override
    public void save(Context context, String key, Object value) {
        SharedPreferences sharedPreferences = getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,new Gson().toJson(value));
        editor.commit();
    }

    @Override
    public Object get(Context context, String key,Class cl) {
        SharedPreferences sharedPreferences = getInstance(context);
        String data = sharedPreferences.getString(key,null);
        return data == null ? null : new Gson().fromJson(data,cl);
    }


    private SharedPreferences getInstance(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("file", Context.MODE_PRIVATE);
        return sharedPref;
    }
}
