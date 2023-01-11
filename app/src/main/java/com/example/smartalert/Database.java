package com.example.smartalert;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.LongStream;

public class Database {

    public class user{
        private String ID;
        private String username;
        private String password_hash;
        private String last_longitude;
        private String last_latitude;
        private String role;

        public user(String username, String password_hash, String last_longitude, String last_latitude, String role) {
            this.username = username;
            this.password_hash = password_hash;
            this.last_longitude = last_longitude;
            this.last_latitude = last_latitude;
            this.role = role;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getID() {
            return ID;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword_hash(String password_hash) {
            this.password_hash = password_hash;
        }

        public void setLast_longitude(String last_longitude) {
            this.last_longitude = last_longitude;
        }

        public void setLast_latitude(String last_latitude) {
            this.last_latitude = last_latitude;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword_hash() {
            return password_hash;
        }

        public String getLast_longitude() {
            return last_longitude;
        }

        public String getLast_latitude() {
            return last_latitude;
        }

        public String getRole() {
            return role;
        }
    }

    public class Reports{
        private String ID;
        private String User_ID_FK;
        private String Report_longitude;
        private String Report_latitude;
        private Timestamp Timespamt;
        private String Category;
        private String Comments;
        private String Url_Image;

        public Reports(String user_ID_FK, String report_longitude, String report_latitude, Timestamp timespamt, String category, String comments, String url_Image) {
            User_ID_FK = user_ID_FK;
            Report_longitude = report_longitude;
            Report_latitude = report_latitude;
            Timespamt = timespamt;
            Category = category;
            Comments = comments;
            Url_Image = url_Image;
        }
        public void setUser_ID_FK(String user_ID_FK) {
            User_ID_FK = user_ID_FK;
        }

        public void setReport_longitude(String report_longitude) {
            Report_longitude = report_longitude;
        }

        public void setReport_latitude(String report_latitude) {
            Report_latitude = report_latitude;
        }

        public void setTimespamt(Timestamp timespamt) {
            Timespamt = timespamt;
        }

        public void setCategory(String category) {
            Category = category;
        }

        public void setComments(String comments) {
            Comments = comments;
        }

        public void setUrl_Image(String url_Image) {
            Url_Image = url_Image;
        }

        public String getID() {
            return ID;
        }

        public String getUser_ID_FK() {
            return User_ID_FK;
        }

        public String getReport_longitude() {
            return Report_longitude;
        }

        public String getReport_latitude() {
            return Report_latitude;
        }

        public Timestamp getTimespamt() {
            return Timespamt;
        }

        public String getCategory() {
            return Category;
        }

        public String getComments() {
            return Comments;
        }

        public String getUrl_Image() {
            return Url_Image;
        }


    }

    public class Severity_summary_Report{
        private String ID;
        private ArrayList<String> UserIDs;
        private Integer Severity ;
        private String AvgLongitude;
        private String AvgLatittude;
        private Timestamp Firsttimestamp;
        private String Category;
        private ArrayList<String> Imgurls;
        private ArrayList<String> Comments;

        public Severity_summary_Report(String ID, ArrayList<String> userIDs, Integer severity, String avgLongitude, String avgLatittude, Timestamp firsttimestamp, String category, ArrayList<String> imgurls, ArrayList<String> comments) {
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


}
