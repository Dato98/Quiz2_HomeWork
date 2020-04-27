package com.example.quiz2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quiz2.data.IStorage;
import com.example.quiz2.data.StorageSharedPreference;
import com.example.quiz2.history.History;
import com.example.quiz2.history.HistoryRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnStartQuiz,btnOpenQuestionsBank;
    private ListView LviewHistory;
    private HistoryArrayAdapter historyArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartQuiz = findViewById(R.id.btnStartQuiz);
        btnOpenQuestionsBank = findViewById(R.id.btnOpenQuestionsBank);
        LviewHistory = findViewById(R.id.LviewHistory);
        historyArrayAdapter = new HistoryArrayAdapter(this,0,new ArrayList<History>());
        LviewHistory.setAdapter(historyArrayAdapter);
        //LoadListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadListView();
    }

    public void StartQuiz(View view) {
        Intent intent = new Intent(this,Quiz.class);
        startActivity(intent);
    }

    public void OpenQuestionsBank(View view) {
        Intent intent = new Intent(this,QuestionsBank.class);
        startActivity(intent);
    }

    void LoadListView(){
        historyArrayAdapter.clear();
        IStorage storage = new StorageSharedPreference();
        HistoryRepository historyRepository = (HistoryRepository)storage.get(this,HistoryRepository.HISTORY_REPO_KEY,HistoryRepository.class);
        if(historyRepository != null){
            Collections.reverse(historyRepository.GetAllHistory());
            historyArrayAdapter.addAll(historyRepository.GetAllHistory());
        }
    }

    class HistoryArrayAdapter extends ArrayAdapter<History>{
        Context mContext;

        public HistoryArrayAdapter(@NonNull Context context, int resource, @NonNull List<History> objects) {
            super(context, resource, objects);
            mContext = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            History history = getItem(position);
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.history_item,parent,false);

            TextView txtCorrectAnswers = view.findViewById(R.id.txtCorrectAnswers);
            TextView txtTotalQuestions = view.findViewById(R.id.txtTotalQuestions);
            TextView txtDatetime = view.findViewById(R.id.txtDatetime);

            txtCorrectAnswers.setText(history.getCorrectQuestions());
            txtTotalQuestions.setText(history.getTotalQuestions());
            txtDatetime.setText(history.getDate());
            return view;
        }
    }
}
