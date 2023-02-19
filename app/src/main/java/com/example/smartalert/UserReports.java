package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class UserReports extends AppCompatActivity {

    private ArrayList<String> myList = new ArrayList<>();
    private RecyclerView listReports;
    private ArrayList<String> adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reports);

        myList.add("Report 1");
        myList.add("Report 2");
        myList.add("Report 3");
        myList.add("Report 4");
        myList.add("Report 5");

        RecyclerView recyclerView = findViewById(R.id.listUserReports);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}