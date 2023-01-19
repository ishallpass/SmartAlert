package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    User userData = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent i = getIntent();
        userData.setEmail(i.getStringExtra("email"));
        userData.setUsername(i.getStringExtra("username"));
        userData.setID(i.getStringExtra("ID"));
        ((TextView)findViewById(R.id.usergreetText)).setText("Hi, "+userData.getUsername());
    }
    public void launchUsersettings(View v){
        Intent i = new Intent(this, UserSettings.class);
        i.putExtra("email", userData.getEmail());
        i.putExtra("username", userData.getUsername());
        i.putExtra("ID",userData.getID());
        //Pass data to other activity
        startActivity(i);
    }
}