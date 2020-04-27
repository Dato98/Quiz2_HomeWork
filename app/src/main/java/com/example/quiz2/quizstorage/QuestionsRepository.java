package com.example.quiz2.quizstorage;

import java.util.ArrayList;

public class QuestionsRepository implements IQuestionsRepository {
    public static final String QUESTIONS_REPO_KEY = "questionsItems";
    private ArrayList<Question> questions = new ArrayList<>();
    @Override
    public void Add(Question question) {
        questions.add(question);
    }

    @Override
    public void Delete(int position) {
        questions.remove(position);
    }

    @Override
    public ArrayList<Question> GetQuestions() {
        return questions;
    }
}
