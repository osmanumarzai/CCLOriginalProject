package com.tkxel.admin.ordertaking.AllClass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tkxel.admin.ordertaking.AdopterClass.CustomGrid;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.Api;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;
import com.tkxel.admin.ordertaking.Utilities.RetrofitClient;
import com.tkxel.admin.ordertaking.Utilities.SharedPreferencesSingletonStringKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Main_View extends Activity {
    GridView grid;
    private int TIME_DELAY = 2000;
    private long back_pressed;
    String[] web = {
            "Merchandisers",
            "Customers",
            "Brand Avail",
            "POSM Placement",
            "Selling Pattern",
            "Product Placement Visibility",
            "ShopVisit",


    };
    int[] imageId_2 = {
            R.drawable.stockicon,
            R.drawable.customer_list,
            R.drawable.product_list_avail,
            R.drawable.product_ads,
            R.drawable.sellingpattern,
            R.drawable.productplacement_visibility,
            R.drawable.visit,
    };

    TextView tv1;
    TextView tvoffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_view_class);

        tv1 = (TextView) findViewById(R.id.tv1);

        if (MyConstants.listLoginDetails.size() > 0)
        {
            tv1.setText("Welcome " + MyConstants.listLoginDetails.get(0).get_Name());
            int userid = MyConstants.listLoginDetails.get(0).get_EmployeeId();
            SharedPreferencesSingletonStringKey.getInstance(Main_View.this).put(SharedPreferencesSingletonStringKey.Key.UserID, userid);


//            String email  = MyConstants.listLoginDetails.get(0).get_Email();
            //getUserIDByEmail(email);
        }

        else
        {
            tv1.setText("Welcome ");

        }




        CustomGrid adapter = new CustomGrid(Main_View.this, web, imageId_2);
        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                fun_Calling(position);


            }
        });
        tvoffline = (TextView) findViewById(R.id.tvoffline);
        tvoffline.setVisibility(View.GONE);
        findViewById(R.id.imgnew_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main_View.this, LoginScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                loginDetails_save(false);
            }
        });
        funSlider();
    }

    void funSlider() {
        AnimationDrawable animation = new AnimationDrawable();
        animation.addFrame(getResources().getDrawable(R.drawable.banner001), 1000);
        animation.addFrame(getResources().getDrawable(R.drawable.banner002), 2000);
        animation.addFrame(getResources().getDrawable(R.drawable.banner003), 1000);
        animation.setOneShot(false);

        ImageView imageAnim = (ImageView) findViewById(R.id.img);
        imageAnim.setBackgroundDrawable(animation);

        // start the animation!
        animation.start();
    }

    Intent intent;

    void fun_Calling(int key) {
        switch (key) {

            //customers
            case 0:
                if (isConnected()) {
                    intent = new Intent(getApplicationContext(), MerchandiserListActivity.class);
                    startActivity(intent);

//                    intent = new Intent(getApplicationContext(), Take_Order_Activity.class);
//                    startActivity(intent);


                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:


                if (isConnected()) {

                    intent = new Intent(getApplicationContext(), CustomerListActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
                }

                break;
            case 2:


                if (isConnected()) {
                    intent = new Intent(getApplicationContext(), ProductAvailAddActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
                }
                break;

            case 3:
                if (isConnected()) {
                    intent = new Intent(getApplicationContext(), ProductAdvertisementAddActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:

                if (isConnected()) {
                    intent = new Intent(getApplicationContext(), SellingPatternActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
                }

                break;
            case 5:

                if (isConnected()) {

                    intent = new Intent(getApplicationContext(), ProductPlacementVisibilityAddActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
                }


                break;

            case 6:


                if (isConnected()) {
                    intent = new Intent(getApplicationContext(), MerchandiserVisitAddActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
                }


                break;
            case 8:
                if (!IsLive_save()) {
                    Gernal_Dialog("Change Server Setting", "Are you sure you want to go for live server", "OK", "CANCEL", 0);
                } else {
                    Gernal_Dialog("Change Server Setting", "Are you sure you want to go for Local server", "OK", "CANCEL", 1);
                }
                break;
            case 7:

                if (isConnected()) {


                } else {

                }

                break;
        }
    }


    public void Gernal_Dialog(String Title1, String Title, String strBtn, String strBtn2, final int type) {

        // custom dialog
        final Dialog dialog = new Dialog(Main_View.this);
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

                if (type == 0) {
                    IsLive_save(true);
                    loginDetails_save(false);
                } else {
                    IsLive_save(false);
                    loginDetails_save(false);
                }


                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                // use live ip here

            }
        });
        TextView dialogButton2 = (TextView) dialog.findViewById(R.id.btn_d2);
        dialogButton2.setText(strBtn2);
        dialogButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


        dialog.show();
    }


    private final static int REQUEST_SCANNER = 1;
    public final static String CONTENT = "content";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCANNER) {
            if (data != null) {
                //  tv_scaan.setText(data.getStringExtra(CONTENT) + "");


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

    private void getUserIDByEmail(String email)
    {
        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.GetUserIDByEmail(email);

//        mProgressDialog = new ProgressDialog(CustomerAddActivity.this);
//        progressBarHandler = new ProgressBarHandler(mProgressDialog,"Customer");
//        progressBarHandler.show(mProgressDialog);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response)
            {

                if(response.isSuccessful()) {

                    int result = GetResult(response);
                    Toast.makeText(getApplicationContext(), String.valueOf(result), Toast.LENGTH_SHORT).show();
                    //SharedPreferencesSingletonStringKey.getInstance(Main_View.this).put(SharedPreferencesSingletonStringKey.Key.UserID, result);


                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }

                // progressBarHandler.hide(mProgressDialog);
                //finish();

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                // close it after response
                //progressBarHandler.hide(mProgressDialog);

            }
        });

    }


    private int GetResult(Response<Object> response) {

        Gson gson = new Gson();
        String json = gson.toJson(response.body());
        return Integer.parseInt(json);

    }

}