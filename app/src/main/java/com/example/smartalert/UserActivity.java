package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent i = getIntent();
        User userData = new User();
        userData.setUsername(i.getStringExtra("email"));
        ((TextView)findViewById(R.id.usergreetText)).setText("Hi, "+userData.getUsername());
    }
    public void launchUsersettings(View v){
        Intent i = new Intent(this, UserSettings.class);
        //Pass data to other activity
        startActivity(i);
    }
}