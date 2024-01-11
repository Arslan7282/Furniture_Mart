package com.example.finalyearproject.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Addcart;
import com.example.finalyearproject.Firstpage;
import com.example.finalyearproject.Models.Cartlistmodel;
import com.example.finalyearproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Cartlistadapter extends RecyclerView.Adapter<Cartlistadapter.ViewHolder> {
    private final ArrayList<Cartlistmodel> list;
    private Context context;
    private DatabaseReference getUserDatabaseReference;
    private FirebaseAuth mAuth;
    private String userid;
    private TextView totalAmountTextView;
    AlertDialog alertDialog;

    public Cartlistadapter(ArrayList<Cartlistmodel> list, Context context, TextView totalAmountTextView) {
        this.list = list;
        this.context = context;
        this.totalAmountTextView = totalAmountTextView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlistdesign, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(list.get(position).getCartimg()).into(holder.cartimg);
        holder.cartname.setText(list.get(position).getCartname());
        holder.cartdisc.setText(list.get(position).getCartdesc());
        holder.cartprice.setText(list.get(position).getCartprice());
        holder.carttime.setText(list.get(position).getCarttime());

        holder.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void dialog() {
                try {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Are You Sure!").setMessage("You want to Delete this item");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    try {
                                        alertDialog.dismiss();
                                        String id = list.get(position).getOrderId();
                                        mAuth = FirebaseAuth.getInstance();
                                        userid = mAuth.getUid();
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("cart").child(userid).child(id);
                                        reference.removeValue();
                                        list.remove(position);
                                        notifyItemRemoved(position);
                                        // Notify the activity to update total amount
                                        if (context instanceof Addcart) {
                                            ((Addcart) context).updateTotalAmount();
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    alertDialogBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cartimg, cancle;
        TextView cartname, cartdisc, cartprice, carttime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartimg = itemView.findViewById(R.id.cartimg);
            cartname = itemView.findViewById(R.id.cartname);
            cartdisc = itemView.findViewById(R.id.cartdisc);
            cartprice = itemView.findViewById(R.id.cartprice);
            carttime = itemView.findViewById(R.id.carttime);
            cancle = itemView.findViewById(R.id.cancle);
        }
    }
}
