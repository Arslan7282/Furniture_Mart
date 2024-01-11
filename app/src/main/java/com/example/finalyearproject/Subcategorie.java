package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.finalyearproject.Adapters.Papularitemadapter;
import com.example.finalyearproject.Models.Cartlistmodel;
import com.example.finalyearproject.Models.Papularitemmodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Subcategorie extends AppCompatActivity implements Papularitemadapter.OnNoteListener{

    TextView specifi1,specifi2,specifi3,specifi4,specifi5,specifi6,price;
    LottieAnimationView progressbar;
    DatabaseReference getUserDatabaseReference;
    RecyclerView subcategorirecycler;
    ArrayList<Papularitemmodel> Subcategori_list;
    Papularitemadapter adapter;
    String subcategoriid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategorie);

        progressbar = findViewById(R.id.progressbar);
        price = findViewById(R.id.price);
        specifi1 = findViewById(R.id.specifi1);
        specifi2 = findViewById(R.id.specifi2);
        specifi3 = findViewById(R.id.specifi3);
        specifi4 = findViewById(R.id.specifi4);
        specifi5 = findViewById(R.id.specifi5);
        specifi6 = findViewById(R.id.specifi6);
        subcategorirecycler = findViewById(R.id.subcategorirecycler);

        Intent intent = getIntent();
        subcategoriid = intent.getStringExtra("id");

//        Toast.makeText(this, ""+subcategoriid, Toast.LENGTH_SHORT).show();

        //recycler for papular items view
        Subcategori_list = new ArrayList<>();
        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference("papularitem");
        subcategorirecycler.setLayoutManager(new GridLayoutManager(this, 2));
        subcategorirecycler.setHasFixedSize(true);
        getSubcategoriData();
        //recycler for papular items view
    }

        // Papular item recycler
        private void getSubcategoriData() {
            try {
                getUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Subcategori_list.clear();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String cid = ds.child("catid").getValue(String.class);
                                if (cid != null && cid.equals(subcategoriid)) {
                                    String img = ds.child("img").getValue(String.class);
                                    String name = ds.child("name").getValue(String.class);
                                    String price = ds.child("price").getValue(String.class);
                                    String dprice = ds.child("dprice").getValue(String.class);
                                    String fee = ds.child("fee").getValue(String.class);
                                    String id = ds.child("id").getValue(String.class);
                                    String specifi1 = ds.child("specifi1").getValue(String.class);
                                    String specifi2 = ds.child("specifi2").getValue(String.class);
                                    String specifi3 = ds.child("specifi3").getValue(String.class);
                                    String specifi4 = ds.child("specifi4").getValue(String.class);
                                    String specifi5 = ds.child("specifi5").getValue(String.class);
                                    String specifi6 = ds.child("specifi6").getValue(String.class);
                                    String service1 = ds.child("service1").getValue(String.class);
                                    String service2 = ds.child("service2").getValue(String.class);
                                    String delivery = ds.child("delivery").getValue(String.class);
                                    String desc = ds.child("desc").getValue(String.class);

                                    Papularitemmodel model = new Papularitemmodel(img, name, price, dprice, fee, id, cid, specifi1, specifi2, specifi3, specifi4, specifi5, specifi6, service1, service2, delivery, desc);
                                    Subcategori_list.add(model);
                                }
                            }
                            Collections.shuffle(Subcategori_list);
//                            Toast.makeText(Subcategorie.this, String.valueOf(Subcategori_list.size()), Toast.LENGTH_SHORT).show();
                            adapter = new Papularitemadapter(Subcategori_list, Subcategorie.this);
                            subcategorirecycler.setAdapter(adapter);
//                            progressbar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(Subcategorie.this, "Empty", Toast.LENGTH_SHORT).show();
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
        // Papular item recycler


    @Override
    public void onNoteClick(int position) {
        String name =  Subcategori_list.get(position).getName();
        String dprice = Subcategori_list.get(position).getDprice();
        String img = Subcategori_list.get(position).getImg();
        String specifi1 =  Subcategori_list.get(position).getSpecifi1();
        String specifi2 =  Subcategori_list.get(position).getSpecifi2();
        String specifi3 =  Subcategori_list.get(position).getSpecifi3();
        String specifi4 =  Subcategori_list.get(position).getSpecifi4();
        String specifi5 =  Subcategori_list.get(position).getSpecifi5();
        String specifi6 =  Subcategori_list.get(position).getSpecifi6();
        String service1 = Subcategori_list.get(position).getService1();
        String service2 = Subcategori_list.get(position).getService2();
        String delivery = Subcategori_list.get(position).getDelivery();
        String desc = Subcategori_list.get(position).getDesc();
        String id = Subcategori_list.get(position).getId();
        Intent intent = new Intent(Subcategorie.this, ItemDetail.class);
        intent.putExtra("name",name);
        intent.putExtra("dprice", dprice);
        intent.putExtra("img", img);
        intent.putExtra("specifi1",specifi1);
        intent.putExtra("specifi2",specifi2);
        intent.putExtra("specifi3",specifi3);
        intent.putExtra("specifi4",specifi4);
        intent.putExtra("specifi5",specifi5);
        intent.putExtra("specifi6",specifi6);
        intent.putExtra("service1",service1);
        intent.putExtra("service2",service2);
        intent.putExtra("delivery",delivery);
        intent.putExtra("desc",desc);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    public void onCreates(Bundle savedInstanceState) {

    }
}