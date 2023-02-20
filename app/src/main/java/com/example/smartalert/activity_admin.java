package com.example.smartalert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class activity_admin extends AppCompatActivity implements RecyclerViewInterface{
    User userData = new User();

    TextView datadataview;
    String data;
    Button refreshButton;
    ImageView settingsButton;
    ArrayList<SummaryReports> allReportsArray=new ArrayList<>();
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ArrayList<Report> reportArrayList = new ArrayList<>();
    ReportClusteringModule allReports = new ReportClusteringModule();
    RecyclerViewReportsAdmin adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        allReports.getReports(this);

        super.onCreate(savedInstanceState);
        Intent i = getIntent();

        userData.setEmail(i.getStringExtra("email"));
        userData.setUsername(i.getStringExtra("username"));
        userData.setID(i.getStringExtra("ID"));

        setContentView(R.layout.activity_admin);

        refreshButton = findViewById(R.id.button2);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpReportReciclerView(allReports);
            }
        });

        settingsButton = findViewById(R.id.settingsButton2);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchUsersettings2();
            }
        });

        setUpReportReciclerView(allReports);



    }
    public void setUpReportReciclerView(ReportClusteringModule allReports){
        RecyclerView recyclerView = findViewById(R.id.recycleReports);
        adapter = new RecyclerViewReportsAdmin(this, activity_admin.this,this,allReports.idk);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void launchUsersettings2() {
        Intent i = new Intent(this, UserSettings.class);
        i.putExtra("email", userData.getEmail());
        i.putExtra("username", userData.getUsername());
        i.putExtra("ID", userData.getID());
        //Pass data to other activity
        startActivity(i);
    }

    @Override
    public void onDenyClick(int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reportRef =  db.collection("reports");
        for(String id: allReports.idk.get(position).getReportIDs()) {
            reportRef.document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "report deleted", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
        allReports.idk.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onAcceptClick(int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reportRef =  db.collection("Alerts");
        Map<String, Object> Alert = new HashMap<>();

        String hash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(allReports.idk.get(position).getAvgLatittude(), allReports.idk.get(position).getAvgLongitude()));

        Alert.put("body", "Natural disasters of Type : "+allReports.idk.get(position).getCategory()+" reported in your area please be aware!!!");
        Alert.put("longitude",allReports.idk.get(position).getAvgLongitude());
        Alert.put("latitude",allReports.idk.get(position).getAvgLatittude());
        Alert.put("geohash",hash);
        reportRef.add(Alert).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(getApplicationContext(), "Alert sent", Toast.LENGTH_LONG).show();
            }
        });
        for(String id: allReports.idk.get(position).getReportIDs()) {
            reportRef.document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "report deleted", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }

        allReports.idk.remove(position);
        adapter.notifyItemRemoved(position);
    }
}