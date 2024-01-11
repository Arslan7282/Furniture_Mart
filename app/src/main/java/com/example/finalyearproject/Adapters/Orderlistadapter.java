package com.example.finalyearproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Models.Orderlistmodel;
import com.example.finalyearproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Orderlistadapter extends RecyclerView.Adapter<Orderlistadapter.ViewHolder> {

    private final ArrayList<Orderlistmodel> list;
    private Context context;


    public Orderlistadapter(ArrayList<Orderlistmodel> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlistdesign,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.oname.setText(list.get(position).getOname());
        holder.oprice.setText(list.get(position).getOprice());
        holder.ordertime.setText(list.get(position).getOrdertime());
        Picasso.get().load(list.get(position).getOimg()).into(holder.oimg);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView oimg;
        TextView oname,oprice,ordertime;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            oimg = itemView.findViewById(R.id.oimg);
            oname = itemView.findViewById(R.id.oname);
            oprice = itemView.findViewById(R.id.oprice);
            ordertime = itemView.findViewById(R.id.ordertime);


        }


    }


}
