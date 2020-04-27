package com.example.quiz2.history;

public class History {
    private String CorrectQuestions;
    private String TotalQuestions;
    private String Date;

    public History(String correctQuestions, String totalQuestions, String date) {
        CorrectQuestions = correctQuestions;
        TotalQuestions = totalQuestions;
        Date = date;
    }

    public String getCorrectQuestions() {
        return CorrectQuestions;
    }

    public String getTotalQuestions() {
        return TotalQuestions;
    }

    public String getDate() {
        return Date;
    }
}
