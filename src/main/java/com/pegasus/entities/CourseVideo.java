package com.pegasus.entities;

public class CourseVideo {
    private int id;
    private String title;
    private String videoUrl;
    private int durationSec;
    private int orderIndex;
    private boolean preview;
    private int sectionId;

    public CourseVideo() {
    }

    public CourseVideo(int id, String title, String videoUrl, int durationSec, int orderIndex, boolean preview, int sectionId) {
        this.id = id;
        this.title = title;
        this.videoUrl = videoUrl;
        this.durationSec = durationSec;
        this.orderIndex = orderIndex;
        this.preview = preview;
        this.sectionId = sectionId;
    }

    public CourseVideo(String title, String videoUrl, int durationSec, int orderIndex, boolean preview, int sectionId) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.durationSec = durationSec;
        this.orderIndex = orderIndex;
        this.preview = preview;
        this.sectionId = sectionId;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(int durationSec) {
        this.durationSec = durationSec;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public boolean isPreview() {
        return preview;
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public String toString() {
        return "CourseVideo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", durationSec=" + durationSec +
                ", orderIndex=" + orderIndex +
                ", preview=" + preview +
                ", sectionId=" + sectionId +
                '}';
    }
}
