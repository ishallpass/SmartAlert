package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class UserSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
    }
    public void editPersonalSettings(View v){
        View menu = findViewById(R.id.personalLayout);
        if (menu.getVisibility() == View.INVISIBLE) menu.setVisibility(View.VISIBLE);
        else menu.setVisibility(View.INVISIBLE);
    }
}