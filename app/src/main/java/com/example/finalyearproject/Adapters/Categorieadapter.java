package com.example.finalyearproject.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Models.Categoriemodel;
import com.example.finalyearproject.R;

import java.util.ArrayList;

public class Categorieadapter extends RecyclerView.Adapter<Categorieadapter.personsViewfinder> {
    private final ArrayList<Categoriemodel> list;
    private final OnCateListener mOnCateListener;


    public Categorieadapter(ArrayList<Categoriemodel> list, OnCateListener onCateListener) {
        this.list = list;
        this.mOnCateListener = onCateListener;
    }

    @androidx.annotation.NonNull
    @Override
    public Categorieadapter.personsViewfinder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorieitem,parent,false);
        return new Categorieadapter.personsViewfinder(view, mOnCateListener);
    }



    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull Categorieadapter.personsViewfinder holder, int position) {

        holder.catname.setText(list.get(position).getCatname());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class personsViewfinder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        TextView catname;
        OnCateListener onCateListener1;
        public personsViewfinder(@NonNull View itemView, OnCateListener OnCateListener){
            super(itemView);

            catname = itemView.findViewById(R.id.catname);

            this.onCateListener1=OnCateListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onCateListener1.onCateClick(getAdapterPosition());
        }
    }

    public interface OnCateListener{
        void onCateClick(int position);
    }


}