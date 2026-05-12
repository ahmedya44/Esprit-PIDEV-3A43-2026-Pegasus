package com.pegasus.entities;

public class QuizQuestion {
    private int id;
    private String questionText;
    private int points;
    private int orderIndex;
    private int quizId;

    public QuizQuestion() {
    }

    public QuizQuestion(int id, String questionText, int points, int orderIndex, int quizId) {
        this.id = id;
        this.questionText = questionText;
        this.points = points;
        this.orderIndex = orderIndex;
        this.quizId = quizId;
    }

    public QuizQuestion(String questionText, int points, int orderIndex, int quizId) {
        this.questionText = questionText;
        this.points = points;
        this.orderIndex = orderIndex;
        this.quizId = quizId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "QuizQuestion{" +
                "id=" + id +
                ", points=" + points +
                ", orderIndex=" + orderIndex +
                ", quizId=" + quizId +
                '}';
    }
}
