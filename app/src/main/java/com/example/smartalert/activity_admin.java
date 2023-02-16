package com.example.smartalert;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        Button button = findViewById(R.id.button2);
        datadataview = findViewById(R.id.textView3);
        ReportClusteringModule all = new ReportClusteringModule();

        all.getReports(this);
        all.groupByCategory(all.reportArrayList1stHourCluster);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datadataview.append(all.groupByCategory(all.reportArrayList1stHourCluster).toString());
            }
        });
    }

    }