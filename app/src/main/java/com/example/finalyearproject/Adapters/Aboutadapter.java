package com.example.finalyearproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Models.Aboutmodel;
import com.example.finalyearproject.R;

import java.util.List;

public class Aboutadapter extends RecyclerView.Adapter<Aboutadapter.ViewHolder> {

    private java.util.List<Aboutmodel> List;
    private Context context;

    public Aboutadapter(List<Aboutmodel> List, Context context) {
        this.List = List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aboutlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.appinfo.setText(List.get(position).getAppinfo());
        holder.developer.setText(List.get(position).getDeveloper());
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView appinfo,developer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appinfo = itemView.findViewById(R.id.appinfo);
            developer = itemView.findViewById(R.id.developer);



        }
    }
}
