package com.example.smartalert;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DOAreport {
    DatabaseReference databaseRefernce;

    public DOAreport() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseRefernce = db.getReference();
    }

}
