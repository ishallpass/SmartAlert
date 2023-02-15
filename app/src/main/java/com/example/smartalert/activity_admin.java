package com.example.smartalert;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class activity_admin extends AppCompatActivity {
    TextView datadataview;
    String data;

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    ArrayList<Report> reportArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        datadataview = findViewById(R.id.textView3);
        ReportClusteringModule all = new ReportClusteringModule();
        reportArrayList= all.getReports();
        datadataview.append(reportArrayList.toString());
    }

    }