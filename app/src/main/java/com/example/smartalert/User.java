package com.example.smartalert;

public class User{
    private String email;
    private String ID;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    private String username;
    private String password_hash;
    private String last_longitude;
    private String last_latitude;
    private String role;

    public void user(String email,String username, String password_hash, String last_longitude, String last_latitude, String role) {
        this.email = email;
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
