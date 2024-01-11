package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.finalyearproject.Models.Addtocart;
import com.example.finalyearproject.Models.Placeorder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Buynow extends AppCompatActivity {


    EditText infoname,infoemail,infophone,infoaddress;
    Button placeorderbtn;
    ImageView img;
    String rate = String.valueOf(120);
    String emailPattern = "[a-z0-9]+@[a-zl]+\\.+[a-z]+";
    int rates = Integer.parseInt(rate);
    String amount;
    DatabaseReference getUserDatabaseReference;
    private FirebaseAuth mAuth;
    TextView name,price,desc,orderprice,dprice,totalprice;
    float deliveryprice=0;
    String id;
    AlertDialog alertDialog;
    String useremail;
    String userpass;

    String data,namedata;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buynow);

        getSupportActionBar().setTitle("Place Order");

        img = findViewById(R.id.img);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        desc = findViewById(R.id.desc);
        orderprice = findViewById(R.id.orderprice);
        dprice = findViewById(R.id.dprice);
        totalprice = findViewById(R.id.totalprice);

        infoname = findViewById(R.id.infoname);
        infoemail = findViewById(R.id.infoemail);
        infophone = findViewById(R.id.infophone);
        infoaddress = findViewById(R.id.infoaddress);
        placeorderbtn = findViewById(R.id.placeorderbtn);

        infoemail.getText();

        SharedPreferences sharedpreferences = getSharedPreferences("RegisterDetails", Context.MODE_PRIVATE);
        useremail = sharedpreferences.getString("Email","");
        userpass = sharedpreferences.getString("Password","");
//        Toast.makeText(this, "Email: "+ useremail, Toast.LENGTH_SHORT).show();
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        getUserDatabaseReference = database.getReference("placeorder").child(userId);



        Intent intent = getIntent();
        data = intent.getStringExtra("img");
        Picasso.get().load(data).into(img);

        namedata = intent.getStringExtra("name");
        name.setText(namedata);

        String pricedata = intent.getStringExtra("price");
        price.setText(pricedata);
        orderprice.setText("PKR. "+pricedata);
        String descsdata = intent.getStringExtra("desc");
        desc.setText(descsdata);
        amount = pricedata;
        int pri = Integer.parseInt(pricedata);
        int totalamount = Integer.parseInt(String.valueOf(pri+rates));
        String showpri = String.valueOf(totalamount);
        totalprice.setText("PKR. "+ showpri);


        placeorderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infoname.getText().toString().isEmpty()){
                    Toast.makeText(Buynow.this, "Enter Your name", Toast.LENGTH_SHORT).show();
                } else if (infoemail.getText().toString().isEmpty()) {
                    Toast.makeText(Buynow.this, "Enter Your Email", Toast.LENGTH_SHORT).show();
                } else if (infophone.getText().toString().isEmpty()) {
                    Toast.makeText(Buynow.this, "Enter Your phone", Toast.LENGTH_SHORT).show();
                } else if (infophone.getText().toString().length()!=11) {
                    Toast.makeText(Buynow.this, "Enter Your Valid Phone", Toast.LENGTH_SHORT).show();
                } else if (infoaddress.getText().toString().isEmpty()) {
                    Toast.makeText(Buynow.this, "Enter Your Address", Toast.LENGTH_SHORT).show();
                } else if (infoemail.getText().toString().matches(emailPattern)){
                    try {
                        if (!isNetworkAvailable()) {
                            // Internet connection is not available, show a Toast
                            dialog();
                            Toast.makeText(Buynow.this, "Internet connection is unavailable.", Toast.LENGTH_SHORT).show();
                        }
                            else {
                                placeorderbtn.setVisibility(View.GONE);
                            confirmDialog();

                        }

                    }
                    catch (Exception e){

                    }

                }
                else{
                    Toast.makeText(Buynow.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    public void confirmDialog() {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.confirm_order_dialog);
            LottieAnimationView continueanim = dialog.findViewById(R.id.continueanim);
            LottieAnimationView thanks = dialog.findViewById(R.id.thanks);
            AppCompatButton orderdon = dialog.findViewById(R.id.orderdon);
            ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
            AppCompatButton continueshopping = dialog.findViewById(R.id.continueshopping);

            orderdon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    continueanim.setVisibility(View.GONE);
                    orderdon.setVisibility(View.GONE);
                    thanks.setVisibility(View.VISIBLE);
                    continueshopping.setVisibility(View.VISIBLE);
                    String fullName = infoname.getText().toString().trim();
                    String email = infoemail.getText().toString().trim();
                    String phone = infophone.getText().toString().trim();
                    String address = infoaddress.getText().toString().trim();

                    long currentTimeMillis = System.currentTimeMillis();
                    // Convert the time to a Firebase-compatible timestamp
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy\nh:mm a");
                    String formattedDate = sdf.format(new Date(currentTimeMillis));

                    // Create an Order object
                    Placeorder order = new Placeorder(id,data, namedata, amount, rate, fullName, email, phone, address,formattedDate);

                    // Push the order to the user's orders node
                    String orderId = getUserDatabaseReference.push().getKey();
                    getUserDatabaseReference.child(orderId).setValue(order);
                    Toast.makeText(Buynow.this, "Add Your Order Successfully!", Toast.LENGTH_SHORT).show();

                    SendMail sendEmailTask = new SendMail();
                    sendEmailTask.execute();
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
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

        } catch (Exception e) {
            e.printStackTrace();
            // Handle or log the exception as needed
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void dialog() {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Buynow.this);

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


}