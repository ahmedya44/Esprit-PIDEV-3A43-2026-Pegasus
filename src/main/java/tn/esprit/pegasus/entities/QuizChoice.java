package tn.esprit.pegasus.entities;

public class QuizChoice {
    private int id;
    private String label;
    private boolean correct;
    private int questionId;

    public QuizChoice() {
    }

    public QuizChoice(int id, String label, boolean correct, int questionId) {
        this.id = id;
        this.label = label;
        this.correct = correct;
        this.questionId = questionId;
    }

    public QuizChoice(String label, boolean correct, int questionId) {
        this.label = label;
        this.correct = correct;
        this.questionId = questionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "QuizChoice{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", correct=" + correct +
                ", questionId=" + questionId +
                '}';
    }
}
