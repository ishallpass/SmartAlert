package com.example.smartalert;

import java.sql.Timestamp;
import java.util.ArrayList;

public class SummaryReports {
    private String ID;
    private ArrayList<String> UserIDs;
    private Integer Severity;
    private String AvgLongitude;
    private String AvgLatittude;
    private Timestamp Firsttimestamp;
    private String Category;
    private ArrayList<String> Imgurls;
    private ArrayList<String> Comments;

    public SummaryReports(String ID, ArrayList<String> userIDs, Integer severity, String avgLongitude, String avgLatittude, Timestamp firsttimestamp, String category, ArrayList<String> imgurls, ArrayList<String> comments) {
        this.ID = ID;
        UserIDs = userIDs;
        Severity = severity;
        AvgLongitude = avgLongitude;
        AvgLatittude = avgLatittude;
        Firsttimestamp = firsttimestamp;
        Category = category;
        Imgurls = imgurls;
        Comments = comments;
    }

    public String getID() {
        return ID;
    }

    public ArrayList<String> getUserIDs() {
        return UserIDs;
    }

    public Integer getSeverity() {
        return Severity;
    }

    public String getAvgLongitude() {
        return AvgLongitude;
    }

    public String getAvgLatittude() {
        return AvgLatittude;
    }

    public Timestamp getFirsttimestamp() {
        return Firsttimestamp;
    }

    public String getCategory() {
        return Category;
    }

    public ArrayList<String> getImgurls() {
        return Imgurls;
    }

    public ArrayList<String> getComments() {
        return Comments;
    }

    public void setUserIDs(ArrayList<String> userIDs) {
        UserIDs = userIDs;
    }

    public void setSeverity(Integer severity) {
        Severity = severity;
    }

    public void setAvgLongitude(String avgLongitude) {
        AvgLongitude = avgLongitude;
    }

    public void setAvgLatittude(String avgLatittude) {
        AvgLatittude = avgLatittude;
    }

    public void setFirsttimestamp(Timestamp firsttimestamp) {
        Firsttimestamp = firsttimestamp;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setImgurls(ArrayList<String> imgurls) {
        Imgurls = imgurls;
    }

    public void setComments(ArrayList<String> comments) {
        Comments = comments;
    }
}

