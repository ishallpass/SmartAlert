package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void launchUser(View v){
        Intent i = new Intent(this, UserActivity.class);
        //Pass data to other activity
        String userdata = ((EditText)findViewById(R.id.mailInput)).getText().toString();
        i.putExtra("userData",userdata);
        startActivity(i);
    }
}