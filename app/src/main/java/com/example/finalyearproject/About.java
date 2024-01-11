package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.finalyearproject.Adapters.Aboutadapter;
import com.example.finalyearproject.Models.Aboutmodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sn.lib.NestedProgress;

import java.util.ArrayList;

public class About extends AppCompatActivity {

    DatabaseReference getUserDatabaseReference;
    TextView appinfo;
    RecyclerView inforecycler;
    Aboutadapter adapter;
    ArrayList<Aboutmodel> About_list;
    LottieAnimationView progressbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setTitle("About");

        appinfo = findViewById(R.id.appinfo);
        progressbar = findViewById(R.id.progressbar);
        inforecycler = findViewById(R.id.inforecycler);

        progressbar.setVisibility(View.VISIBLE);
        About_list = new ArrayList<>();
        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference("about");
        inforecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        inforecycler.setHasFixedSize(true);
        getMenData();




    }

    private void getMenData() {
        try {
            getUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        About_list.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String appinfo = ds.child("appinfo").getValue(String.class);
                            String developer = ds.child("developer").getValue(String.class);
                            Aboutmodel model = new Aboutmodel(appinfo,developer);
                            About_list.add(model);
                        }
                        adapter = new Aboutadapter(About_list, About.this);
                        inforecycler.setAdapter(adapter);
                    }
                    else {
                        Toast.makeText(About.this, "Empty", Toast.LENGTH_SHORT).show();
                    }
                    progressbar.setVisibility(View.GONE);
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