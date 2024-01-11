package com.example.finalyearproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternet {
    public boolean isConnect(Context context)
    {
        ConnectivityManager manager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile_Info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi_Info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifi_Info!=null && wifi_Info.isConnected() || mobile_Info != null && mobile_Info.isConnected())
        {
            return true;
        }
        else {
            return false;
        }
    }
}