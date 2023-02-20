package com.example.smartalert;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//ArrayList<SummaryReports> reports2BeRendered
public class RecyclerViewReportsAdmin extends RecyclerView.Adapter<RecyclerViewReportsAdmin.MyViewHolder> {
    private Activity activity;
    Context context;
    ArrayList<SummaryReports> reports2BeRendered;

    public RecyclerViewReportsAdmin(Activity activity,Context context, ArrayList<SummaryReports> reports2BeRendered){
        this.activity=activity;
        this.context=context;
        this.reports2BeRendered=reports2BeRendered;
    }
    @NonNull
    @Override
    public RecyclerViewReportsAdmin.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_view_admin,parent,false);

        return new RecyclerViewReportsAdmin.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewReportsAdmin.MyViewHolder holder, int position) {
        holder.categorytext.setText(reports2BeRendered.get(position).getCategory());
        holder.severitytext.setText(reports2BeRendered.get(position).getSeverity().toString());

        Nestedrecycler nestedrecycler = new Nestedrecycler(context,reports2BeRendered.get(position).getComments(),reports2BeRendered.get(position).getImgurls());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(nestedrecycler);

    }

    @Override
    public int getItemCount() {
        return reports2BeRendered.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView categorytext,severitytext;
        RecyclerView recyclerView;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            categorytext=itemView.findViewById(R.id.category);
            severitytext=itemView.findViewById(R.id.severity);
            recyclerView=itemView.findViewById(R.id.commentsimagesRecycler);
        }
    }
}
