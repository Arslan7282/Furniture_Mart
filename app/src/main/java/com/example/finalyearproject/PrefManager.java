package com.example.finalyearproject;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    Context context;
    public PrefManager(Context context) {this.context = context;}

    public void saveRegisterDetails( String semail, String sphone, String spass){
        SharedPreferences sharedPreferences = context.getSharedPreferences("RegisterDetails", Signup.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Phone",sphone);
        editor.putString("Email",semail);
        editor.putString("Password",spass);
        editor.apply();
    }
}