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

import com.tkxel.admin.ordertaking.AdopterClass.CustomGrid;
import com.tkxel.admin.ordertaking.LoadingClass.Get_EmployeeTarget_Task;
import com.tkxel.admin.ordertaking.ModelClass.EmployeeTarget;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import java.util.ArrayList;


/**
 * Created by admin on 10/28/2017.
 */

public class Main_View_Offline extends Activity {
    GridView grid;
    private int TIME_DELAY = 2000;
    private long back_pressed;
    String[] web = {
            "New Order",
            "Order List",
            "Customers",
            "Price List",
            "Statistics",
            "Payment History",
            "Target"


    };
    int[] imageId_2 = {
            R.drawable.neworder,
            R.drawable.orderlist,
            R.drawable.customer_list,
            R.drawable.price_list,
            R.drawable.graph_list,
            R.drawable.payment_list,
            R.drawable.target_list


    };

    TextView tv1;
    TextView tvoffline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_view_class);

        tv1 = (TextView) findViewById(R.id.tv1);
        tvoffline = (TextView) findViewById(R.id.tvoffline);
        tvoffline.setVisibility(View.VISIBLE);

        // tv1.setText("Welcome " + MyConstants.listLoginDetails.get(0).get_Name());


        CustomGrid adapter = new CustomGrid(Main_View_Offline.this, web, imageId_2);
        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                fun_Calling(position);


            }
        });

        findViewById(R.id.imgnew_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main_View_Offline.this, LoginScreen.class);
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
            case 0:
                for (int i = 0; i < MyConstants.mArrayProducts.size(); i++) {
                    MyConstants.mArrayProducts.get(i).setisSelected(false);
                    MyConstants.mArrayProducts.get(i).set_isBox(false);
                    MyConstants.mArrayProducts.get(i).setCount(0);
                    MyConstants.mArrayProducts.get(i).set_tottle_Price(0);
                }
                intent = new Intent(getApplicationContext(), Take_Order_Activity_Offline.class);
                startActivity(intent);

                break;
            case 1:
                // Live
                intent = new Intent(getApplicationContext(), All_Order_List_Offline.class);
                startActivity(intent);

                break;
            case 2:
                intent = new Intent(getApplicationContext(), AllCustomer_list.class);
                startActivity(intent);

                break;
            case 3:
                for (int i = 0; i < MyConstants.mArrayProducts.size(); i++) {
                    MyConstants.mArrayProducts.get(i).setisSelected(false);
                    MyConstants.mArrayProducts.get(i).set_isBox(false);
                    MyConstants.mArrayProducts.get(i).setCount(0);
                    MyConstants.mArrayProducts.get(i).set_tottle_Price(0);
                }
                Intent intent = new Intent(getApplicationContext(), All_PriceList.class);
                startActivity(intent);

                break;
            case 4:
                if (isConnected()) {
                    Get_EmployeeTarget_Task get_employeeTarget_task = new Get_EmployeeTarget_Task(
                            Main_View_Offline.this, 1);
                    ArrayList<EmployeeTarget> params_catgory1 = new ArrayList<EmployeeTarget>();
                    get_employeeTarget_task.execute(params_catgory1);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
                }

                break;
            case 5:

                intent = new Intent(getApplicationContext(), All_PaymentList_Offline.class);
                intent.putExtra("isOnline", false);

                startActivity(intent);

                break;

            case 6:


                if (isConnected()) {
                    Get_EmployeeTarget_Task get_employeeTarget_task = new Get_EmployeeTarget_Task(
                            Main_View_Offline.this, 0);
                    ArrayList<EmployeeTarget> params_catgory1 = new ArrayList<EmployeeTarget>();
                    get_employeeTarget_task.execute(params_catgory1);

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
                }

                break;
            case 7:
                if (!IsLive_save()) {
                    Gernal_Dialog("Change Server Setting", "Are you sure you want to go for live server", "OK", "CANCEL", 0);
                } else {
                    Gernal_Dialog("Change Server Setting", "Are you sure you want to go for Local server", "OK", "CANCEL", 1);
                }
                break;

        }
    }


    public void Gernal_Dialog(String Title1, String Title, String strBtn, String strBtn2, final int type) {

        // custom dialog
        final Dialog dialog = new Dialog(Main_View_Offline.this);
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
}