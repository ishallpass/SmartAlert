package com.example.smartalert;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class activity_admin extends AppCompatActivity {
    TextView datadataview;
    String data;
    ArrayList<SummaryReports> allReportsArray=new ArrayList<>();
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ArrayList<Report> reportArrayList = new ArrayList<>();
    @Override
    protected void attachBaseContext(Context newBase){
        if(LanguageConfig.localeGr) {
            Context context = LanguageConfig.changeLanguage(newBase,"el");
            super.attachBaseContext(context);
        }else{
            Context context = LanguageConfig.changeLanguage(newBase,"en");
            super.attachBaseContext(context);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ReportClusteringModule allReports = new ReportClusteringModule();
        allReports.getReports(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin);

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpReportReciclerView(allReports);
            }
        });

        setUpReportReciclerView(allReports);



    }
    public void setUpReportReciclerView(ReportClusteringModule allReports){
        RecyclerView recyclerView = findViewById(R.id.recycleReports);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerViewReportsAdmin adapter = new RecyclerViewReportsAdmin(activity_admin.this,this,allReports.idk);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    }