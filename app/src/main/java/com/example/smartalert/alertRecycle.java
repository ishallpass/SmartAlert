package com.example.smartalert;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class alertRecycle extends RecyclerView.Adapter<alertRecycle.MyViewHolder> {
    ArrayList<String> Alert;
    private Activity activity;

    public alertRecycle(ArrayList<String> Alert, Activity activity) {
        this.Alert = Alert;
        this.activity = activity;
    }


    @NonNull
    @Override
    public alertRecycle.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.alertrecycler,parent,false);

        return new alertRecycle.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.AlertText.setText(Alert.get(position));
    }

    @Override
    public int getItemCount() {return Alert.size();}
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView AlertText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            AlertText =itemView.findViewById(R.id.Title);
        }
    }
}
