package com.example.quiz2.quizstorage;

import java.util.ArrayList;

public class Question {
    private String QuestionText;
    private String CorrectAnswer;
    private ArrayList<String> Choices;

    public String getQuestionText() {
        return QuestionText;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public ArrayList<String> getChoices() {
        return Choices;
    }

    public Question(String questionText, String correctAnswer, ArrayList<String> choices) {
        QuestionText = questionText;
        CorrectAnswer = correctAnswer;
        Choices = choices;
    }
}
