

package com.tkxel.admin.ordertaking.AllClass;

/**
 * Created by admin on 10/16/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.AdopterClass.Adapter_All_Payment;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.ModelClass.PaymentPostmodel;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.CityListDialog;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import java.util.ArrayList;
import java.util.List;


public class All_PaymentList extends Activity implements View.OnClickListener {
    TextView tv_customer;
    List<PaymentPostmodel> mListPayment = new ArrayList<>();

    public static ListView main_lisrview;
    CityListDialog cityListDialog;
    TextView tvoffline, tv_header;
    ImageView imgnew_scy;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_payment);
        databaseHelper = new DatabaseHelper(All_PaymentList.this);


        tvoffline = (TextView) findViewById(R.id.tvoffline);
        tv_header = (TextView) findViewById(R.id.tvoffline);
        tv_header.setText("Payment History");
        tvoffline.setVisibility(View.GONE);


        tv_customer = (TextView) findViewById(R.id.tv_customer);
        tv_customer.setOnClickListener(this);

        tv_customer.setVisibility(View.GONE);

        //  intent.putExtra("curent_userID", cityListDialog.curent_userID);


        main_lisrview = (ListView) findViewById(R.id.main_lisrview);

        main_lisrview.setAdapter(new Adapter_All_Payment(All_PaymentList.this, MyConstants.mListPayment, 1));

        findViewById(R.id.imgnew_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_PaymentList.this, Add_Payment_Class.class);
                intent.putExtra("isOnline", true);
                startActivity(intent);
            }
        });

        findViewById(R.id.add_new_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tv_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityListDialog.show();
            }
        });

        if (MyConstants.listCustomer != null)
            setDialoguData();


        imgnew_scy = (ImageView) findViewById(R.id.imgnew_scy);
        imgnew_scy.setOnClickListener(this);

        mListPayment.clear();
        mListPayment = databaseHelper.getAllPayments();

        if (mListPayment.size() > 0) {
            imgnew_scy.setVisibility(View.VISIBLE);
        } else {
            imgnew_scy.setVisibility(View.GONE);
        }

    }

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

        cityListDialog = new CityListDialog(All_PaymentList.this, array, tv_customer, 400);
    }

    Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgnew_scy:
                intent = new Intent(All_PaymentList.this, All_PaymentList_Offline.class);
                startActivity(intent);

                break;
        }
    }


}