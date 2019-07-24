package com.tkxel.admin.ordertaking.AllClass;

/**
 * Created by admin on 10/16/2017.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.AdopterClass.Adapter_Order_View;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.LoadingClass.Order_PostTask;
import com.tkxel.admin.ordertaking.ModelClass.Order;
import com.tkxel.admin.ordertaking.ModelClass.Order_Details;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class View_Order_Activity extends Activity implements View.OnClickListener {
    ListView lisview_products;
    double totle_Qty = 0;
    double totoel_price = 0.0;
    int curent_userID = 0;
    TextView totl_qty, totleprice, cutomer_name_1, cutomer_name_2, customer_city, customer_address;

    String address_ = "";

    public double current_latitude=31.498282, current_longitude=74.321148;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order_view);
        databaseHelper = new DatabaseHelper(View_Order_Activity.this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            totoel_price = bundle.getDouble("totoel_price");

            current_latitude = bundle.getDouble("current_latitude");
            current_longitude = bundle.getDouble("current_longitude");

            address_ = bundle.getString("curent_address");
            curent_userID = bundle.getInt("curent_userID");
        }

        strLat = current_latitude + "";
        strLog = current_longitude + "";


        Log.i("SArdarG",strLat) ;
        Log.i("SArdarG",strLog) ;

        fun_loads();
        Log.i("curent_userID", "" + curent_userID);
        findViewById(R.id.add_new_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void fun_loads() {
        orer_address = (TextView) findViewById(R.id.orer_address);
        orer_address.setText("Address : " + address_);


        orer_date = (TextView) findViewById(R.id.orer_date);
        orer_date.setOnClickListener(this);

        cutomer_name_1 = (TextView) findViewById(R.id.cutomer_name_1);
        cutomer_name_2 = (TextView) findViewById(R.id.cutomer_name_2);
        customer_city = (TextView) findViewById(R.id.customer_city);
        customer_address = (TextView) findViewById(R.id.customer_address);
        Log.i("curent_userID", "1: " + curent_userID);
        cutomer_name_1.setText("Customer : " + MyConstants.listCustomer.get(curent_userID).get_CUSTOMER_NAME());

        cutomer_name_2.setText(MyConstants.listCustomer.get(curent_userID).get_CUSTOMER_NAME() + "");

        customer_city.setText("City : " + MyConstants.listCustomer.get(curent_userID).get_REGION() + "");
        if (MyConstants.listCustomer.get(curent_userID).get_ADDRESS().equalsIgnoreCase("null")) {
            customer_address.setText("No Address Found");
        } else {
            customer_address.setText(MyConstants.listCustomer.get(curent_userID).get_ADDRESS() + "");
        }


        findViewById(R.id.save_order).setOnClickListener(this);
        totl_qty = (TextView) findViewById(R.id.totl_qty);
        totleprice = (TextView) findViewById(R.id.totleprice);


        for (int i = 0; i < MyConstants.mArrayProducts.size(); i++) {
            totle_Qty = totle_Qty + MyConstants.mArrayProducts.get(i).getCount();

            //TkxelWifi
        }

        //totl_qty.setText("Qty " + totle_Qty + "");

        totl_qty.setText("Qty." + String.format("%.2f", round(totle_Qty, 2)) + "");

        // totleprice.setText("PRK." + totoel_price);
        totleprice.setText("PRK." + String.format("%.2f", round(totoel_price, 2)) + "");

        lisview_products = (ListView) findViewById(R.id.lisview_products);

        Adapter_Order_View adapter_tAkeOrder = new Adapter_Order_View(View_Order_Activity.this, MyConstants.mArrayProducts_selected);

        lisview_products.setAdapter(adapter_tAkeOrder);
        lisview_products.invalidateViews();
        fun_Date();
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.save_order:
                Log.i("curent_userID", "2: " + curent_userID);
                if (isConnected())
                    Gernal_Dialog("Submit Order", "Are you sure you want to submit Order ?", "OK", "CANCEL");
                    // new GetBlacmceTask(View_Order_Activity.this, (int) MyConstants.listCustomer.get(curent_userID).get_CUSTOMER_ID(), Double.parseDouble(totoel_price)).execute();
                else
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                //  Gernal_Dialog("Submit Order", "Are you sure you want to submit Order ?", "OK", "CANCEL");
                break;

            case R.id.orer_date:
                showDialog(DATE_DIALOG_ID);

                break;
        }
    }

    public void Gernal_Dialog(String Title1, String Title, String strBtn, String strBtn2) {

        // custom dialog
        final Dialog dialog = new Dialog(View_Order_Activity.this);
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

                if (isConnected()) {
                    addorder(true);

                    SalesPersonName = MyConstants.listLoginDetails.get(0).get_Name();
                    Region = MyConstants.listLoginDetails.get(0).get_RegionId();
                    LOCATION = address_;

                    FunSubmitOrder(MyConstants.listLoginDetails.get(0).get_EmployeeId(), (int) MyConstants.listCustomer.get(curent_userID).get_CUSTOMER_ID());

                } else {
                    addorder(false);
                }


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

    Order userOrder;

    DatabaseHelper databaseHelper;

    public void addorder(boolean isOnline) {
        userOrder = new Order();

        userOrder.set_USER_ID((int) MyConstants.listCustomer.get(curent_userID).get_CUSTOMER_ID());
        userOrder.set_Name("");

        userOrder.set_SalesPersonName(""+SalesPersonName);

        userOrder.set_strLat(""+strLat);
        userOrder.set_strLog(""+strLog);

        userOrder.set_Region(""+Region);





        userOrder.set_Location("" + address_);
        userOrder.set_PRICE("" + totoel_price);
        userOrder.set_QtyOrder("" + totle_Qty);
        userOrder.set_Status("OPEN");
        userOrder.set_customer(cutomer_name_2.getText() + "");
        userOrder.set_Date(currentdat + "");

        if (isOnline) {
            addOrderDetait(0000);
        } else {
            addOrderDetait((int) databaseHelper.addorder(userOrder));
        }


    }

    Order_Details order_details;

    List<Order_Details> mPDetails = new ArrayList<>();

    void addOrderDetait(int lstID) {
        Log.i("waheeddatabase", lstID + "");
        mPDetails.clear();
        for (int i = 0; i < MyConstants.mArrayProducts_selected.size(); i++) {
            order_details = new Order_Details();
            // order_details.setId(lstID);
            order_details.set_Order_ID(lstID);
            order_details.set_PRODUCT_ID(MyConstants.mArrayProducts_selected.get(i).get_ITEM_ID());
            order_details.set_itemName(MyConstants.mArrayProducts_selected.get(i).get_ITEM_DESC());
            order_details.set_itemPRICE(MyConstants.mArrayProducts_selected.get(i).get_PRICERATE() + "");
            order_details.set_itemQty(MyConstants.mArrayProducts_selected.get(i).getCount() + "");
            mPDetails.add(order_details);
            if (lstID == 0000) {

            } else {
                databaseHelper.add_OrderDetails(order_details);
            }

        }


    }


    private TextView orer_date, orer_address;
    private int year, month, day;
    String currentdat = "13-11-2017";

    void fun_Date() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        currentdat = new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" ") + "";

        orer_date.setText("Date : " + new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));


    }


    public String theMonth(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }


    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            currentdat = new StringBuilder()
                    // Month is 0 based, just add 1
                    .append(month + 1).append("-").append(day).append("-")
                    .append(year).append("") + "";

            // set selected date into textview
            orer_date.setText("Date : " + new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(""));

            // set selected date into datepicker also

        }
    };
    static final int DATE_DIALOG_ID = 999;


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month, day);
        }
        return null;
    }

    // listLoginDetails

    String SalesPersonName, LOCATION, strLat, strLog, Region;

    void FunSubmitOrder(int empId, int CustomerId) {
        Log.i("Sardar", "4: " + CustomerId);
        if (isConnected()) {
            //   Toast.makeText(getApplicationContext(), "Sardar" + CustomerId, Toast.LENGTH_SHORT).show();
            new Order_PostTask(View_Order_Activity.this, empId, mPDetails, CustomerId, MyConstants.listCustomer.get(curent_userID).get_CREDIT_LIMIT() + "", totoel_price + ""
                    , SalesPersonName, LOCATION, strLat, strLog, Region
            ).execute();

        } else
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
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