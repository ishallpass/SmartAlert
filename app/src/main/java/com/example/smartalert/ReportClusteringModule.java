package com.example.smartalert;

import android.os.Build;

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

public class ReportClusteringModule{
    ArrayList<Report> reportArrayList1stHourCluster = new ArrayList<>();
    ArrayList<Report> reportArrayList2ndHourCluster = new ArrayList<>();
    ArrayList<Report> reportArrayList3rdHourCluster = new ArrayList<>();

    public ReportClusteringModule() {
    }

    private void groupByCategory (ArrayList<ArrayList<ArrayList<Report>>> arrayOfReports){
        ArrayList<ArrayList<ArrayList<ArrayList<Report>>>> totalFinalReportArrayList = new ArrayList<>();

        Iterator<ArrayList<ArrayList<Report>>> i = arrayOfReports.iterator();


        while (i.hasNext()){

            ArrayList<ArrayList<ArrayList<Report>>> reportsByTimeBracketGpsCategory = new ArrayList<>();
            ArrayList<ArrayList<Report>> reports= i.next();
            Iterator<ArrayList<Report>> x = reports.iterator();

            while (x.hasNext()){

                ArrayList<Report> rainCluster = new ArrayList<>(),snowCluster=new ArrayList<>(),thunderCluster=new ArrayList<>(),fireCluster=new ArrayList<>();
                ArrayList<ArrayList<Report>> reportByGpsCategory = new ArrayList<>();
                ArrayList<Report> oneReportCluster = x.next();
                Iterator<Report> y = oneReportCluster.iterator();


                while (y.hasNext()){
                    Report nextReport = y.next();
                    if(nextReport.getCategory().equals("rain")) {
                        rainCluster.add(nextReport);
                    }
                    else if(nextReport.getCategory().equals("fire")){
                        fireCluster.add(nextReport);
                    }
                    else if(nextReport.getCategory().equals("snow")){
                        snowCluster.add(nextReport);
                    }
                    else if(nextReport.getCategory().equals("thunderstorm")){
                        thunderCluster.add(nextReport);
                    }
                }
                if(rainCluster.size()>0){
                    reportByGpsCategory.add(rainCluster);
                }
                if(fireCluster.size()>0){
                    reportByGpsCategory.add(fireCluster);
                }
                if(snowCluster.size()>0){
                    reportByGpsCategory.add(snowCluster);
                }
                if(thunderCluster.size()>0){
                    reportByGpsCategory.add(thunderCluster);
                }
                reportsByTimeBracketGpsCategory.add(reportByGpsCategory);
            }
            totalFinalReportArrayList.add(reportsByTimeBracketGpsCategory);
        }
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
        groupByCategory(reportByCalendarAndGps);


    }

    public ArrayList<Report> getReports(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reportRef =  db.collection("reports");
        Query firstHourCluster =reportRef.whereGreaterThanOrEqualTo("timestamp",removeTime(1));

        firstHourCluster.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Report report = new Report(documentSnapshot.get("user_ID_FK").toString(),documentSnapshot.get("report_longitude").toString(),documentSnapshot.get("report_latitude").toString(), (Long) documentSnapshot.get("timespamp"),documentSnapshot.get("category").toString(),documentSnapshot.get("comments").toString(),documentSnapshot.get("url_Image").toString());
                        if(report != null){reportArrayList1stHourCluster.add(report);}
                    }
                }
                else{
                    //Toast.makeText(this.getApplicationContext(), "Data error check connection", Toast.LENGTH_LONG).show();
                }
            }
        });
        return reportArrayList1stHourCluster;
    }

    public long addTime(int time){
        return System.currentTimeMillis()+(time*3600000);
    }
    public long removeTime(int time){
        return System.currentTimeMillis()-(time*3600000);
    }

    public static ArrayList<ArrayList<Report>> findClusters(ArrayList<Report> data, double distance) {
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

            double d = distance(lat1, lon1, lat2, lon2);
            if (d <= distance) {
                findNeighbors(nextPoint, data, distance, cluster, visited);
            }
        }
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);
        return a;
    }

}
