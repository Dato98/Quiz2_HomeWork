package com.example.quiz2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz2.data.IStorage;
import com.example.quiz2.data.StorageSharedPreference;
import com.example.quiz2.quizstorage.Question;
import com.example.quiz2.quizstorage.QuestionsRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class QuestionsBank extends AppCompatActivity {
    private EditText txtQuestion,txtCorrectAnswer,txtChoice1,txtChoice2,txtChoice3;
    private Button btnAddQuestion;
    private ListView LvQuestions;
    ArrayList<EditText> txtList;
    private QuestionsArrayAdapter questionsArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_bank);
        LvQuestions = findViewById(R.id.lviewquestions);
        txtQuestion = findViewById(R.id.newQuestion);
        txtCorrectAnswer = findViewById(R.id.correctAnswer);
        txtChoice1 = findViewById(R.id.Choice1);
        txtChoice2 = findViewById(R.id.Choice2);
        txtChoice3 = findViewById(R.id.Choice3);
        btnAddQuestion = findViewById(R.id.btnAddQuestion);
        txtList = new ArrayList<>(Arrays.asList(txtChoice1,txtChoice2,txtChoice3,txtCorrectAnswer,txtQuestion));
        questionsArrayAdapter = new QuestionsArrayAdapter(this,0,new ArrayList<Question>());
        LvQuestions.setAdapter(questionsArrayAdapter);
        LoadListView();
    }

    public void AddQuestion(View view) {
        if(check()){
            ArrayList<String> answers = new ArrayList<>(Arrays.asList(txtCorrectAnswer.getText().toString(),txtChoice1.getText().toString(),txtChoice2.getText().toString(),txtChoice3.getText().toString()));
            Collections.shuffle(answers);
            Question question = new Question(txtQuestion.getText().toString(),txtCorrectAnswer.getText().toString(),answers);

            QuestionsRepository questionsRepository = GetQuestionsRepo();

            questionsRepository.Add(question);
            IStorage storage = new StorageSharedPreference();
            storage.save(this,QuestionsRepository.QUESTIONS_REPO_KEY,questionsRepository);
            LoadListView();
        }else{
            Toast.makeText(getApplicationContext(),"Fill all fields",Toast.LENGTH_SHORT).show();
        }
        clear();
    }

    void LoadListView(){
        questionsArrayAdapter.clear();
        QuestionsRepository questionsRepository = GetQuestionsRepo();
        questionsArrayAdapter.addAll(questionsRepository.GetQuestions());
    }

    QuestionsRepository GetQuestionsRepo(){
        IStorage storage = new StorageSharedPreference();
        Object QuestionsrepoAsObject = storage.get(this, QuestionsRepository.QUESTIONS_REPO_KEY,QuestionsRepository.class);

        QuestionsRepository questionsRepository;
        if(QuestionsrepoAsObject != null)
            questionsRepository = (QuestionsRepository)QuestionsrepoAsObject;
        else
            questionsRepository = new QuestionsRepository();

        return questionsRepository;
    }

    boolean check(){
        for(int i = 0;i<txtList.size();i++)
            if(txtList.get(i).getText().length() == 0)
                return false;

        return true;
    }

    void clear(){
        for(int i = 0;i<txtList.size();i++)
            txtList.get(i).setText("");
    }


    class QuestionsArrayAdapter extends ArrayAdapter<Question>
    {
        private Context mContext;
        public QuestionsArrayAdapter(@NonNull Context context, int resource, @NonNull List<Question> objects) {
            super(context, resource, objects);
            mContext = context;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Question question = getItem(position);
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.questions_item,parent,false);
            TextView txtQuestion = view.findViewById(R.id.txtQuestion);
            txtQuestion.setText(question.getQuestionText());
            Button btnDelete = view.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Delete(position);
                    LoadListView();
                }
            });

            return view;
        }

        void Delete(int position){
            QuestionsRepository questionsRepository = GetQuestionsRepo();
            questionsRepository.Delete(position);
            IStorage storage = new StorageSharedPreference();
            storage.save(getContext().getApplicationContext(),QuestionsRepository.QUESTIONS_REPO_KEY,questionsRepository);
        }
    }
}
