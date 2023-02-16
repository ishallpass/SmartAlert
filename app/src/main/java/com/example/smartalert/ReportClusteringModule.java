package com.example.smartalert;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportClusteringModule{
    public ArrayList<Report> reportArrayList1stHourCluster = new ArrayList<>();
    public ArrayList<Report> reportArrayList2ndHourCluster = new ArrayList<>();
    public ArrayList<Report> reportArrayList3rdHourCluster = new ArrayList<>();


    public ArrayList<ArrayList<Report>>reportArrayList1stHourClusterByCategory = new ArrayList<>();


    public void setReportArrayList1stHourCluster(ArrayList<Report> reportArrayList1stHourCluster) {
        this.reportArrayList1stHourCluster = reportArrayList1stHourCluster;
    }

    public void setReportArrayList2ndHourCluster(ArrayList<Report> reportArrayList2ndHourCluster) {
        this.reportArrayList2ndHourCluster = reportArrayList2ndHourCluster;
    }

    public void setReportArrayList3rdHourCluster(ArrayList<Report> reportArrayList3rdHourCluster) {
        this.reportArrayList3rdHourCluster = reportArrayList3rdHourCluster;
    }

    public ArrayList<Report> getReportArrayList1stHourCluster() {
        return reportArrayList1stHourCluster;
    }

    public ArrayList<Report> getReportArrayList2ndHourCluster() {
        return reportArrayList2ndHourCluster;
    }

    public ArrayList<Report> getReportArrayList3rdHourCluster() {
        return reportArrayList3rdHourCluster;
    }

    public ReportClusteringModule() {
    }

    public Map<String, List<Report>> groupByCategory (ArrayList<Report> arrayOfReports){
        Map<String, List<Report>> myReportsPerCategory = arrayOfReports.stream().collect(Collectors.groupingBy(Report::getCategory));
        return myReportsPerCategory;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void groupDataModuleByGps(ArrayList<ArrayList<Report>> arrayCheckedByTime){
        ArrayList<ArrayList<ArrayList<Report>>> reportByCalendarAndGps= new ArrayList<>();

        Iterator<ArrayList<Report>> i = arrayCheckedByTime.iterator();

        while (i.hasNext()){
            ArrayList<ArrayList<Report>> groupGpsReports = new ArrayList<>();

            ArrayList<Report> reportByTime = i.next();
            Iterator<Report> k = reportByTime.iterator();
            groupGpsReports= findClusters(i.next(),10);
            reportByCalendarAndGps.add(groupGpsReports);

        }


    }

    public void getReports(Context context){

        ProgressDialog progressDialog= new ProgressDialog(context);
        progressDialog.setTitle("Fetching data by time");
        progressDialog.show();
        ArrayList<Report> reportArrayList1stHourClusterNew = new ArrayList<>();
        ArrayList<Report> reportArrayList2ndHourClusterNew = new ArrayList<>();
        ArrayList<Report> reportArrayList3rdHourClusterNew = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reportRef =  db.collection("reports");

        Query firstHourCluster =reportRef.whereGreaterThan("timespamp",removeTime(1));
        Query secondHourCluster = reportRef.whereGreaterThan("timespamp",removeTime(2));
        Query thirdHourCluster = reportRef.whereGreaterThan("timespamp",removeTime(3));

        firstHourCluster.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Report report = new Report(documentSnapshot.get("user_ID_FK").toString(),documentSnapshot.get("report_longitude").toString(),documentSnapshot.get("report_latitude").toString(), (Long) documentSnapshot.get("timespamp"),documentSnapshot.get("category").toString(),documentSnapshot.get("comments").toString(),documentSnapshot.get("url_Image").toString());
                        if(report != null){reportArrayList1stHourClusterNew.add(report);}
                        }
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();}
                }
                else{
                    Toast.makeText(context, "Data error check connection", Toast.LENGTH_LONG).show();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();}

                }
            }
        });
        secondHourCluster.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Report report = new Report(documentSnapshot.get("user_ID_FK").toString(),documentSnapshot.get("report_longitude").toString(),documentSnapshot.get("report_latitude").toString(), (Long) documentSnapshot.get("timespamp"),documentSnapshot.get("category").toString(),documentSnapshot.get("comments").toString(),documentSnapshot.get("url_Image").toString());
                        if(report != null){reportArrayList2ndHourClusterNew.add(report);}
                    }
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();}
                }
                else{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();}
                    Toast.makeText(context, "Data error check connection", Toast.LENGTH_LONG).show();

                }
            }
        });
        thirdHourCluster.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Report report = new Report(documentSnapshot.get("user_ID_FK").toString(),documentSnapshot.get("report_longitude").toString(),documentSnapshot.get("report_latitude").toString(), (Long) documentSnapshot.get("timespamp"),documentSnapshot.get("category").toString(),documentSnapshot.get("comments").toString(),documentSnapshot.get("url_Image").toString());
                        if(report != null){reportArrayList3rdHourClusterNew.add(report);}
                    }
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();}
                }
                else{
                    Toast.makeText(context, "Data error check connection", Toast.LENGTH_LONG).show();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();}
                }
            }
        });

        setReportArrayList1stHourCluster(reportArrayList1stHourClusterNew);
        setReportArrayList2ndHourCluster(reportArrayList2ndHourClusterNew);
        setReportArrayList3rdHourCluster(reportArrayList3rdHourClusterNew);
    }

    public int addTime(int time){
        return (int) (System.currentTimeMillis()+(time*3600000));
    }
    public int removeTime(int time){
        return (int) (System.currentTimeMillis()-(time*3600000));
    }

    public ArrayList<ArrayList<Report>> findClusters(ArrayList<Report> data, double distance) {
        ArrayList<ArrayList<Report>> clusters = new ArrayList<ArrayList<Report>>();
        ArrayList<Report> visited = new ArrayList<Report>();

        for (Report point : data) {
            if (visited.contains(point)) {
                continue;
            }

            ArrayList<Report> cluster = new ArrayList<Report>();
            findNeighbors(point, data, distance, cluster, visited);
            clusters.add(cluster);
        }

        return clusters;
    }

    public static void findNeighbors(Report point, ArrayList<Report> data, double distance, ArrayList<Report> cluster, ArrayList<Report> visited) {
        visited.add(point);
        cluster.add(point);

        for (Report nextPoint : data) {
            if (visited.contains(nextPoint)) {
                continue;
            }
            double lat1 = Double.parseDouble(point.getReport_latitude());
            double lon1 = Double.parseDouble(point.getReport_longitude());
            double lat2 = Double.parseDouble(nextPoint.getReport_latitude());
            double lon2 = Double.parseDouble(nextPoint.getReport_longitude());

            double d = org.apache.lucene.util.SloppyMath.haversinMeters(lat1, lon1, lat2, lon2)/1000;
            if (d <= distance) {
                findNeighbors(nextPoint, data, distance, cluster, visited);
            }
        }
    }

}
