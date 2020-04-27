package com.example.quiz2.data;

import android.content.Context;

import java.util.ArrayList;

public interface IStorage {
    void save(Context context, String key, Object value);

    Object get(Context context, String key,Class cl);

}
