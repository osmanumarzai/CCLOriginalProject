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

import com.tkxel.admin.ordertaking.AdopterClass.Adapter_All_order;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.CityListDialog;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import java.util.ArrayList;

import static com.tkxel.admin.ordertaking.R.id.main_lisrview;


public class All_Order_List extends Activity implements View.OnClickListener {
    public static ListView listvew_allOrder;

    ///[3].Payable_balance
    public TextView tv_customer;
    ImageView imgnew_scy;
    DatabaseHelper databaseHelper;
    TextView tvoffline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order);
        databaseHelper = new DatabaseHelper(All_Order_List.this);
        fun_loads();
        setDialoguData();
        //imgnew_scy
        findViewById(R.id.add_new_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgnew_scy = (ImageView) findViewById(R.id.imgnew_scy);
        imgnew_scy.setOnClickListener(this);
        MyConstants.listOrderOffline = databaseHelper.getAllorder((int) MyConstants.listCustomer.get(0).get_CUSTOMER_ID() + "");

        if (MyConstants.listOrderOffline.size() > 0) {
            imgnew_scy.setVisibility(View.VISIBLE);
        } else {
            imgnew_scy.setVisibility(View.GONE);
        }

    }

    int[] colors = {0, 0xFF4B0082, 0}; // red for the example

    void fun_loads() {
        tvoffline = (TextView) findViewById(R.id.tvoffline);
        tvoffline.setVisibility(View.GONE);

        tv_customer = (TextView) findViewById(R.id.tv_customer);
        tv_customer.setOnClickListener(this);

        findViewById(R.id.imgnew_order).setOnClickListener(this);
        listvew_allOrder = (ListView) findViewById(main_lisrview);
        listvew_allOrder.setAdapter(new Adapter_All_order(All_Order_List.this, MyConstants.mOrderListByCustomer, 1));
        listvew_allOrder.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        listvew_allOrder.setDividerHeight(1);


    }


    ArrayList<String> araList_warwhouse;
    String[] array;
    Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgnew_scy:
                intent = new Intent(getApplicationContext(), All_Order_List_Offline.class);
                startActivity(intent);

                break;
            case R.id.imgnew_order:

                for (int i = 0; i < MyConstants.mArrayProducts.size(); i++) {
                    MyConstants.mArrayProducts.get(i).setisSelected(false);
                    MyConstants.mArrayProducts.get(i).set_isBox(false);
                    MyConstants.mArrayProducts.get(i).setCount(0);
                }
                intent = new Intent(getApplicationContext(), Take_Order_Activity.class);
                startActivity(intent);


//                if (isConnected()) {
//                    Get_AllProducts_Task backroundTask_coupon_catgory = new Get_AllProducts_Task(
//                            All_Order_List.this, 0);
//                    ArrayList<Product_model> params_catgory = new ArrayList<Product_model>();
//                    backroundTask_coupon_catgory.execute(params_catgory);
//                } else {
//                    Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
//                }


                break;
            case R.id.tv_customer:
                cityListDialog.show();
                break;


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

    void setDialoguData() {
        araList_warwhouse = new ArrayList<String>();
        for (int i = 0; i < MyConstants.listCustomer.size(); i++) {
            araList_warwhouse.add(MyConstants.listCustomer.get(i).get_CUSTOMER_NAME());
        }
        array = araList_warwhouse.toArray(new String[araList_warwhouse.size()]);
        if (array.length > 0)
            tv_customer.setText(array[0] + "");
        cityListDialog = new CityListDialog(All_Order_List.this, array, tv_customer, 100);
    }

    CityListDialog cityListDialog;
}