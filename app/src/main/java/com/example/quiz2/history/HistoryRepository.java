package com.example.quiz2.history;

import android.content.Context;

import java.util.ArrayList;

public class HistoryRepository implements IHistoryRepository {

    public static final String HISTORY_REPO_KEY = "historyItems";
    private ArrayList<History> histories = new ArrayList<>();

    public HistoryRepository() {
    }

    @Override
    public void Add(History history) {
        histories.add(history);
    }

    @Override
    public ArrayList<History> GetAllHistory() {
        return histories;
    }
}
