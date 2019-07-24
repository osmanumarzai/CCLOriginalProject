

package com.tkxel.admin.ordertaking.AllClass;

/**
 * Created by admin on 10/16/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.AdopterClass.Adapter_All_Payment;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.LoadingClass.Payment_PostMultiTask;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.CityListDialog;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import java.util.ArrayList;

import static com.tkxel.admin.ordertaking.Utilities.MyConstants.mListPayment;


public class All_PaymentList_Offline extends Activity implements View.OnClickListener {
    TextView tv_customer;
    //
    public static ListView main_lisrview;
    CityListDialog cityListDialog;
    DatabaseHelper databaseHelper;
    TextView tvoffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_payment);
        databaseHelper = new DatabaseHelper(All_PaymentList_Offline.this);

        tv_customer = (TextView) findViewById(R.id.tv_customer);
        tv_customer.setOnClickListener(this);

        tv_customer.setVisibility(View.GONE);
        tvoffline = (TextView) findViewById(R.id.tvoffline);
        tvoffline.setVisibility(View.VISIBLE);
        //  intent.putExtra("curent_userID", cityListDialog.curent_userID);


        main_lisrview = (ListView) findViewById(R.id.main_lisrview);
        mListPayment = databaseHelper.getAllPayments();
        main_lisrview.setAdapter(new Adapter_All_Payment(All_PaymentList_Offline.this, mListPayment, 1));

        findViewById(R.id.imgnew_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_PaymentList_Offline.this, Add_Payment_Class.class);
                intent.putExtra("isOnline", false);

                startActivity(intent);
            }
        });

        findViewById(R.id.add_new_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        findViewById(R.id.imgnew_scy).setOnClickListener(this);
        tv_header = (TextView) findViewById(R.id.tvoffline);
        tv_header.setText("Pending Payment");
        tv_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityListDialog.show();
            }
        });

        if (MyConstants.listCustomer != null)
            setDialoguData();

    }

    TextView tv_header;
    ArrayList<String> araList_warwhouse;
    String[] array;

    void setDialoguData() {
        araList_warwhouse = new ArrayList<String>();
        for (int i = 0; i < MyConstants.listCustomer.size(); i++) {
            araList_warwhouse.add(MyConstants.listCustomer.get(i).get_CUSTOMER_NAME());
        }
        array = araList_warwhouse.toArray(new String[araList_warwhouse.size()]);

        if (array.length > 0)
            tv_customer.setText(array[0] + "");
        cityListDialog = new CityListDialog(All_PaymentList_Offline.this, array, tv_customer, 400);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgnew_scy:
                if (isConnected()) {
                    if (MyConstants.mListPayment.size() > 0)
                        new Payment_PostMultiTask(All_PaymentList_Offline.this, MyConstants.mListPayment).execute();
                    else
                        Toast.makeText(All_PaymentList_Offline.this, "No Pending Order!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(All_PaymentList_Offline.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
                }

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


}