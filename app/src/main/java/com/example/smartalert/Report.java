package com.example.smartalert;

public class Report {
    private String User_ID_FK;
    private String Report_longitude;
    private String Report_latitude;
    private String Timespamt;
    private String Category;
    private String Comments;
    private String Url_Image;

    public Report(String user_ID_FK, String report_longitude, String report_latitude, String timespamt, String category, String comments, String url_Image) {
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

    public void setTimespamt(String timespamt) {
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

    public String getUser_ID_FK() {
        return User_ID_FK;
    }

    public String getReport_longitude() {
        return Report_longitude;
    }

    public String getReport_latitude() {
        return Report_latitude;
    }

    public String getTimespamp() {
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
