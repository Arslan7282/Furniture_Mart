package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.finalyearproject.Adapters.Papularitemadapter;
import com.example.finalyearproject.Models.Addtocart;
import com.example.finalyearproject.Models.Papularitemmodel;
import com.example.finalyearproject.Models.Placeorder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ItemDetail extends AppCompatActivity implements Papularitemadapter.OnNoteListener{
    AppCompatButton addtocart, buynow;
    ImageView arrow1, arrow2, arrow3,img;
    TextView name, price, specifi1,specifi2,specifi3,specifi4,specifi5,specifi6, service1,service2, delivery,desc;
    private FrameLayout contentLayout1,contentLayout2,contentLayout3;
    DatabaseReference getUserDatabaseReference,getUserDatabaseReferencecart;
    private boolean isArrowDown1 = false;
    private boolean isArrowDown2 = false;
    private boolean isArrowDown3 = false;
    LottieAnimationView progressbar,progressbtn;
    AlertDialog alertDialog;
    private FirebaseAuth mAuth;
    ArrayList<Papularitemmodel> Related_list;
    RecyclerView relatedrecycler;
    Papularitemadapter adapter;
    String subcategoriid;
    String imgdata,namedata,descdata,data2data;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        if (!isNetworkAvailable()) {
            // Internet connection is not available, show a Toast
            dialog();
            Toast.makeText(this, "Internet connection is unavailable.", Toast.LENGTH_SHORT).show();
            return;
        }

        name = findViewById(R.id.name);
        img = findViewById(R.id.img);
        price = findViewById(R.id.price);
        desc = findViewById(R.id.desc);
        progressbar = findViewById(R.id.progressbar);
        progressbtn = findViewById(R.id.progressbtn);

        contentLayout1 = findViewById(R.id.contentLayout1);
        contentLayout2 = findViewById(R.id.contentLayout2);
        contentLayout3 = findViewById(R.id.contentLayout3);
        addtocart = findViewById(R.id.addtocart);
        buynow = findViewById(R.id.buynow);
        arrow1 = findViewById(R.id.arrow1);
        specifi1 = findViewById(R.id.specifi1);
        specifi2 = findViewById(R.id.specifi2);
        specifi3 = findViewById(R.id.specifi3);
        specifi4 = findViewById(R.id.specifi4);
        specifi5 = findViewById(R.id.specifi5);
        specifi6 = findViewById(R.id.specifi6);
        arrow2 = findViewById(R.id.arrow2);
        service1 = findViewById(R.id.service1);
        service2 = findViewById(R.id.service2);
        arrow3 = findViewById(R.id.arrow3);
        delivery = findViewById(R.id.delivery);
        relatedrecycler = findViewById(R.id.relatedrecycler);



        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        getUserDatabaseReferencecart = database.getReference("cart").child(userId);


        progressbar.setVisibility(View.VISIBLE);
        final Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        final Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        getSupportActionBar().setTitle("Item Detail");




        //Receive Data from first page of papular items
        Intent intent = getIntent();
        String data = intent.getStringExtra("img");
        Picasso.get().load(data).into(img);
        imgdata = data;

        String data1 = intent.getStringExtra("name");
        name.setText(data1);
        namedata = data1;

        String data2 = intent.getStringExtra("dprice");
        price.setText(data2);
        data2data=data2;

        String specification1 = intent.getStringExtra("specifi1");
        specifi1.setText(specification1);
        String specification2 = intent.getStringExtra("specifi2");
        specifi2.setText(specification2);
        String specification3 = intent.getStringExtra("specifi3");
        specifi3.setText(specification3);
        String specification4 = intent.getStringExtra("specifi4");
        specifi4.setText(specification4);
        String specification5 = intent.getStringExtra("specifi5");
        specifi5.setText(specification5);
        String specification6 = intent.getStringExtra("specifi6");
        specifi6.setText(specification6);

        String services1 = intent.getStringExtra("service1");
        service1.setText(services1);
        String services2 = intent.getStringExtra("service2");
        service2.setText(services2);

        String deliverys = intent.getStringExtra("delivery");
        delivery.setText(deliverys);

        String descs = intent.getStringExtra("desc");
        desc.setText(descs);
        descdata = descs;

//        subcategoriid = intent.getStringExtra("id");
        subcategoriid = intent.getStringExtra("catid");
        //Receive Data from first page of papular items


        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtocart.setText("");
                progressbtn.setVisibility(View.VISIBLE);

                try {
                    if (!isNetworkAvailable()) {
                        // Internet connection is not available, show a Toast
                        dialog();
                        Toast.makeText(ItemDetail.this, "Internet connection is unavailable.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        showAddToCartDialog();
                    }
                }
                catch (Exception e){
                }
//                Intent intent = new Intent(ItemDetail.this, Addcart.class);
//                startActivity(intent);
                progressbtn.setVisibility(View.GONE);
                addtocart.setText("Item Added");
            }
        });


        arrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isArrowDown1) {
                    arrow1.setImageResource(R.drawable.baseline_keyboard_arrow_right_24);
                    contentLayout1.startAnimation(slideUp);
                    specifi1.setVisibility(View.GONE);
                    specifi2.setVisibility(View.GONE);
                    specifi3.setVisibility(View.GONE);
                    specifi4.setVisibility(View.GONE);
                    specifi5.setVisibility(View.GONE);
                    specifi6.setVisibility(View.GONE);
                } else {
                    arrow1.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
                    contentLayout1.startAnimation(slideDown);
                    specifi1.setVisibility(View.VISIBLE);
                    specifi2.setVisibility(View.VISIBLE);
                    specifi3.setVisibility(View.VISIBLE);
                    specifi4.setVisibility(View.VISIBLE);
                    specifi5.setVisibility(View.VISIBLE);
                    specifi6.setVisibility(View.VISIBLE);
                }
                isArrowDown1 = !isArrowDown1;
            }
        });

        arrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isArrowDown2) {
                    arrow2.setImageResource(R.drawable.baseline_keyboard_arrow_right_24);
                    contentLayout2.startAnimation(slideUp);
                    service1.setVisibility(View.GONE);
                    service2.setVisibility(View.GONE);

                } else {
                    arrow2.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
                    contentLayout2.startAnimation(slideDown);
                    service1.setVisibility(View.VISIBLE);
                    service2.setVisibility(View.VISIBLE);
                }
                isArrowDown2 = !isArrowDown2;
            }
        });

        arrow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isArrowDown3) {
                    arrow3.setImageResource(R.drawable.baseline_keyboard_arrow_right_24);
                    contentLayout3.startAnimation(slideUp);
                    delivery.setVisibility(View.GONE);
                } else {
                    arrow3.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
                    contentLayout3.startAnimation(slideDown);
                    delivery.setVisibility(View.VISIBLE);
                }
                isArrowDown3 = !isArrowDown3;
            }
        });


        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetail.this, Buynow.class);
                intent.putExtra("img", data);
                intent.putExtra("name", data1);
                intent.putExtra("price",data2);
                intent.putExtra("desc",descs);
                startActivity(intent);
            }
        });

        progressbar.setVisibility(View.GONE);


        //recycler for papular items view
        Related_list = new ArrayList<>();
        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference("papularitem");
        relatedrecycler.setLayoutManager(new GridLayoutManager(this, 2));
        relatedrecycler.setHasFixedSize(true);
        getrelatedData();
        //recycler for papular items view


    }

    // Method to show the "Add to Cart" dialog
    private void showAddToCartDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_to_cart);
        AppCompatButton addToCartButton = dialog.findViewById(R.id.addToCartButton);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        AppCompatButton explore = dialog.findViewById(R.id.explore);
        AppCompatButton continueshopping = dialog.findViewById(R.id.continueshopping);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCartButton.setVisibility(View.GONE);
                explore.setVisibility(View.VISIBLE);
                continueshopping.setVisibility(View.VISIBLE);
                String orderId = getUserDatabaseReferencecart.push().getKey();
                long currentTimeMillis = System.currentTimeMillis();
                // Convert the time to a Firebase-compatible timestamp
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy\nh:mm a");
                String formattedDate = sdf.format(new Date(currentTimeMillis));
                // Create an Order object
                Addtocart order = new Addtocart(imgdata,namedata, descdata, data2data, formattedDate,  orderId);
                // Push the order to the user's orders node
                getUserDatabaseReferencecart.child(orderId).setValue(order);
