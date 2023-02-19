package com.example.smartalert;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class bridgeSummaryAndReportsArray{
    ArrayList<String> imgurls;
    ArrayList<String> comments;
    ArrayList<String> userID;
    ArrayList<Float> longitude,latitude;
    Timestamp firstTimestamp,lastTimestamp;


    public bridgeSummaryAndReportsArray() {

    }

    public ArrayList<Float> getLongitude() {
        return longitude;
    }

    public ArrayList<Float> getLatitude() {
        return latitude;
    }

    public ArrayList<String> getImgurls() {
        return imgurls;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public ArrayList<String> getUserID() {
        return userID;
    }

    public Timestamp getFirstTimestamp() {
        return firstTimestamp;
    }

    public Timestamp getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLongitude(ArrayList<Float> longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(ArrayList<Float> latitude) {
        this.latitude = latitude;
    }

    public void setImgurls(ArrayList<String> imgurls) {
        this.imgurls = imgurls;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public void setUserID(ArrayList<String> userID) {
        this.userID = userID;
    }

    public void setFirstTimestamp(Timestamp firstTimestamp) {
        this.firstTimestamp = firstTimestamp;
    }

    public void setLastTimestamp(Timestamp lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }
}