package com.tkxel.admin.ordertaking.Loyalty;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.tkxel.admin.ordertaking.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 10/31/2017.
 */

public class SceenActivity_Class extends Activity implements View.OnClickListener {

    private final static int REQUEST_SCANNER = 1;
    public final static String FORMAT = "format";
    public final static String CONTENT = "content";
    String[] permissionsList = new String[]{
            Manifest.permission.CAMERA
    };

    TextView txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        if (checkPermissions()) {
            txtview = (TextView) findViewById(R.id.txtview);
            findViewById(R.id.scanlayout).setOnClickListener(this);
        } else {
            Toast.makeText(getApplicationContext(), "Permission Not Granted", Toast.LENGTH_LONG).show();
        }


    }

    Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.scanlayout:
                Intent intent = new Intent(SceenActivity_Class.this, ScannerActivity.class);
                startActivityForResult(intent, REQUEST_SCANNER);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCANNER) {
            if (data != null) {
                //   tv_scaan.setText(data.getStringExtra(CONTENT) + "");
//                Intent intent = new Intent(getApplicationContext(), Web_View_activity.class);
//                intent.putExtra("new_variable_name", data.getStringExtra(CONTENT) + "");
//                startActivity(intent);


//
                String url = data.getStringExtra(CONTENT) + "";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
                CustomDialogClass cdd = new CustomDialogClass(SceenActivity_Class.this, "" + url);
                cdd.show();


            }

        } else {

        }
    }

    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<String>();
        for (String p : permissionsList) {
            result = ContextCompat
                    .checkSelfPermission(SceenActivity_Class.this, p);
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

                findViewById(R.id.button2).setOnClickListener(this);
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

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
