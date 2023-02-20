package com.example.smartalert;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class Activity_generatedata extends AppCompatActivity {
    ArrayList<String> rawData=new ArrayList<>();

    TextView rain,snow,thunderstorm,fire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generatedata);
        fire=findViewById(R.id.firecounttext);
        rain=findViewById(R.id.raincounttext);
        snow=findViewById(R.id.snowcounttext);
        thunderstorm=findViewById(R.id.snowcounttext2);
        getnumbers();

    }
    public void getnumbers(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reportRef =  db.collection("Alerts");
        reportRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        rawData.add(documentSnapshot.getString("type"));
                    }
                }
                if(Collections.frequency(rawData,"fire")>0) fire.setText(String.valueOf(Collections.frequency(rawData,"fire")));
                if(Collections.frequency(rawData,"snow")>0) snow.setText(String.valueOf(Collections.frequency(rawData,"snow")));
                if(Collections.frequency(rawData,"rain")>0) rain.setText(String.valueOf(Collections.frequency(rawData,"rain")));
                if(Collections.frequency(rawData,"thunderstorm")>0) thunderstorm.setText(String.valueOf(Collections.frequency(rawData,"thunderstorm")));

            }
        });
    }
}
