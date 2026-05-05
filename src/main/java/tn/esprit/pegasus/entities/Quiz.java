package tn.esprit.pegasus.entities;

public class Quiz {
    private int id;
    private String title;
    private Integer timeLimitMin;
    private int passingScore;
    private Integer attemptLimit;
    private int courseId;

    public Quiz() {
    }

    public Quiz(int id, String title, Integer timeLimitMin, int passingScore, Integer attemptLimit, int courseId) {
        this.id = id;
        this.title = title;
        this.timeLimitMin = timeLimitMin;
        this.passingScore = passingScore;
        this.attemptLimit = attemptLimit;
        this.courseId = courseId;
    }

    public Quiz(String title, Integer timeLimitMin, int passingScore, Integer attemptLimit, int courseId) {
        this.title = title;
        this.timeLimitMin = timeLimitMin;
        this.passingScore = passingScore;
        this.attemptLimit = attemptLimit;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTimeLimitMin() {
        return timeLimitMin;
    }

    public void setTimeLimitMin(Integer timeLimitMin) {
        this.timeLimitMin = timeLimitMin;
    }

    public int getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(int passingScore) {
        this.passingScore = passingScore;
    }

    public Integer getAttemptLimit() {
        return attemptLimit;
    }

    public void setAttemptLimit(Integer attemptLimit) {
        this.attemptLimit = attemptLimit;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", passingScore=" + passingScore +
                ", courseId=" + courseId +
                '}';
    }
}
