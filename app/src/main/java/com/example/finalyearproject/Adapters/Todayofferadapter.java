package com.example.finalyearproject.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Models.Todayoffermodel;
import com.example.finalyearproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Todayofferadapter extends RecyclerView.Adapter<Todayofferadapter.personsViewfinder> {
    private final ArrayList<Todayoffermodel> list;
    private final OnTodayListener mOnNoteListener;


    public Todayofferadapter(ArrayList<Todayoffermodel> list,OnTodayListener onTodayListener) {
        this.list = list;
        this.mOnNoteListener = onTodayListener;
    }

    @androidx.annotation.NonNull
    @Override
    public Todayofferadapter.personsViewfinder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todayoffer,parent,false);
        return new Todayofferadapter.personsViewfinder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull Todayofferadapter.personsViewfinder holder, int position) {

        holder.desc.setText(list.get(position).getDesc());
        Picasso.get().load(list.get(position).getImg()).into(holder.img);
        holder.name.setText(list.get(position).getName());
        holder.dprice.setText(list.get(position).getDprice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class personsViewfinder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        ImageView img;
        TextView name,dprice,desc;
        OnTodayListener onNoteListener2;
        public personsViewfinder(@NonNull View itemView, OnTodayListener OnTodayListener){
            super(itemView);

            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            dprice = itemView.findViewById(R.id.dprice);
            desc = itemView.findViewById(R.id.desc);

            this.onNoteListener2=OnTodayListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            onNoteListener2.onTodayClick(getAdapterPosition());
        }
    }

    public interface OnTodayListener{
        void onTodayClick(int position);
    }
}