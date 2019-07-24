package com.tkxel.admin.ordertaking.AllClass;

/**
 * Created by admin on 10/16/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.AdopterClass.Adapter_All_orderOffLine;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.LoadingClass.Order_MultiPostTask;
import com.tkxel.admin.ordertaking.ModelClass.OfflineDetails_ArrModel;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.CityListDialog;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import java.util.ArrayList;
import java.util.List;

import static com.tkxel.admin.ordertaking.R.id.main_lisrview;


public class All_Order_List_Offline extends Activity implements View.OnClickListener {
    public static ListView listvew_allOrder;


    public TextView tv_customer, tvtile;

    DatabaseHelper databaseHelper;
    TextView tvoffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order);
        databaseHelper = new DatabaseHelper(All_Order_List_Offline.this);
        fun_loads();
        setDialoguData();
        findViewById(R.id.add_new_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgnew_scy = (ImageView) findViewById(R.id.imgnew_scy);
        imgnew_scy.setOnClickListener(this);
    }

    ImageView imgnew_scy;
    int[] colors = {0, 0xFF4B0082, 0}; // red for the example

    void fun_loads() {

        tvoffline = (TextView) findViewById(R.id.tvoffline);
        tvoffline.setVisibility(View.VISIBLE);


        tv_customer = (TextView) findViewById(R.id.tv_customer);
        tvtile = (TextView) findViewById(R.id.tvtile);
        tvtile.setText("Pending Orders");
        tv_customer.setOnClickListener(this);
        findViewById(R.id.imgnew_order).setOnClickListener(this);
        listvew_allOrder = (ListView) findViewById(main_lisrview);
        MyConstants.listOrderOffline.clear();
        // here is customer base
        // MyConstants.listOrderOffline = databaseHelper.getAllorder((int) MyConstants.listCustomer.get(0).get_CUSTOMER_ID() + "");

        MyConstants.listOrderOffline = databaseHelper.getAllorderNoCustomer();



//
        listvew_allOrder.setAdapter(new Adapter_All_orderOffLine(All_Order_List_Offline.this, MyConstants.listOrderOffline, 1));
        listvew_allOrder.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        listvew_allOrder.setDividerHeight(1);
    }

    public List<OfflineDetails_ArrModel> AllOrderList = new ArrayList<>();
    OfflineDetails_ArrModel offlineDetails_arrModel;

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    ArrayList<String> araList_warwhouse;
    String[] array;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgnew_scy:
                if (isConnected()) {
                    AllOrderList.clear();
                    for (int i = 0; i < MyConstants.listOrderOffline.size(); i++) {
                        offlineDetails_arrModel = new OfflineDetails_ArrModel();
                        offlineDetails_arrModel.set_customerId(MyConstants.listOrderOffline.get(i).get_USER_ID());

                        offlineDetails_arrModel.set_Region(MyConstants.listOrderOffline.get(i).get_Region());
                        offlineDetails_arrModel.set_SalesPersonName(MyConstants.listOrderOffline.get(i).get_SalesPersonName());
                        offlineDetails_arrModel.set_strLat(MyConstants.listOrderOffline.get(i).get_strLat());
                        offlineDetails_arrModel.set_strLog(MyConstants.listOrderOffline.get(i).get_strLog());
                        offlineDetails_arrModel.set_Location(MyConstants.listOrderOffline.get(i).get_Location());




                        offlineDetails_arrModel.set_CrdtLimit(MyConstants.listOrderOffline.get(i).get_CrdLimit()+"");
                        offlineDetails_arrModel.set_TottleAmount(MyConstants.listOrderOffline.get(i).get_TotleAmount()+"");
                        offlineDetails_arrModel.setDetailsList(databaseHelper.getAllOrderDetails_2(MyConstants.listOrderOffline.get(i).getId()));


                        AllOrderList.add(offlineDetails_arrModel);
                    }




                    new Order_MultiPostTask(All_Order_List_Offline.this, MyConstants.listLoginDetails.get(0).get_EmployeeId(), 0, AllOrderList).execute();
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.imgnew_order:
                for (int i = 0; i < MyConstants.mArrayProducts.size(); i++) {
                    MyConstants.mArrayProducts.get(i).setisSelected(false);
                    MyConstants.mArrayProducts.get(i).set_isBox(false);
                    MyConstants.mArrayProducts.get(i).setCount(0);
                    MyConstants.mArrayProducts.get(i).set_tottle_Price(0);
                }
                Intent intent = new Intent(getApplicationContext(), Take_Order_Activity_Offline.class);
                startActivity(intent);
                break;
            case R.id.tv_customer:
                cityListDialog.show();
                break;

        }

    }

    void setDialoguData() {
        araList_warwhouse = new ArrayList<String>();
        araList_warwhouse.add("All");
        for (int i = 0; i < MyConstants.listCustomer.size(); i++) {
            araList_warwhouse.add(MyConstants.listCustomer.get(i).get_CUSTOMER_NAME());
        }
        array = araList_warwhouse.toArray(new String[araList_warwhouse.size()]);
        if (array.length > 0)
            tv_customer.setText(array[0] + "");
        cityListDialog = new CityListDialog(All_Order_List_Offline.this, array, tv_customer, 1111);
    }

    CityListDialog cityListDialog;
}