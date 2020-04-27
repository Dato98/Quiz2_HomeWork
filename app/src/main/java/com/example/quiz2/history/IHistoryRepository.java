package com.example.quiz2.history;

import android.content.Context;

import java.util.ArrayList;

public interface IHistoryRepository {
    void Add(History history);
    ArrayList<History> GetAllHistory();
}
