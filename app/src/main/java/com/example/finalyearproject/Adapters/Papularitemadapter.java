package com.example.finalyearproject.Adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Models.Papularitemmodel;
import com.example.finalyearproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Papularitemadapter extends RecyclerView.Adapter<Papularitemadapter.personsViewfinder> {
    private final ArrayList<Papularitemmodel> list;
    private final OnNoteListener mOnNoteListener;


    public Papularitemadapter(ArrayList<Papularitemmodel> list,OnNoteListener onNoteListener) {
        this.list = list;
        this.mOnNoteListener = onNoteListener;
    }

    @androidx.annotation.NonNull
    @Override
    public Papularitemadapter.personsViewfinder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.papularitem,parent,false);
        return new Papularitemadapter.personsViewfinder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull Papularitemadapter.personsViewfinder holder, int position) {

        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice());
        holder.dprice.setText(list.get(position).getDprice());
        holder.fee.setText(list.get(position).getFee());
        Picasso.get().load(list.get(position).getImg()).into(holder.img);
        holder.specifi1.setText(list.get(position).getSpecifi1());
        holder.specifi2.setText(list.get(position).getSpecifi2());
        holder.specifi3.setText(list.get(position).getSpecifi3());
        holder.specifi4.setText(list.get(position).getSpecifi4());
        holder.specifi5.setText(list.get(position).getSpecifi5());
        holder.specifi6.setText(list.get(position).getSpecifi6());
        holder.service1.setText(list.get(position).getService1());
        holder.service2.setText(list.get(position).getService2());
        holder.delivery.setText(list.get(position).getDelivery());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class personsViewfinder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        ImageView img;
        TextView name,price,dprice,fee,specifi1,specifi2,specifi3,specifi4,specifi5,specifi6,service1,service2,delivery;
        OnNoteListener onNoteListener2;
        public personsViewfinder(@NonNull View itemView, OnNoteListener onNoteListener){
            super(itemView);

            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            dprice = itemView.findViewById(R.id.dprice);
            fee = itemView.findViewById(R.id.fee);
            specifi1 = itemView.findViewById(R.id.specifi1);
            specifi2 = itemView.findViewById(R.id.specifi2);
            specifi3 = itemView.findViewById(R.id.specifi3);
            specifi4 = itemView.findViewById(R.id.specifi4);
            specifi5 = itemView.findViewById(R.id.specifi5);
            specifi6 = itemView.findViewById(R.id.specifi6);
            service1 = itemView.findViewById(R.id.service1);
            service2 = itemView.findViewById(R.id.service2);
            delivery = itemView.findViewById(R.id.delivery);

            this.onNoteListener2=onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            onNoteListener2.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);

        void onCreates(Bundle savedInstanceState);
    }
}