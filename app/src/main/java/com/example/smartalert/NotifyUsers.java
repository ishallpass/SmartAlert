package com.example.smartalert;

public class NotifyUsers {
    public Float Longitude;
    public Float Lattitude;

    public NotifyUsers(Float longitude, Float lattitude) {
        Longitude = longitude;
        Lattitude = lattitude;
    }

    public void findUsersWithinRange(Float longitude , Float lattitude,Integer rangInKilometters){

    }

    public Float getLongitude() {
        return Longitude;
    }

    public Float getLattitude() {
        return Lattitude;
    }

    public void setLongitude(Float longitude) {
        Longitude = longitude;
    }

    public void setLattitude(Float lattitude) {
        Lattitude = lattitude;
    }

}
