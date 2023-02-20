package com.example.smartalert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends AppCompatActivity {
    EditText mailIinput,passwordInput;
    TextView someText ;
    Button loginBtn;
    String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        someText = findViewById(R.id.titleText);
        loginBtn =(Button) findViewById(R.id.loginBtn);
        mailIinput = findViewById(R.id.mailInput);
        passwordInput = findViewById(R.id.passwordInput);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticate();
            }
        });

    }
    private void authenticate() {
        String email = mailIinput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.matches(regexPattern)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                @Override
                public void onSuccess(AuthResult authResult) {
                    //retrieval of 1st row with the email we querried this is not in a function cause asych
                    final String[] role = new String[1];
                     User myUser = new User();
                    Query documentReference = firebaseFirestore.getInstance().collection("users").whereEqualTo("email",email);
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                    role[0] = documentSnapshot.get("role").toString();
                                    myUser.setEmail(documentSnapshot.get("email").toString());
                                    myUser.setUsername(documentSnapshot.get("username").toString());
                                    myUser.setRole(documentSnapshot.get("role").toString());
                                    myUser.setLast_latitude(documentSnapshot.get("last_latitude").toString());
                                    myUser.setLast_longitude(documentSnapshot.get("last_longitude").toString());
                                    myUser.setID(documentSnapshot.getId());
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "no role", Toast.LENGTH_LONG).show();
                            }
                            if(role[0].matches("user")){
                                launchUser(myUser);
                            }
                            else if(role[0].matches("admin")){
                                launchAdmin(myUser);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Error on role"+role[0], Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "wrong email and or password", Toast.LENGTH_LONG).show();
                }
            });

            } else {
                Toast.makeText(getApplicationContext(), "not correct email syntax", Toast.LENGTH_LONG).show();
            }
        ;
        }


        public void launchUser(User userData){
            Intent i = new Intent(this, UserActivity.class);
            //Pass data to other activity
            i.putExtra("username",userData.getUsername());
            i.putExtra("email", userData.getEmail());
            i.putExtra("role", userData.getRole());
            i.putExtra("latitude", userData.getLast_latitude());
            i.putExtra("longitude",userData.getLast_longitude());
            i.putExtra("ID",userData.getID());
            startActivity(i);
        }
        public void launchAdmin(User adminData){
            Intent i = new Intent(this, activity_admin.class);
            //Pass data to other activity
            i.putExtra("username",adminData.getUsername());
            i.putExtra("email", adminData.getEmail());
            i.putExtra("role", adminData.getRole());
            i.putExtra("ID",adminData.getID());
            startActivity(i);
        }
}
