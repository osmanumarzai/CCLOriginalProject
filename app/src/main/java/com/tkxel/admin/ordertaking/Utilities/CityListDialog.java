package com.tkxel.admin.ordertaking.Utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.AdopterClass.Adapter_All_orderOffLine;
import com.tkxel.admin.ordertaking.AllClass.All_Order_List_Offline;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.LoadingClass.GetOrderBySalesPersonId_Task;
import com.tkxel.admin.ordertaking.LoadingClass.Get_AllPayment_Task;
import com.tkxel.admin.ordertaking.ModelClass.PaymentPostmodel;
import com.tkxel.admin.ordertaking.R;

import java.util.ArrayList;

import static com.tkxel.admin.ordertaking.Utilities.MyConstants.listCustomer;

/**
 * Created by admin on 11/10/2017.
 */
public class CityListDialog extends Dialog implements View.OnClickListener {

    private ListView list;
    private EditText filterText = null;
    ArrayAdapter<String> adapter = null;
    private static final String TAG = "CityList";
    public int curent_userID = 0;
    private DatabaseHelper databaseHelper;
    Context mcontext;

    public CityListDialog(Context context, String[] cityList, final TextView tv, final int type_1) {
        super(context);

        setContentView(R.layout.citylistview);

        mcontext = context;

        databaseHelper = new DatabaseHelper(context);
        this.setTitle("Select Customer");
        filterText = (EditText) findViewById(R.id.EditBox);
        filterText.addTextChangedListener(filterTextWatcher);
        list = (ListView) findViewById(R.id.List);

        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, cityList);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {


                filterText.setText("");
                if (type_1 == 100) {
                    get_position(position);
                } else if (type_1 == 1111) {
                    get_offline(position);
                } else if (type_1 == 400) {
                    if (isConnected()) {
                        Get_AllPayment_Task backroundTask_coupon_catgory = new Get_AllPayment_Task(
                                mcontext, 1, (int) MyConstants.listCustomer.get(position).get_CUSTOMER_ID());
                        ArrayList<PaymentPostmodel> params_catgory = new ArrayList<PaymentPostmodel>();
                        backroundTask_coupon_catgory.execute(params_catgory);
                    } else {
                        Toast.makeText(mcontext, "No Network Connection !", Toast.LENGTH_LONG).show();
                    }

                } else if (type_1 == 200) {

                    MyConstants.customerpostionDepp=position;
                }
                quitDialog(v);
                curent_userID = position;
                tv.setText(list.getItemAtPosition(position) + "");
            }
        });
    }


    public void quitDialog(View v) {
        if (isShowing()) dismiss();
    }

    @Override
    public void onClick(View v) {
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            adapter.getFilter().filter(s);
        }
    };

    @Override
    public void onStop() {
        filterText.removeTextChangedListener(filterTextWatcher);
    }
// 0322 4772494

    public void get_position(int position) {
        if (listCustomer != null) {
            int customerid = (int) MyConstants.listCustomer.get(position).get_CUSTOMER_ID();
            new GetOrderBySalesPersonId_Task(mcontext, customerid + "", 1).execute();
        }

    }

    int vari = 0;

    public void get_offline(int position) {

        if (position == 0) {
            MyConstants.listOrderOffline.clear();
            MyConstants.listOrderOffline = databaseHelper.getAllorderNoCustomer();
            All_Order_List_Offline.listvew_allOrder.setAdapter(new Adapter_All_orderOffLine(mcontext, MyConstants.listOrderOffline, 1));
        } else {
            vari = position - 1;

            if (listCustomer != null) {
                MyConstants.listOrderOffline.clear();
                MyConstants.listOrderOffline = databaseHelper.getAllorder((int) MyConstants.listCustomer.get(vari).get_CUSTOMER_ID() + "");
                All_Order_List_Offline.listvew_allOrder.setAdapter(new Adapter_All_orderOffLine(mcontext, MyConstants.listOrderOffline, 1));
            }
        }


    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) mcontext.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}