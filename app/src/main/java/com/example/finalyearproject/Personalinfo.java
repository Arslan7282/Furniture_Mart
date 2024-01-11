package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject.Models.profilemodal;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class Personalinfo extends AppCompatActivity {
    Uri uri;
    private CircleImageView profile_settings_image;
    private ImageView editPhotoIcon;
    private EditText display_name, display_email, user_phone, user_profession, user_nickname,user_date;

    private Button saveInfoBtn;
    String email;
    private DatabaseReference getUserDatabaseReference;
    private FirebaseAuth mAuth;
    private StorageReference mProfileImgStorageRef;
    private StorageReference thumb_image_ref;
    String user_id;
    private final static int GALLERY_PICK_CODE = 1;
    Bitmap thumb_Bitmap = null;

    private ProgressDialog progressDialog;
    private String selectedGender = "", profile_download_url, profile_thumb_download_url;


    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);

        mAuth = FirebaseAuth.getInstance();
         user_id = mAuth.getCurrentUser().getUid();
        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);
        getUserDatabaseReference.keepSynced(true); // for offline

        mProfileImgStorageRef = FirebaseStorage.getInstance().getReference().child("profile_image");
        thumb_image_ref = FirebaseStorage.getInstance().getReference().child("thumb_image");

        profile_settings_image = findViewById(R.id.profile_img);
        display_name = findViewById(R.id.user_display_name );
        user_nickname = findViewById(R.id.user_nickname );
        user_profession = findViewById(R.id.profession);
        display_email = findViewById(R.id.userEmail);
        user_date = findViewById(R.id.udate);
        user_phone = findViewById(R.id.phone);
        editPhotoIcon = findViewById(R.id.editPhotoIcon);
        saveInfoBtn = findViewById(R.id.saveInfoBtn);



        editPhotoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(Personalinfo.this).crop()
                        // Crop image(Optional), Check Customization for more option

                      //  .compress(1024)			//Final image size will be less than 1 MB(Optional)
                       // .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });


//        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");

        progressDialog = new ProgressDialog(this);

        // Retrieve data from database
        getUserDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // retrieve data from db
                String name = dataSnapshot.child("user_name").getValue().toString();
                String nickname = dataSnapshot.child("user_nickname").getValue().toString();
                String profession = dataSnapshot.child("user_profession").getValue().toString();
                email = dataSnapshot.child("user_email").getValue().toString();
                String phone = dataSnapshot.child("user_mobile").getValue().toString();
                String image = dataSnapshot.child("user_image").getValue().toString();
               // String thumbImage = dataSnapshot.child("user_thumb_image").getValue().toString();
                String date = dataSnapshot.child("user_date").getValue().toString();


                user_date.setText(date);
                user_date.setSelection(user_date.getText().length());

                display_name.setText(name);
                display_name.setSelection(display_name.getText().length());

                user_nickname.setText(nickname);
                user_nickname.setSelection(user_nickname.getText().length());

                user_profession.setText(profession);
                user_profession.setSelection(user_profession.getText().length());


                user_phone.setText(phone);
                user_phone.setSelection(user_phone.getText().length());


                display_email.setText(email);


                if(!image.equals("default_image")){ // default image condition for new user
                    Picasso.get()
                            .load(image)
                           // .networkPolicy(NetworkPolicy.OFFLINE) // for offline
                            .placeholder(R.drawable.default_profile_image)
                            .error(R.drawable.default_profile_image)
                            .into(profile_settings_image);
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        /** Edit information */
        saveInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = display_name.getText().toString();
                String uNickname = user_nickname.getText().toString();
                String uPhone = user_phone.getText().toString();
                String uProfession = user_profession.getText().toString();
                String udate = user_date.getText().toString();

                saveInformation(uName, uNickname, uPhone, uProfession,udate);

            }
        });


        // hide soft keyboard
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    } // Ending onCrate


    private void saveInformation(String uName, String uNickname, String uPhone, String uProfession, String udate) {
        if (TextUtils.isEmpty(uName)){
            Toast.makeText(this, "Oops! your name can't be empty", Toast.LENGTH_SHORT).show();
        } else if (uName.length()<3 || uName.length()>40){
            Toast.makeText(this, "Your name should be 3 to 40 numbers of characters", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(uPhone)){
            Toast.makeText(this, "Your mobile number is required.", Toast.LENGTH_SHORT).show();
        } else if (uPhone.length()<11){
            Toast.makeText(this, "Sorry! your mobile number is too short", Toast.LENGTH_SHORT).show();
        } else if (udate.isEmpty()) {
            Toast.makeText(this, "Enter Your Date of Birth", Toast.LENGTH_SHORT).show();
        } else {

            /** Store profile image into database */

            FirebaseStorage firebaseStorage;
            firebaseStorage=FirebaseStorage.getInstance();
            FirebaseDatabase firebaseDatabase;
            firebaseDatabase=FirebaseDatabase.getInstance();
            final  StorageReference storageReference=firebaseStorage.getReference().child("profileimage").child(FirebaseAuth.getInstance().getUid());

            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            profilemodal profilemodal=new profilemodal();
                            profilemodal.setUser_image(uri.toString());
                            profilemodal.setSearch_name("");
                            profilemodal.setCreated_at("");
                            profilemodal.setUser_email(email);
                            profilemodal.setUser_date(user_date.getText().toString());
                            profilemodal.setUser_mobile(user_phone.getText().toString());
                            profilemodal.setUser_nickname(user_nickname.getText().toString());
                            profilemodal.setUser_profession(user_profession.getText().toString());
                            profilemodal.setUser_name(display_name.getText().toString());


                            firebaseDatabase.getReference().child("users").child(user_id).setValue(profilemodal).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Personalinfo.this, "Update", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                }
            });

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {     //Image Uri will not be null for RESULT_OK
            uri=data.getData();                     // Use Uri object instead of File to avoid storage permissions
            profile_settings_image.setImageURI(uri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }



    }


}