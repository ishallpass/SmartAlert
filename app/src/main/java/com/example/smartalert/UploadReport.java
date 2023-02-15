package com.example.smartalert;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadReport {
    private Uri image;
    private Context context;
    private String UserID;
    Report report;

    public UploadReport(Uri image, Context context, String UserID,Report report) {
        this.image = image;
        this.context = context;
        this.UserID = UserID;
        this.report = report;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUserID() {
        return UserID;
    }

    public Uri getImage() {
        return image;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public void imagetoCloudAndFileReport(Context context){
        ProgressDialog  progressDialog= new ProgressDialog(context);
        progressDialog.setTitle("Please wait");
        progressDialog.show();
        String imagepath = "report_Images/"+UserID+System.currentTimeMillis();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference(imagepath);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference reportRef =  db.collection("reports").document();

        if(image != null) {
            storageRef.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(context, "image uploaded", Toast.LENGTH_SHORT).show();
                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                            new OnCompleteListener<Uri>() {

                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String fileLink = task.getResult().toString();
                                    Toast.makeText(context, "ALL is well", Toast.LENGTH_SHORT).show();
                                    report.setUrl_Image(fileLink);

                                    reportRef.set(report).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Report has been files", Toast.LENGTH_SHORT).show();
                                    }
                                    }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Report error", Toast.LENGTH_SHORT).show();
                                }
                            });
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "image upload error", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            });
        }
    });
}}}