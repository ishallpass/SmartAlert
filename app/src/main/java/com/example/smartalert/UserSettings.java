package com.example.smartalert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.api.core.ApiFuture;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.util.concurrent.ExecutionException;

public class UserSettings extends AppCompatActivity {
    String emaiSyntax = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    String passwordSyntax = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

     //= "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$\n";
    User userData = new User();
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore firebaseFirestore;
    Button saveButton;

    EditText username , email , password , passwordCheck ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        userData.setEmail(i.getStringExtra("email"));
        userData.setUsername(i.getStringExtra("username"));
        userData.setID(i.getStringExtra("ID"));
        setContentView(R.layout.activity_user_settings);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        saveButton = findViewById(R.id.button);

        username = findViewById(R.id.editTextTextPersonName2);
        email = findViewById(R.id.editTextTextEmailAddress);
        password =findViewById(R.id.editTextTextPassword);
        passwordCheck = findViewById(R.id.editTextTextPassword2);
        username.setText(userData.getUsername());

        email.setText(mUser.getEmail());

                
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!username.getText().toString().matches(userData.getUsername())){
                    Task<Void> editUsername = firebaseFirestore.getInstance().collection("users").document(userData.getID()).update("username",username.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "username changed", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "username error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if(email.getText().toString().matches(emaiSyntax) && !email.getText().toString().matches(mUser.getEmail())){
                    mUser.updateEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Task<Void> editUsername = firebaseFirestore.getInstance().collection("users").document(userData.getID()).update("email",email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "email changed", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "email error", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "email error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else if(!email.getText().toString().matches(userData.getEmail())){
                    Toast.makeText(getApplicationContext(), "wrong email syntax", Toast.LENGTH_LONG).show();
                }

                if(password.getText().toString().matches(passwordCheck.getText().toString()) && password.getText().toString().matches(passwordSyntax)){
                    mUser.updatePassword(password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "password changed", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "password error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else if(!password.getText().toString().matches(passwordSyntax) && passwordCheck.getText().toString().matches(passwordSyntax)){
                    Toast.makeText(getApplicationContext(), "password must contain 1 small 1 big letter one number and 1 character", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void editPersonalSettings(View v){
        View menu = findViewById(R.id.personalLayout);
        if (menu.getVisibility() == View.INVISIBLE) menu.setVisibility(View.VISIBLE);
        else menu.setVisibility(View.INVISIBLE);
    }


}