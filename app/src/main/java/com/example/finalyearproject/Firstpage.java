package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.motion.utils.StopLogic;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.finalyearproject.Adapters.Categorieadapter;
import com.example.finalyearproject.Adapters.Papularitemadapter;
import com.example.finalyearproject.Adapters.Todayofferadapter;
import com.example.finalyearproject.Models.Categoriemodel;
import com.example.finalyearproject.Models.Papularitemmodel;
import com.example.finalyearproject.Models.Todayoffermodel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import me.ibrahimsn.lib.BuildConfig;

public class Firstpage extends AppCompatActivity implements Papularitemadapter.OnNoteListener, Categorieadapter.OnCateListener, Todayofferadapter.OnTodayListener {

    TextView specifi1,specifi2,specifi3,specifi4,specifi5,specifi6,price;
    LottieAnimationView progrssbar;
    SearchView searchitem;
    DrawerLayout dl;
    NavigationView nv;
    ActionBarDrawerToggle t;
    TextView notifyMess;
    DatabaseReference getUserDatabaseReference;
    RecyclerView papularrecycler, todayofferrecycler,categorierecycler;
    ArrayList<Papularitemmodel> Papular_list;
    ArrayList<Categoriemodel> Categorie_list;
    ArrayList<Todayoffermodel> Todayoffer_list;
    Papularitemadapter adapter;
    Todayofferadapter todayadapter;
    Categorieadapter cateadapter;
    AlertDialog alertDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        searchitem = findViewById(R.id.searchitem);
        progrssbar = findViewById(R.id.progrssbar);
        price = findViewById(R.id.price);
        specifi1 = findViewById(R.id.specifi1);
        specifi2 = findViewById(R.id.specifi2);
        specifi3 = findViewById(R.id.specifi3);
        specifi4 = findViewById(R.id.specifi4);
        specifi5 = findViewById(R.id.specifi5);
        specifi6 = findViewById(R.id.specifi6);
        papularrecycler = findViewById(R.id.papularrecycler);
        categorierecycler = findViewById(R.id.categorierecycler);
        todayofferrecycler = findViewById(R.id.todayofferrecycler);
        nv = findViewById(R.id.nv);
        dl = (DrawerLayout) findViewById(R.id.drawer);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        // Set up RecyclerView and other initializations
        // ...


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setSubtitle(Html.fromHtml("<small>Design Your Home with Furniture Mart</small>"));
        getSupportActionBar().setTitle("Furniture Mart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        searchitem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle the search query submission if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle the search query change
                filterData(newText);
                return true;
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isNetworkAvailable()) {
                    // Internet connection is not available, show a Toast
                    Toast.makeText(Firstpage.this, "Internet connection is unavailable.", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
                // Refresh data from the database
                refreshData();
                // Stop the refreshing animation
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        if (!isNetworkAvailable()) {
            // Internet connection is not available, show a Toast
            dialog();
            Toast.makeText(this, "Internet connection is unavailable.", Toast.LENGTH_SHORT).show();
            return;
        }

//recycler for Today Offer items view
        Todayoffer_list = new ArrayList<>();
        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference("todayoffer");
        todayofferrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        todayofferrecycler.setHasFixedSize(true);
        getMenData2();
        //recycler for Today Offer items view


        //recycler for Categorie items view
        Categorie_list = new ArrayList<>();
        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference("categorie");
        categorierecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categorierecycler.setHasFixedSize(true);
        getMenData1();
        //recycler for Categorie items view


        //recycler for papular items view
        Papular_list = new ArrayList<>();
        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference("papularitem");
        papularrecycler.setLayoutManager(new GridLayoutManager(this, 2));
        papularrecycler.setHasFixedSize(true);
        getMenData();
        //recycler for papular items view



        // Drawable start
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        t = new ActionBarDrawerToggle(this, dl, R.string.nav_open, R.string.nav_close);
        dl.addDrawerListener(t);
        t.syncState();
        nv.setNavigationItemSelectedListener(item -> {
            try {
                int id = item.getItemId();
                switch (id) {

                    case R.id.setting:
                        Intent art = new Intent(Firstpage.this, Setting.class);
                        startActivity(art);
                        break;
                    case R.id.cart:
                        Intent car = new Intent(Firstpage.this, Addcart.class);
                        startActivity(car);
                        break;
                    case R.id.order:
                        Intent ord = new Intent(Firstpage.this, Orders.class);
                        startActivity(ord);
                        break;
                    case R.id.abo:
                        Intent ab = new Intent(Firstpage.this, About.class);
                        startActivity(ab);
                        break;
                    case R.id.notif:
                        Intent not = new Intent(Firstpage.this, Notification.class);
                        startActivity(not);
                        break;

                    case R.id.logout_accont:
                        new PrefManager(Firstpage.this).saveRegisterDetails("", "", "");
                        Intent intent = new Intent(Firstpage.this, Login.class);
                        startActivity(intent);
                        Toast.makeText(this, "Logout Successfully!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sharebutton:
                        try {
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "HOME MAI'N");
                            String shareMessage = "\nLet me recommend you this application\n\n";
                            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                            startActivity(Intent.createChooser(shareIntent, "choose one"));
                        } catch (Exception e) {
                        }
                        break;


                    default:
                        return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;

        });
        // Drawable End


    }


    private void filterData(String newText) {
    }

    private void refreshData() {

    }

    private void dialog() {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Firstpage.this);
            alertDialogBuilder.setTitle("No Internet!!").setMessage("Please Check Your Internet");
            alertDialogBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                alertDialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(intent);
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

    //Today Offer item recycler
    private void getMenData2() {
        progrssbar.setVisibility(View.VISIBLE);
        try {
            getUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Todayoffer_list.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String desc = ds.child("desc").getValue(String.class);
                            String dprice = ds.child("dprice").getValue(String.class);
                            String id = ds.child("id").getValue(String.class);
                            String img = ds.child("img").getValue(String.class);
                            String name = ds.child("name").getValue(String.class);


                            Todayoffermodel model = new Todayoffermodel(desc, id, img, name, dprice);
                            Todayoffer_list.add(model);
                        }
                        Collections.shuffle(Todayoffer_list);
                        todayadapter = new Todayofferadapter(Todayoffer_list, Firstpage.this);
                        todayofferrecycler.setAdapter(todayadapter);
                    } else {
                        Toast.makeText(Firstpage.this, "Empty", Toast.LENGTH_SHORT).show();
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
    //Today Offer item recycler


    // Categorie item recycler
    private void getMenData1() {
        try {
            getUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Categorie_list.clear();
                        for (DataSnapshot dsa : dataSnapshot.getChildren()) {
                            String catname = dsa.child("catname").getValue(String.class);
                            String id = dsa.child("id").getValue(String.class);

                            Categoriemodel catmodel = new Categoriemodel(catname, id);
                            Categorie_list.add(catmodel);
                        }
//                        Toast.makeText(Firstpage.this, String.valueOf(Categorie_list.size()), Toast.LENGTH_SHORT).show();
                        cateadapter = new Categorieadapter(Categorie_list, Firstpage.this);
                        categorierecycler.setAdapter(cateadapter);
                    } else {
                        Toast.makeText(Firstpage.this, "Empty", Toast.LENGTH_SHORT).show();
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
    // Categorie item recycler


    // Papular item recycler
    private void getMenData() {
        try {
            getUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Papular_list.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String img = ds.child("img").getValue(String.class);
                            String name = ds.child("name").getValue(String.class);
                            String price = ds.child("price").getValue(String.class);
                            String dprice = ds.child("dprice").getValue(String.class);
                            String fee = ds.child("fee").getValue(String.class);
                            String id = ds.child("id").getValue(String.class);
                            String cid = ds.child("catid").getValue(String.class);
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

                            Papularitemmodel model = new Papularitemmodel(img, name, price, dprice, fee, id, cid,specifi1,specifi2,specifi3,specifi4,specifi5,specifi6,service1,service2,delivery,desc);
                            Papular_list.add(model);
                        }
                        Collections.shuffle(Papular_list);
//                        Toast.makeText(Firstpage.this, String.valueOf(Papular_list.size()), Toast.LENGTH_SHORT).show();
                        adapter = new Papularitemadapter(Papular_list, Firstpage.this);
                        papularrecycler.setAdapter(adapter);
                        progrssbar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(Firstpage.this, "Empty", Toast.LENGTH_SHORT).show();
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


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    //Drawable override method
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (t.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Drawable override method


    @Override
    public void onTodayClick(int position) {
        String name =  Todayoffer_list.get(position).getName();
        String dprice = Todayoffer_list.get(position).getDprice();
        String img = Todayoffer_list.get(position).getImg();
        String id = Todayoffer_list.get(position).getId();
        Intent intent = new Intent(Firstpage.this, ItemDetail.class);
        intent.putExtra("name",name);
        intent.putExtra("dprice", dprice);
        intent.putExtra("img", img);
        intent.putExtra("id", id);
        startActivity(intent);
    }


    @Override
    public void onCateClick(int position) {
        String name =  Categorie_list.get(position).getCatname();
        String id = Categorie_list.get(position).getId();
        Intent intent = new Intent(Firstpage.this, Subcategorie.class);
        intent.putExtra("name",name);
        intent.putExtra("id",id);
        startActivity(intent);
    }


    @Override
    public void onNoteClick(int position) {
        String name =  Papular_list.get(position).getName();
        String dprice = Papular_list.get(position).getDprice();
        String img = Papular_list.get(position).getImg();
        String specifi1 =  Papular_list.get(position).getSpecifi1();
        String specifi2 =  Papular_list.get(position).getSpecifi2();
        String specifi3 =  Papular_list.get(position).getSpecifi3();
        String specifi4 =  Papular_list.get(position).getSpecifi4();
        String specifi5 =  Papular_list.get(position).getSpecifi5();
        String specifi6 =  Papular_list.get(position).getSpecifi6();
        String service1 = Papular_list.get(position).getService1();
        String service2 = Papular_list.get(position).getService2();
        String delivery = Papular_list.get(position).getDelivery();
        String desc = Papular_list.get(position).getDesc();
        String id = Papular_list.get(position).getId();
        String catid = Papular_list.get(position).getCatid();
        Intent intent = new Intent(Firstpage.this, ItemDetail.class);
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
        intent.putExtra("catid",catid);
        startActivity(intent);
    }

    @Override
    public void onCreates(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



}