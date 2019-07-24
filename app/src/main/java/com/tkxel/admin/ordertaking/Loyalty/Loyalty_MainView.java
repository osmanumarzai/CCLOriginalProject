package com.tkxel.admin.ordertaking.Loyalty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.AdopterClass.LoyaltyCustomGrid;
import com.tkxel.admin.ordertaking.MapsActivity;
import com.tkxel.admin.ordertaking.R;


/**
 * Created by admin on 10/28/2017.
 */

public class Loyalty_MainView extends Activity {
    GridView grid;
    private int TIME_DELAY = 2000;
    private long back_pressed;
    String[] web = {
            "Scan",
            "Order",
            "Recipe",
            "Location",
            "History",
            "Setting"
    };
    int[] imageId_2 = {
            R.drawable.scan,
            R.drawable.orderfood,
            R.drawable.recipe,
            R.drawable.mapsandflags,
            R.drawable.histroy,
            R.drawable.settings
    };

    TextView tv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutymian_view_class);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setText("Home");
        LoyaltyCustomGrid adapter = new LoyaltyCustomGrid(Loyalty_MainView.this, web, imageId_2);
        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                fun_Calling(position);
            }
        });
        //   funSlider();
    }

//    void funSlider() {
//        AnimationDrawable animation = new AnimationDrawable();
//        animation.addFrame(getResources().getDrawable(R.drawable.banner001), 1000);
//        animation.addFrame(getResources().getDrawable(R.drawable.banner002), 2000);
//        animation.addFrame(getResources().getDrawable(R.drawable.banner003), 1000);
//        animation.setOneShot(false);
//    }


    void fun_Calling(int pos) {
        switch (pos) {
            case 0:
                Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
                startActivityForResult(intent, REQUEST_SCANNER);
                break;
            case 1:
                startActivity(new Intent(getApplicationContext(), Loyalty_ProductsCutomer.class));
                break;
            case 2:
                break;
            case 3:
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }


    private final static int REQUEST_SCANNER = 1;
    public final static String CONTENT = "content";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCANNER) {
            if (data != null) {
                String url = data.getStringExtra(CONTENT) + "";
                CustomDialogClass cdd = new CustomDialogClass(Loyalty_MainView.this, "" + url);
                cdd.show();
            }

        } else {

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

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }


    SharedPreferences sharedPreferences_form1;

    void loginDetails_save(boolean b) {
        sharedPreferences_form1 = getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences_form1.edit();
        editor.putBoolean("is_save", b);
        editor.commit();
    }

    void IsLive_save(boolean b) {
        sharedPreferences_form1 = getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences_form1.edit();
        editor.putBoolean("is_Live", b);
        editor.commit();
    }

    boolean IsLive_save() {
        sharedPreferences_form1 = getSharedPreferences("Login", Context.MODE_PRIVATE);
        return sharedPreferences_form1.getBoolean("is_Live", false);

    }
}