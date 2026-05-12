package com.pegasus.entities;

public class CourseSection {
    private int id;
    private String title;
    private int orderIndex;
    private int courseId;

    public CourseSection() {
    }

    public CourseSection(int id, String title, int orderIndex, int courseId) {
        this.id = id;
        this.title = title;
        this.orderIndex = orderIndex;
        this.courseId = courseId;
    }

    public CourseSection(String title, int orderIndex, int courseId) {
        this.title = title;
        this.orderIndex = orderIndex;
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

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "CourseSection{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", orderIndex=" + orderIndex +
                ", courseId=" + courseId +
                '}';
    }
}
