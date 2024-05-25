package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.finalyearproject.Adapters.Cartlistadapter;
import com.example.finalyearproject.Models.Cartlistmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Addcart extends AppCompatActivity {

    TextView totalamount;
    RecyclerView cartrecycler;
    AppCompatButton placeallorder;
    ArrayList<Cartlistmodel> Cart_list;
    Cartlistadapter adapter;
    DatabaseReference getUserDatabaseReference;
    private FirebaseAuth mAuth;
    private String userid;
    private int tbill = 0;
    private LottieAnimationView progressbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcart);

        cartrecycler = findViewById(R.id.cartrecycler);
        totalamount = findViewById(R.id.totalamount);
        progressbar = findViewById(R.id.progressbar);
        placeallorder = findViewById(R.id.placeallorder);

        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getUid();
        progressbar.setVisibility(View.VISIBLE);

        Cart_list = new ArrayList<>();
        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference("cart").child(userid);
        cartrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cartrecycler.setHasFixedSize(true);

        adapter = new Cartlistadapter(Cart_list, Addcart.this, totalamount);
        cartrecycler.setAdapter(adapter);
        getCartData();

        placeallorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!Cart_list.isEmpty()) {
                        // Intent intent = new Intent(Addcart.this,Buynow.class);
                        // intent.putExtra("TotalBill",tBill);
                        // intent.putExtra("numb",bucketList.size());
                        // intent.putExtra("charges",charges);
                        // intent.putExtra("lessThan",lessThan);
                        // startActivity(intent);
                    } else {
                        Toast.makeText(Addcart.this, "Cart is empty", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Addcart.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getCartData() {
        try {
            getUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Cart_list.clear();
                        tbill = 0;
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String cartname = ds.child("cartname").getValue(String.class);
                            String cartimg = ds.child("cartimg").getValue(String.class);
                            String cartdisc = ds.child("cartdisc").getValue(String.class);
                            String cartprice = ds.child("cartprice").getValue(String.class);
                            String carttime = ds.child("timestamp").getValue(String.class);
                            String orderId = ds.child("orderId").getValue(String.class);

                            Cartlistmodel model = new Cartlistmodel(cartimg, cartname, cartdisc, cartprice, carttime, orderId);
                            Cart_list.add(model);
                            tbill += Integer.parseInt(cartprice);
                        }
                        Collections.shuffle(Cart_list);
                        adapter.notifyDataSetChanged();
                        totalamount.setText(String.valueOf(tbill));
                    } else {
                        Toast.makeText(Addcart.this, "Empty", Toast.LENGTH_SHORT).show();
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
    public void updateTotalAmount() {
        int tbill = 0;
        for (Cartlistmodel model : Cart_list) {
            getCartData();
            tbill += Integer.parseInt(model.getCartprice());
        }
        totalamount.setText(String.valueOf(tbill));
    }
}