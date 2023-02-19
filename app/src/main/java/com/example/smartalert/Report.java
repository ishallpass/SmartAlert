package com.example.smartalert;

import com.google.firebase.Timestamp;

public class Report {
    private String user_ID_FK;
    private String report_longitude;
    private String report_latitude;
    private long timestamp;
    private String category;
    private String comments;
    private String url_Image;

    public Report(String build_user_ID_FK, String build_report_longitude, String build_report_latitude, long build_timestamp, String build_category, String build_comments, String build_url_Image) {
        user_ID_FK = build_user_ID_FK;
        report_longitude = build_report_longitude;
        report_latitude = build_report_latitude;
        timestamp = build_timestamp;
        category = build_category;
        comments = build_comments;
        url_Image = build_url_Image;
    }

    public void setUser_ID_FK(String set_user_ID_FK) {
        user_ID_FK = user_ID_FK;
    }

    public void setReport_longitude(String set_report_longitude) {
        report_longitude = set_report_longitude;
    }

    public void setReport_latitude(String set_report_latitude) {
        report_latitude = set_report_latitude;
    }

    public void setTimestamp(long set_timestamp) {
        timestamp = set_timestamp;}

    public void setCategory(String set_category) {
        category = set_category;
    }

    public void setComments(String set_comments) {
        comments = set_comments;
    }

    public void setUrl_Image(String set_url_Image) {
        url_Image = set_url_Image;
    }

    public String getUser_ID_FK() {
        return user_ID_FK;
    }

    public String getReport_longitude() {
        return report_longitude;
    }

    public String getReport_latitude() {
        return report_latitude;
    }

    public long getTimespamp() {
        return timestamp;
    }

    public String getCategory() {
        return category;
    }

    public String getComments() {
        return comments;
    }

    public String getUrl_Image() {
        return url_Image;
    }
}
