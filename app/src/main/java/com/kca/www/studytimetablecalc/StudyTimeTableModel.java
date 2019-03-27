package com.kca.www.studytimetablecalc;

import android.os.Parcelable;

public class StudyTimeTableModel {
    private String course_day = "";
    private String priority_hour = "";

    public StudyTimeTableModel() { }

    public StudyTimeTableModel(String course_day, String priority_hour) {
        this.course_day = course_day;
        this.priority_hour = priority_hour;
    }

    public String getCourse_day() {
        return course_day;
    }

    public void setCourse_day(String course_day) {
        this.course_day = course_day;
    }

    public String getPriority_hour() {
        return priority_hour;
    }

    public void setPriority_hour(String priority_hour) {
        this.priority_hour = priority_hour;
    }
}