//                dialog.dismiss();
                Toast.makeText(ItemDetail.this, "Item added to cart", Toast.LENGTH_SHORT).show();

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetail.this, Firstpage.class);
                startActivity(intent);
                finish();
            }
        });
        continueshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }   // Method to show the "Add to Cart" dialog


    // Papular item recycler
    private void getrelatedData() {
        try {
            getUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Related_list.clear();
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
                                Related_list.add(model);
                            }
                        }
                        Collections.shuffle(Related_list);
//                            Toast.makeText(Subcategorie.this, String.valueOf(Subcategori_list.size()), Toast.LENGTH_SHORT).show();
                        adapter = new Papularitemadapter(Related_list, ItemDetail.this);
                        relatedrecycler.setAdapter(adapter);
//                            progressbar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(ItemDetail.this, "Empty", Toast.LENGTH_SHORT).show();
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

    private void dialog() {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ItemDetail.this);

            alertDialogBuilder.setTitle("No Internet!!").setMessage("Please Check Your Internet");
            alertDialogBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                alertDialog.dismiss();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onNoteClick(int position) {
        String name =  Related_list.get(position).getName();
        String dprice = Related_list.get(position).getDprice();
        String img = Related_list.get(position).getImg();
        String specifi1 =  Related_list.get(position).getSpecifi1();
        String specifi2 =  Related_list.get(position).getSpecifi2();
        String specifi3 =  Related_list.get(position).getSpecifi3();
        String specifi4 =  Related_list.get(position).getSpecifi4();
        String specifi5 =  Related_list.get(position).getSpecifi5();
        String specifi6 =  Related_list.get(position).getSpecifi6();
        String service1 = Related_list.get(position).getService1();
        String service2 = Related_list.get(position).getService2();
        String delivery = Related_list.get(position).getDelivery();
        String desc = Related_list.get(position).getDesc();
        String id = Related_list.get(position).getId();
        Intent intent = new Intent(ItemDetail.this, ItemDetail.class);
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
