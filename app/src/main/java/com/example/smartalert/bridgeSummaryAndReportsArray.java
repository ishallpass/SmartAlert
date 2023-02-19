package com.example.smartalert;

import java.util.ArrayList;

class bridgeSummaryAndReportsArray{
    ArrayList<String> imgurls;
    ArrayList<String> comments;
    ArrayList<String> userID;
    ArrayList<Float> longitude,latitude;
    long firstTimestamp,lastTimestamp;


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

    public long getFirstTimestamp() {
        return firstTimestamp;
    }

    public long getLastTimestamp() {
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

    public void setFirstTimestamp(long firstTimestamp) {
        this.firstTimestamp = firstTimestamp;
    }

    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }
}