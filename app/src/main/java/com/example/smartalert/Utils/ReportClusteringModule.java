package com.example.smartalert.Utils;

import androidx.annotation.NonNull;

import com.example.smartalert.Report;
import com.example.smartalert.SummaryReports;
import com.example.smartalert.bridgeSummaryAndReportsArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportClusteringModule {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final Calendar calendar = Calendar.getInstance();

    private final ArrayList<SummaryReports> idk = new ArrayList<>();
    private final ArrayList<Report> hourCluster = new ArrayList<>();

    public ReportClusteringModule() {
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

    public ArrayList<Report> getReports(int hourBegin,int hourEnd){
        CollectionReference reportRef = db.collection("reports");

        //Set Hour range for backed query
        calendar.add(Calendar.HOUR_OF_DAY, -hourBegin);
        Timestamp timeStart = new Timestamp(calendar.getTime());

        calendar.add(Calendar.HOUR_OF_DAY, -hourEnd);
        Timestamp timeEnd = new Timestamp(calendar.getTime());

        //Set query
        Query query = reportRef.whereGreaterThan("timespamp",timeStart).whereLessThan("timespamp",timeEnd);

        //Get Reports
        executeQuery(new FirestoreCallback() {
            @Override
            public void onCallBack(ArrayList<Report> reports) {
                hourCluster.addAll(reports);
            }
        },query);

        return hourCluster;
    }

    private void executeQuery(FirestoreCallback firestoreCallback,Query query){
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Report> reports = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Report report = new Report(
                                documentSnapshot.getString("user_ID_FK"),
                                documentSnapshot.getString("report_longitude"),
                                documentSnapshot.getString("report_latitude"),
                                documentSnapshot.getTimestamp("timespamp"),
                                documentSnapshot.getString("category"),
                                documentSnapshot.getString("comments"),
                                documentSnapshot.getString("url_Image"));
                        reports.add(report);
                    }
                    firestoreCallback.onCallBack(reports);
                }
            }
        });
    }

    private interface FirestoreCallback {
        void onCallBack(ArrayList<Report> reports);
    }
    //Check later
    public ArrayList<SummaryReports> groupReports(ArrayList<Report> reports){
        if(!reports.isEmpty()) {
            ArrayList<ArrayList<Report>> cluster = findClusters(reports, 10);
            return iterateCategoryAndSummarize(cluster);
        }
        return new ArrayList<SummaryReports>();
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
    //pass data that is broken by category to be finally flattened and create a new report summary
    public ArrayList<SummaryReports> flattenAndCreateSummaryReports(Map<String, List<Report>> clearedByCategory){
        ArrayList<SummaryReports> actualReports= new ArrayList<>();
        for (Map.Entry<String, List<Report>> e : clearedByCategory.entrySet() ){
            ArrayList<Report> oneArrayCluster= (ArrayList<Report>) e.getValue();
            bridgeSummaryAndReportsArray bridge= getReportbridge(oneArrayCluster);
            SummaryReports newSummary = new SummaryReports(bridge.getUserID(),
                    calculateSeverity(oneArrayCluster,10),
                    findAverage(bridge.getLongitude()),
                    findAverage(bridge.getLatitude()),
                    bridge.getFirstTimestamp(),
                    e.getKey(),bridge.getImgurls()
                    ,bridge.getComments()
            );
            actualReports.add(newSummary);
        }
        return actualReports;

    }


    public int calculateSeverity(ArrayList<Report> cluster,int rangeInKilometers){
        int severity;
        int clusterSize = cluster.size();
        int clusterAndRangeScore = clusterSize/rangeInKilometers;

        if(clusterSize >1 && clusterSize<=3) return severity = 2;
        else if(clusterSize >4 && clusterSize<=6) return severity = 3;
        else if(clusterSize >8 && clusterSize<=10) return severity = 4;
        else if(clusterSize>10) return severity = 5;

        return severity = 1;
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
        ArrayList<Timestamp> timestamp = new ArrayList<Timestamp>();
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
        bridge.setFirstTimestamp(timestamp.get(0));
        bridge.setLatitude(lat);
        bridge.setLongitude(lon);
        bridge.setComments(comments);

        return bridge;
    }

    public ArrayList<Report> getHourCluster(){
        return hourCluster;
    }
    public ArrayList<SummaryReports> getIdk(){
        return idk;
    }
}
