package com.example.smartalert;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class UserActivity extends AppCompatActivity implements OnMapReadyCallback , AdapterView.OnItemSelectedListener {

    public String locale;

    User userData = new User();
    MapView yourLocation;
    Button reportBtn;
    Button submitReport;
    public Criteria criteria;
    TextView helloText;
    Spinner spinner;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private final int REQUEST_LOCATION_PERMISSION = 100;
    alertRecycle adapter2 ;

    String longitude,latitude,category=null;

    EditText comments;
    Uri imagePath;
    ImageView gallleryPick;
    RecyclerView recyclerView;
    ArrayList<String> message = new ArrayList<>();


    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        requestLocationPermission();
        reportBtn = findViewById(R.id.reportbtn);
        submitReport = findViewById(R.id.Report);
        Intent i = getIntent();


        userData.setEmail(i.getStringExtra("email"));
        userData.setUsername(i.getStringExtra("username"));
        userData.setID(i.getStringExtra("ID"));
        userData.setLast_latitude(i.getStringExtra("latitude"));
        userData.setLast_longitude(i.getStringExtra("longitude"));
        helloText = findViewById(R.id.usergreetText);
        helloText.setText("Hi, " + userData.getUsername());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        comments = findViewById(R.id.comments);

        spinner = findViewById(R.id.Categoryspinner);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        gallleryPick = findViewById(R.id.galleryPickView);

        getLocation();
        checkForAlert(this);


        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                newReport();

                //yourLocation.

            }
        });
        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendReport(view);
            }
        });

        gallleryPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                someActivityResultLauncher.launch(intent);


            }
        });
    }

    public void sendReport(View v){
        Report report = new Report(userData.getID(),longitude,latitude,System.currentTimeMillis(),category,comments.getText().toString(),null);

        UploadReport currentImage = new UploadReport(imagePath,UserActivity.this,userData.getID(),report);

        currentImage.imagetoCloudAndFileReport(UserActivity.this);

    }
    public void checkForAlert(Activity activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final GeoLocation center = new GeoLocation(Float.valueOf(userData.getLast_latitude()), Float.valueOf(userData.getLast_longitude()));
        final double radiusInM = 10 * 1000;
        List<GeoQueryBounds> bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM);
        final List<Task<QuerySnapshot>> tasks = new ArrayList<>();
        for (GeoQueryBounds b : bounds) {
            Query q = db.collection("Alerts")
                    .orderBy("geohash")
                    .startAt(b.startHash)
                    .endAt(b.endHash);

            tasks.add(q.get());
        }

        Tasks.whenAllComplete(tasks)
                .addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Task<?>>> t) {
                        List<DocumentSnapshot> matchingDocs = new ArrayList<>();

                        for (Task<QuerySnapshot> task : tasks) {
                            QuerySnapshot snap = task.getResult();
                            for (DocumentSnapshot doc : snap.getDocuments()) {
                                double lat =  doc.getDouble("latitude");
                                double lng =  doc.getDouble("longitude");


                                GeoLocation docLocation = new GeoLocation(lat, lng);
                                double distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center);
                                if (distanceInM <= radiusInM) {
                                    matchingDocs.add(doc);
                                    message.add(doc.getString("body"));

                                }
                            }
                        };
                        recyclerView = findViewById(R.id.Alerts);
                        adapter2 = new alertRecycle(message, activity);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        recyclerView.setAdapter(adapter2);
                    }
                });
    }

    public void launchUsersettings(View v) {
        Intent i = new Intent(this, UserSettings.class);
        i.putExtra("email", userData.getEmail());
        i.putExtra("username", userData.getUsername());
        i.putExtra("ID", userData.getID());
        //Pass data to other activity
        startActivity(i);
    }

    public void newReport() {
        View report = findViewById(R.id.Reportlayout);
        if (report.getVisibility() == View.INVISIBLE) report.setVisibility(View.VISIBLE);
        else report.setVisibility(View.INVISIBLE);
        report.bringToFront();

    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location2) {
                    if (location2 != null) {
                        Geocoder geocoder = new Geocoder(UserActivity.this, Locale.getDefault());
                        List<Address> location;
                        try {
                            location = geocoder.getFromLocation(location2.getLatitude(), location2.getLongitude(), 1);
                            longitude = String.valueOf(location.get(0).getLongitude());
                            latitude = String.valueOf(location.get(0).getLatitude());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(UserActivity.this, "please turn on your gps error", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserActivity.this, "get location failed", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        category = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, category, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri image = data.getData();
                        imagePath = image;
                        gallleryPick.setImageURI(image);

                    }
                }
            });



    public ArrayList<Float> latlonmax(float lat,float lng,int radius) {
        ArrayList<Float> maxandmin=new ArrayList<>();
        int earth = 6371 ;// earth radius in miles
        float maxLat = (float) (((lat + Math.PI / 2) * 180) / Math.PI);
        float minLat = (float) (((lat - Math.PI / 2) * 180) / Math.PI);
        float maxLng = (float) (((lng + (Math.PI * radius) / earth) * 180) / Math.PI);
        float minLng = (float) (((lng - (Math.PI * radius) / earth) * 180) / Math.PI);
        maxandmin.add(maxLat);
        maxandmin.add(minLat);
        maxandmin.add(maxLng);
        maxandmin.add(minLng);
        return maxandmin;
  }
}
