package com.example.smartalert;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

public class SummaryReports extends FirebaseMessagingService {
    private static final String TAG = "new Token";
    private ArrayList<String> UserIDs;
    private Integer Severity;
    private Float AvgLongitude;
    private Float AvgLatittude;
    private Timestamp Firsttimestamp;
    private String Category;
    private ArrayList<String> Imgurls;
    private ArrayList<String> Comments;

    public SummaryReports(ArrayList<String> userIDs, Integer severity, Float avgLongitude, Float avgLatittude, Timestamp firsttimestamp, String category, ArrayList<String> imgurls, ArrayList<String> comments) {
        UserIDs = userIDs;
        Severity = severity;
        AvgLongitude = avgLongitude;
        AvgLatittude = avgLatittude;
        Firsttimestamp = firsttimestamp;
        Category = category;
        Imgurls = imgurls;
        Comments = comments;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (true) {
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }

        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    public ArrayList<String> getUserIDs() {
        return UserIDs;
    }

    public Integer getSeverity() {
        return Severity;
    }

    public Float getAvgLongitude() {
        return AvgLongitude;
    }

    public Float getAvgLatittude() {
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

    public void setAvgLongitude(Float avgLongitude) {
        AvgLongitude = avgLongitude;
    }

    public void setAvgLatittude(Float avgLatittude) {
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

