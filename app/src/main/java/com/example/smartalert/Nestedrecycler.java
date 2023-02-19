package com.example.smartalert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Nestedrecycler extends RecyclerView.Adapter<Nestedrecycler.MyViewHolder> {

    Context context;
    ArrayList<String> comments;
    ArrayList<String> imgUrls;

    public Nestedrecycler(Context context, ArrayList<String>comments,ArrayList<String>imgUrls) {
        this.context = context;
        this.comments = comments;
        this.imgUrls=imgUrls;
    }

    @NonNull
    @Override
    public Nestedrecycler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.nestedrecycler,parent,false);

        return new Nestedrecycler.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Nestedrecycler.MyViewHolder holder, int position) {
        holder.commentext.setText(comments.get(position));
        Picasso.get().load(imgUrls.get(position).toString()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imgUrls.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView commentext;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            commentext=itemView.findViewById(R.id.commentsrecycle);
            imageView=itemView.findViewById(R.id.imagerecycle);
        }
    }


}
