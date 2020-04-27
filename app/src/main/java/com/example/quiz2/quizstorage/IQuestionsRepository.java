package com.example.quiz2.quizstorage;

import java.util.ArrayList;

public interface IQuestionsRepository {
    void Add(Question question);
    void Delete(int position);
    ArrayList<Question> GetQuestions();
}
