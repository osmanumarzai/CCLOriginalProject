package com.tkxel.admin.ordertaking.AllClass;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tkxel.admin.ordertaking.AdopterClass.AdapterSpinShop;
import com.tkxel.admin.ordertaking.ModelClass.MerchandiserVisit;
import com.tkxel.admin.ordertaking.ModelClass.Shop;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.Api;
import com.tkxel.admin.ordertaking.Utilities.GPSTracker;
import com.tkxel.admin.ordertaking.Utilities.ProgressBarHandler;
import com.tkxel.admin.ordertaking.Utilities.RetrofitClient;
import com.tkxel.admin.ordertaking.Utilities.SharedPreferencesSingletonStringKey;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchandiserVisitAddActivity extends AppCompatActivity {

    private Button btn_save;

    int ShopID;
    List<Shop> mListShop= new ArrayList<>();
    private Spinner shopSpinner;
    private AdapterSpinShop adapterSpinShop;

//    private int SPLASH_TIME_OUT = 1000;
    private ProgressDialog dialog;
    TextView tv_location;
    String address_ = "";
    public double current_latitude, current_longitude;
    int UserID ;

    GPSTracker gps;

    ProgressBarHandler progressBarHandler;
    ProgressDialog mProgressDialog;

    int CustomerID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchandiser_visit_add);

        CustomerID = SharedPreferencesSingletonStringKey.getInstance(MerchandiserVisitAddActivity.this).getInt(SharedPreferencesSingletonStringKey.Key.CustomerID);


        dialog = new ProgressDialog(MerchandiserVisitAddActivity.this);
        gps = new GPSTracker(MerchandiserVisitAddActivity.this);

        shopSpinner = (Spinner) findViewById(R.id.sp_shop);
        tv_location = (TextView) findViewById(R.id.tv_location);

        try {
            findLaction();
            showLocationOnUI();
        } catch (IOException e) {
            e.printStackTrace();
        }

        populateShopSpinner();
        setListener();

        UserID =  SharedPreferencesSingletonStringKey.getInstance(MerchandiserVisitAddActivity.this).getInt(SharedPreferencesSingletonStringKey.Key.UserID);
    }

    private void selectValue(int customerID) {
        for (int i = 0; i < shopSpinner.getCount(); i++) {
            Shop shop = (Shop) shopSpinner.getItemAtPosition(i);

            if (shop.getId() == customerID ) {
                shopSpinner.setSelection(i);
                break;
            }

        }
    }

    private void showLocationOnUI()
    {
        tv_location.setText(address_);
    }

    void GpsSetting_Dialog(String Title1, String Title, String strBtn, String strBtn2) {

        // custom dialog
        final Dialog dialog = new Dialog(MerchandiserVisitAddActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.newiponedialoge2);
        dialog.setCanceledOnTouchOutside(false);
        // dialog.getWindow().setLayout(275, 350);

        TextView text_title1 = (TextView) dialog.findViewById(R.id.title_d1);
        text_title1.setText(Title1);

        TextView text_title = (TextView) dialog.findViewById(R.id.title_d);
        text_title.setText(Title);


        TextView dialogButton = (TextView) dialog.findViewById(R.id.btn_d);
        dialogButton.setText(strBtn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 1);
            }
        });
        TextView dialogButton2 = (TextView) dialog.findViewById(R.id.btn_d2);
        dialogButton2.setText(strBtn2);
        dialogButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    void findLaction() throws IOException {

        LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            GpsSetting_Dialog("GPS SETTINGS", "Please enable GPS in order to use this app", "SETTINGS", "CANCEL");

        } else {
            gps = new GPSTracker(MerchandiserVisitAddActivity.this);

            if (gps.canGetLocation()) {

                current_latitude = gps.getLatitude();
                current_longitude = gps.getLongitude();

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(current_latitude, current_longitude, 5);
                if (addresses.size() != 0) {
                    String cityName = addresses.get(0).getAddressLine(0);
                    String stateName = addresses.get(0).getAddressLine(1);
                    String countryName = addresses.get(0).getAddressLine(2);

                    address_ = cityName + "";
                }
            } else {
                Log.i("Maps", "not found location");
            }
        }

    }



    private void populateShopSpinner()
    {
        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.GetShops();

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if(response.isSuccessful()) {

                    mListShop= GetShopList(response);

                    adapterSpinShop = new AdapterSpinShop(MerchandiserVisitAddActivity.this,android.R.layout.simple_list_item_1, mListShop);

                    shopSpinner.setAdapter(adapterSpinShop); // Set the custom adapter to the spinner

                    if (CustomerID != -1)
                    {
                        selectValue(CustomerID);
                    }

                    shopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {

                            Shop shop = adapterSpinShop.getItem(position);
                            ShopID = shop.getId();
//                            Toast.makeText(MerchandiserVisitAddActivity.this, "ID: " + shop.getId() + "\nName: " + shop.getName(),
//                                    Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapter) {
                        }
                    });

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });



    }


    private List<Shop> GetShopList(Response<Object> response) {

        List<Shop> mList = new ArrayList<>();
        Gson gson = new Gson();
        String json = gson.toJson(response.body());
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(json);

            mList = Arrays.asList(gson.fromJson(jArray.toString(), Shop[].class));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mList;
    }

    private void setListener() {

        btn_save = (Button) findViewById(R.id.btn_save);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MerchandiserVisit merchandiserVisit =new MerchandiserVisit(UserID,ShopID,address_ );
                insertMerchandiserVisit(merchandiserVisit);
            }
        });

    }

    private void insertMerchandiserVisit(MerchandiserVisit merchandiserVisit)
    {
        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.PostMerchandiserVisit(merchandiserVisit);

        mProgressDialog = new ProgressDialog(MerchandiserVisitAddActivity.this);
        progressBarHandler = new ProgressBarHandler(mProgressDialog,"Saving");
        progressBarHandler.show(mProgressDialog);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response)
            {

                if(response.isSuccessful()) {


                    int result = GetResult(response);
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }

                progressBarHandler.hide(mProgressDialog);
                finish();


            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                // close it after response
                progressBarHandler.hide(mProgressDialog);

            }
        });
    }

    private int GetResult(Response<Object> response) {

        Gson gson = new Gson();
        String json = gson.toJson(response.body());
        return 1;

    }
}
