package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Adapters.Orderlistadapter;
import com.example.finalyearproject.Models.Orderlistmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Orders extends AppCompatActivity{

    RecyclerView orecycler;
    ArrayList<Orderlistmodel> Order_list;
    Orderlistadapter adapter;
    DatabaseReference getUserDatabaseReference;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        orecycler = findViewById(R.id.orecycler);

        mAuth=FirebaseAuth.getInstance();



        Order_list = new ArrayList<>();
        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference("placeorder").child(mAuth.getUid());
        orecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        orecycler.setHasFixedSize(true);
        adapter = new Orderlistadapter(Order_list, Orders.this);
        orecycler.setAdapter(adapter);
        getMenData();



    }

    private void getMenData() {
        try {
            getUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Order_list.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String img = ds.child("img").getValue(String.class);
                            String name = ds.child("itemName").getValue(String.class);
                            String price = ds.child("itemPrice").getValue(String.class);
                            String dprice = ds.child("timestamp").getValue(String.class);
                            String dphone = ds.child("phone").getValue(String.class);
                            String dadress = ds.child("email").getValue(String.class);
                            String demail = ds.child("phone").getValue(String.class);
                            String delivery = ds.child("deliveryFee").getValue(String.class);

                            Orderlistmodel model = new Orderlistmodel(img, name, price, dprice,dphone,dadress,demail,delivery);
                            Order_list.add(model);
                        }
//                        Toast.makeText(Orders.this, String.valueOf(Order_list.size()), Toast.LENGTH_SHORT).show();
                        adapter = new Orderlistadapter(Order_list, Orders.this);
                        orecycler.setAdapter(adapter);
                    } else {
                        Toast.makeText(Orders.this, "Empty", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle onCancelled event if needed
                }
            });
        } catch (Exception error) {
            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
        }
    }



}