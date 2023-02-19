package com.example.smartalert;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartalert.Utils.ReportClusteringModule;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private TextView datadataview;
    private String data;
    private final ArrayList<SummaryReports> priorityReports = new ArrayList<>();
    private final ArrayList<Report> reportArrayList = new ArrayList<>();
    private final ReportClusteringModule reportFetcher = new ReportClusteringModule();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Fetch reports from specific hours
        ArrayList<Report> firstHourReports = reportFetcher.getReports(0,1);
        ArrayList<Report> secondHourReports = reportFetcher.getReports(1,2);
        ArrayList<Report> thirdHourReports = reportFetcher.getReports(2,3);

        // Group Reports based on category and location
        priorityReports.addAll(reportFetcher.groupReports(firstHourReports));
        priorityReports.addAll(reportFetcher.groupReports(secondHourReports));
        priorityReports.addAll(reportFetcher.groupReports(thirdHourReports));


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin);

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpReportRecyclerView(priorityReports);
            }
        });
        setUpReportRecyclerView(priorityReports);
    }
    public void setUpReportRecyclerView(ArrayList<SummaryReports> allReports){
        RecyclerView recyclerView = findViewById(R.id.recycleReports);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerViewReportsAdmin adapter = new RecyclerViewReportsAdmin(AdminActivity.this,this,allReports);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}