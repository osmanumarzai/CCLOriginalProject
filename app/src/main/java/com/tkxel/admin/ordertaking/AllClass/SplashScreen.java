package com.tkxel.admin.ordertaking.AllClass;

/**
 * Created by admin on 10/16/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


import com.tkxel.admin.ordertaking.LoadingClass.Offline_LoginTask;
import com.tkxel.admin.ordertaking.R;

import java.util.ArrayList;
import java.util.List;


public class SplashScreen extends Activity {
    //
    String[] permissionsList = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
    };
    private int SPLASH_TIME_OUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (checkPermissions()) {
            if (isConnected()) {
                // Online Apps
                fun_all();
            } else {
                // Offline Apps
                  if (get_jsonDetails().equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), "No Internet Connection !!", Toast.LENGTH_LONG).show();
                    } else {
                        new Offline_LoginTask(SplashScreen.this, get_jsonDetails(),true).execute();
                  }

            }
        } else {
            Toast.makeText(getApplicationContext(), "Permission Not Granted", Toast.LENGTH_LONG).show();
        }
    }


// Online Apps
    void fun_all() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                if (getlogin()) {

                    if (get_jsonDetails().equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), "Error Come Please Contact to Admin!", Toast.LENGTH_LONG).show();
                    } else {

                        new Offline_LoginTask(SplashScreen.this, get_jsonDetails(),false).execute();

                    }
                } else {

                    Intent i = new Intent(SplashScreen.this, LoginScreen.class);

                    //Intent i = new Intent(SplashScreen.this, CustomerListActivity.class);

                    startActivity(i);
                    finish();
                }


            }
        }, SPLASH_TIME_OUT);
    }


    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<String>();
        for (String p : permissionsList) {
            result = ContextCompat
                    .checkSelfPermission(SplashScreen.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    String permissionss = "";

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (isConnected()) {
                    fun_all();
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_LONG).show();
                }

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permissions granted.
                } else {
                    for (String per : permissionsList) {
                        permissionss += "\n" + per;
                    }
                    // permissions list of don't granted permission
                }
                return;
            }
        }
    }


    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    SharedPreferences sharedPreferences_form1;

    boolean getlogin() {
        sharedPreferences_form1 = getSharedPreferences("Login", Context.MODE_PRIVATE);
        return sharedPreferences_form1.getBoolean("is_save", false);

    }

    String get_jsonDetails() {
        sharedPreferences_form1 = getSharedPreferences("saveJson", Context.MODE_PRIVATE);
        return sharedPreferences_form1.getString("saveJson", "");

    }

}