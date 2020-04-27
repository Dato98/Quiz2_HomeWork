package com.example.quiz2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz2.data.IStorage;
import com.example.quiz2.data.StorageSharedPreference;
import com.example.quiz2.history.History;
import com.example.quiz2.history.HistoryRepository;
import com.example.quiz2.history.IHistoryRepository;
import com.example.quiz2.quizstorage.Question;
import com.example.quiz2.quizstorage.QuestionsRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Quiz extends AppCompatActivity {
    private ListView LviewQuiz;
    private Button btnCheckResult;
    private QuizArrayAdapter quizArrayAdapter;
    private ArrayList<Question> questions;
    private int[] answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        LviewQuiz = findViewById(R.id.LviewQuiz);
        btnCheckResult = findViewById(R.id.btnCheckResult);
        quizArrayAdapter = new QuizArrayAdapter(this,0,new ArrayList<Question>());
        LviewQuiz.setAdapter(quizArrayAdapter);
        LoadListView();
        answers = new int[quizArrayAdapter.getCount()];
    }

    void LoadListView(){
        quizArrayAdapter.clear();
        QuestionsRepository questionsRepository = (QuestionsRepository) GetRepositoryObject(QuestionsRepository.QUESTIONS_REPO_KEY,QuestionsRepository.class);
        questions = new ArrayList<>(questionsRepository.GetQuestions());
        quizArrayAdapter.addAll(questionsRepository.GetQuestions());
    }

    Object GetRepositoryObject(String key,Class cl){
        IStorage storage = new StorageSharedPreference();
        Object object = storage.get(this,key,cl);
        return object;
    }


    public void CheckResult(View view) {
        Check();
        LoadListView();
        answers=new int[quizArrayAdapter.getCount()];
    }

    void Check(){
        int TotalQuestions = answers.length;
        int CorrectAnswers = 0;
        for(int i = 0;i<TotalQuestions;i++){
            if(answers[i] != 0){
                if(questions.get(i).getChoices().get(answers[i]-1).equals(questions.get(i).getCorrectAnswer())){
                    CorrectAnswers++;
                }
            }
        }
        SaveHistory(Integer.toString(TotalQuestions),Integer.toString(CorrectAnswers));
        Toast.makeText(getApplicationContext(),String.format("სწორი პასუხების რაოდენობა : %d/%d",CorrectAnswers,TotalQuestions),Toast.LENGTH_SHORT).show();
    }

    void SaveHistory(String TotalQuestions,String CorrectAnswers){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        History history = new History(CorrectAnswers,TotalQuestions,formatter.format(date).toString().toString());
        HistoryRepository historyRepository = (HistoryRepository)GetRepositoryObject(HistoryRepository.HISTORY_REPO_KEY,HistoryRepository.class);
        if(historyRepository == null)
            historyRepository = new HistoryRepository();
        historyRepository.Add(history);
        IStorage storage = new StorageSharedPreference();
        storage.save(this,HistoryRepository.HISTORY_REPO_KEY,historyRepository);
    }

    class QuizArrayAdapter extends ArrayAdapter<Question>{
        Context mContext;


        public QuizArrayAdapter(@NonNull Context context, int resource, @NonNull List<Question> objects) {
            super(context, resource, objects);
            mContext = context;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Question question = getItem(position);
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.quiz_item,parent,false);
            TextView txtQuestion = view.findViewById(R.id.QuizQuestion);
            txtQuestion.setText(question.getQuestionText());
            final RadioGroup rgroupAnswers = view.findViewById(R.id.RgrpAnswers);

            final RadioButton btnChoice1 = view.findViewById(R.id.rbChoice1);
            btnChoice1.setText(question.getChoices().get(0));
            btnChoice1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    answers[position] = 1;
                    CheckAnswer(btnChoice1,isChecked,question);
                    LockButtons(rgroupAnswers);
                }
            });

            final RadioButton btnChoice2 = view.findViewById(R.id.rbChoice2);
            btnChoice2.setText(question.getChoices().get(1));
            btnChoice2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    answers[position] = 2;
                    CheckAnswer(btnChoice2,isChecked,question);
                    LockButtons(rgroupAnswers);
                }
            });

            final RadioButton btnChoice3 = view.findViewById(R.id.rbChoice3);
            btnChoice3.setText(question.getChoices().get(2));
            btnChoice3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    answers[position] = 3;
                    CheckAnswer(btnChoice3,isChecked,question);
                    LockButtons(rgroupAnswers);
                }
            });

            final RadioButton btnChoice4 = view.findViewById(R.id.rbChoice4);
            btnChoice4.setText(question.getChoices().get(3));
            btnChoice4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    answers[position] = 4;
                    CheckAnswer(btnChoice4,isChecked,question);
                    LockButtons(rgroupAnswers);
                }
            });
            ArrayList<RadioButton> radioButtonArrayList = new ArrayList<>(Arrays.asList(btnChoice1,btnChoice2,btnChoice3,btnChoice4));
            if(answers[position] != 0)
                radioButtonArrayList.get(answers[position]-1).setChecked(true);


            return view;
        }

        void CheckAnswer(RadioButton btn,boolean isChecked,Question question){
            if(isChecked){
                if(btn.getText().equals(question.getCorrectAnswer()))
                    btn.setBackgroundColor(Color.GREEN);
                else
                    btn.setBackgroundColor(Color.RED);
            }
        }

        void LockButtons(RadioGroup radioGroup){
            for(int i = 0;i<radioGroup.getChildCount();i++){
                radioGroup.getChildAt(i).setEnabled(false);
            }
        }
    }
}
