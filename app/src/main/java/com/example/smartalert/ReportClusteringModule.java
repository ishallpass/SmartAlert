package com.example.smartalert;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportClusteringModule{
    public ArrayList<Report> reportArrayList1stHourCluster = new ArrayList<>();
    public ArrayList<Report> reportArrayList2ndHourCluster = new ArrayList<>();
    public ArrayList<Report> reportArrayList3rdHourCluster = new ArrayList<>();

    public ArrayList<SummaryReports> idk = new ArrayList<>();

    public ReportClusteringModule() {
    }

    public Map<String, List<Report>> groupByCategory (ArrayList<Report> arrayOfReports){
        Map<String, List<Report>> myReportsPerCategory = arrayOfReports.stream().collect(Collectors.groupingBy(Report::getCategory));
        return myReportsPerCategory;
    }
    public ArrayList<SummaryReports> iterateCategoryAndSummarize(ArrayList<ArrayList<Report>> arraylist){
        ArrayList<SummaryReports> totalSummaryReports = new ArrayList<>();
        for(ArrayList<Report> reportArrayList : arraylist){
            Map<String, List<Report>> groupedByCatArray = groupByCategory(reportArrayList);
            ArrayList<SummaryReports> currentSummaryReports = flattenAndCreateSummaryReports(groupedByCatArray);
            totalSummaryReports.addAll(currentSummaryReports);
        }
        return totalSummaryReports;

    }

    public void getReports(Context context){
        ArrayList<SummaryReports> allReports = new ArrayList<>();
        ArrayList<Report> reportArrayList3rdHourClusterNew = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reportRef =  db.collection("reports");

        Query firstHourCluster =reportRef.whereGreaterThan("timespamp",removeTime(1));
        Query secondHourCluster = reportRef.whereGreaterThan("timespamp",removeTime(2)).whereLessThan("timespamp",removeTime(1));
        Query thirdHourCluster = reportRef.whereGreaterThan("timespamp",removeTime(3)).whereLessThan("timespamp",removeTime(2));
        firstHourCluster.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Report> reportArrayList1stHourClusterNew = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Report report = new Report(documentSnapshot.get("user_ID_FK").toString(), documentSnapshot.get("report_longitude").toString(), documentSnapshot.get("report_latitude").toString(), (Long) documentSnapshot.get("timespamp"), documentSnapshot.get("category").toString(), documentSnapshot.get("comments").toString(), documentSnapshot.get("url_Image").toString());
                        reportArrayList1stHourClusterNew.add(report);
                        Log.d(TAG, "o aetos");

                    }
                    setReportArrayList1stHourCluster(reportArrayList1stHourClusterNew);
                    if(getReportArrayList1stHourCluster().size()>0) {
                        ArrayList<ArrayList<Report>> clustersOf1h = findClusters(getReportArrayList1stHourCluster(), 10);
                        idk.addAll( iterateCategoryAndSummarize(clustersOf1h));
                    }
                } else {

                    Toast.makeText(context, "Data error check connection", Toast.LENGTH_LONG).show();

                }
            }
        });
        secondHourCluster.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Report> reportArrayList2ndHourClusterNew = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Report report = new Report(documentSnapshot.get("user_ID_FK").toString(), documentSnapshot.get("report_longitude").toString(), documentSnapshot.get("report_latitude").toString(), (Long) documentSnapshot.get("timespamp"), documentSnapshot.get("category").toString(), documentSnapshot.get("comments").toString(), documentSnapshot.get("url_Image").toString());
                        reportArrayList2ndHourClusterNew.add(report);
                        Log.d(TAG, "o aetos");
                    }

                    setReportArrayList2ndHourCluster(reportArrayList2ndHourClusterNew);
                    if (getReportArrayList2ndHourCluster().size()>0) {
                        ArrayList<ArrayList<Report>> clustersOf2h = findClusters(getReportArrayList2ndHourCluster(), 10);
                        ArrayList<SummaryReports> summaryOf2h = iterateCategoryAndSummarize(clustersOf2h);
                        idk.addAll(summaryOf2h);
                    }

                    Log.d(TAG, "o aetos o re enas aetos");
                } else {

                    Toast.makeText(context, "Data error check connection", Toast.LENGTH_LONG).show();

                }
            }
        });
        thirdHourCluster.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Report report = new Report(documentSnapshot.get("user_ID_FK").toString(), documentSnapshot.get("report_longitude").toString(), documentSnapshot.get("report_latitude").toString(), (Long) documentSnapshot.get("timespamp"), documentSnapshot.get("category").toString(), documentSnapshot.get("comments").toString(), documentSnapshot.get("url_Image").toString());
                        reportArrayList3rdHourClusterNew.add(report);
                        Log.d(TAG, "o aetos");

                    }

                    setReportArrayList3rdHourCluster(reportArrayList3rdHourClusterNew);
                    if(getReportArrayList3rdHourCluster().size()>0) {
                        ArrayList<ArrayList<Report>> clustersOf3h = findClusters(getReportArrayList3rdHourCluster(), 10);
                        ArrayList<SummaryReports> summaryOf3h = iterateCategoryAndSummarize(clustersOf3h);
                        idk.addAll(summaryOf3h);
                    }
                    Log.d(TAG, "size"+getReportArrayList3rdHourCluster().size());
                } else {

                    Toast.makeText(context, "Data error check connection", Toast.LENGTH_LONG).show();

                }
            }



        /*Collections.sort(allReports, new Comparator<SummaryReports>() {
            @Override
            public int compare(SummaryReports second, SummaryReports first) {
                return Integer.compare(first.getSeverity(),second.getSeverity());
            }

        });*/
        });
    }
    public int addTime(int time){
        return (int) (System.currentTimeMillis()+(time*3600000));
    }
    public long removeTime(int time){
        return  (System.currentTimeMillis()-(time*3600000));
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
    //pass data that is broken by category to be finaly flattened and create a new report summary
    public ArrayList<SummaryReports> flattenAndCreateSummaryReports(Map<String, List<Report>> clearedByCategory){
        ArrayList<SummaryReports> actualReports= new ArrayList<>();
        for (Map.Entry<String, List<Report>> e : clearedByCategory.entrySet() ){
            ArrayList<Report> oneArrayCluster= (ArrayList<Report>) e.getValue();
            bridgeSummaryAndReportsArray bridge= getReportbridge(oneArrayCluster);
            SummaryReports newSummary = new SummaryReports(bridge.getUserID(),calculateSeverity(oneArrayCluster,10),findAverage(bridge.getLongitude()),findAverage(bridge.getLatitude()),bridge.getFirstTimestamp(),e.getKey(),bridge.getImgurls(),bridge.getComments());
            actualReports.add(newSummary);
        }
        return actualReports;

    }


    public int calculateSeverity(ArrayList<Report> cluster,int rangeInKilometers){
        int severity=1;
        int clusterSize =cluster.size();
        int clusterAndRangeScore = clusterSize/rangeInKilometers;
        if(clusterAndRangeScore>=0 && clusterAndRangeScore<=1){
            severity=1;
        }
        else if(clusterSize >1 && clusterSize<=3){
            severity=2;
        }
        else if(clusterSize >4 && clusterSize<=6){
            severity=3;
        }
        else if(clusterSize >8 && clusterSize<=10){
            severity=4;
        }
        else if(clusterSize>10){
            severity=5;
        }
        return severity;

    }

    public float findAverage(ArrayList<Float> data){
        float sum=0;
        for (Float fl : data){
            sum+=fl;
        }
        float avg=sum/data.size();

        return avg;
    }

    public bridgeSummaryAndReportsArray getReportbridge(ArrayList<Report> arrayOfReports){

        bridgeSummaryAndReportsArray bridge = new bridgeSummaryAndReportsArray();
        ArrayList<String> imgUrls=new ArrayList<>();
        ArrayList<String> comments=new ArrayList<>();
        ArrayList<String> userID=new ArrayList<>();
        ArrayList<Long> timestamp = new ArrayList<Long>();
        ArrayList<Float> lon= new ArrayList<>(),lat = new ArrayList<>();

        for(Report report : arrayOfReports){
            comments.add(report.getComments());
            imgUrls.add(report.getUrl_Image());
            userID.add(report.getUser_ID_FK());
            timestamp.add(report.getTimespamp());
            lat.add(Float.valueOf(report.getReport_latitude()));
            lon.add(Float.valueOf(report.getReport_longitude()));
        }

        //bridge.setComments(comments);
        bridge.setUserID(userID);
        bridge.setImgurls(imgUrls);
        bridge.setLastTimestamp(timestamp.stream().max(Long::compare).get());
        bridge.setFirstTimestamp(timestamp.stream().min(Long::compare).get());
        bridge.setLatitude(lat);
        bridge.setLongitude(lon);
        bridge.setComments(comments);

        return bridge;

    }
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


}
