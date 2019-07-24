

package com.tkxel.admin.ordertaking.AllClass;

/**
 * Created by admin on 10/16/2017.
 */

import android.app.Activity;
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
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.AdopterClass.ItemListAdapter;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.CityListDialog;
import com.tkxel.admin.ordertaking.Utilities.GPSTracker;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Take_Order_Activity extends Activity implements View.OnClickListener, SearchView.OnQueryTextListener {

    ListView main_lisrview;


    private SearchView mSearchView;
    TextView tv_Tprice, totoel_qty;
    double totleprice = 0.0;

    DatabaseHelper databaseHelper;
    TextView tvoffline;
    EditText edt;
    boolean isClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_takeorder);
        dialog = new ProgressDialog(Take_Order_Activity.this);
        gps = new GPSTracker(Take_Order_Activity.this);
        main_lisrview = (ListView) findViewById(R.id.main_lisrview);
        mSearchView = (SearchView) findViewById(R.id.searchView1);
        edt = (EditText) findViewById(R.id.edit);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
        mSearchView.setFocusable(false);
        mSearchView.setVisibility(View.GONE);
        tvoffline = (TextView) findViewById(R.id.tvoffline);
        tvoffline.setVisibility(View.GONE);
        tv_Tprice = (TextView) findViewById(R.id.totoel_price);
        totoel_qty = (TextView) findViewById(R.id.totoel_qty);
        tv_customer = (TextView) findViewById(R.id.tv_customer);
        tv_customer.setOnClickListener(this);

        findViewById(R.id.save_order).setOnClickListener(this);
        databaseHelper = new DatabaseHelper(Take_Order_Activity.this);
        try {
            findLaction();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fun_SetData();
        if (MyConstants.listCustomer != null)
            setDialoguData();
        findViewById(R.id.add_new_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    ArrayList<String> araList_warwhouse;
    String[] array;
    CostChangedListener costChangedListener = new CostChangedListener() {
        @Override
        public void onCostChanged(double pRice, double qty) {

            totleprice = round(pRice, 2);
            pRice = round(pRice, 2);


            if (pRice == -0.00) {
                totleprice = 0.0;
                pRice = 0.0;
                //String.format("%.2f", d)
                tv_Tprice.setText("PKR. " + String.format("%.2f", pRice));
                totleprice = Double.parseDouble(String.format("%.2f", pRice));
            } else {
                totleprice = Double.parseDouble(String.format("%.2f", pRice));
                tv_Tprice.setText("PKR. " + String.format("%.2f", pRice));
            }

            fun_setQty();
        }


    };
    double totle_Qty = 0;
    double price_T = 0.0;

    void fun_setQty() {
        totle_Qty = 0;
        price_T = 0;
        for (int i = 0; i < MyConstants.mArrayProducts.size(); i++) {
            totle_Qty = totle_Qty + MyConstants.mArrayProducts.get(i).getCount();
            price_T = price_T + MyConstants.mArrayProducts.get(i).get_tottle_Price();

        }

        // totoel_qty.setText("QTY. " + totle_Qty);

        totoel_qty.setText("QTY. " + String.format("%.2f", totle_Qty));

        tv_Tprice.setText("PKR. " + String.format("%.2f", price_T));

        totleprice = Double.parseDouble(String.format("%.2f", price_T));

    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    void fun_SetData() {
        adapter_tAkeOrder = new ItemListAdapter(Take_Order_Activity.this, 0, MyConstants.mArrayProducts, costChangedListener);
        main_lisrview.setAdapter(adapter_tAkeOrder);
        main_lisrview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        main_lisrview.setTextFilterEnabled(true);
        setupSearchView();
    }

    ItemListAdapter adapter_tAkeOrder;

    private void setupSearchView() {


        mSearchView.setIconifiedByDefault(true);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
//                 main_lisrview.invalidateViews();
                adapter_tAkeOrder.getFilter().filter(newText);
                adapter_tAkeOrder.notifyDataSetChanged();

                //   adapter_tAkeOrder.filterSArdar(newText);


                return true;
            }


        });


        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  adapter_tAkeOrder.getFilter().filter(newText);
                String text = edt.getText().toString().toLowerCase(Locale.getDefault());
                adapter_tAkeOrder.getFilter().filter(text);
                adapter_tAkeOrder.notifyDataSetChanged();

                // adapter_tAkeOrder.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public TextView tv_customer;
    CityListDialog cityListDialog;
    String address_ = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Done Order
            case R.id.save_order:
                //   adapter_tAkeOrder.getFilter().filter(" ");
                if (isClick) {
                    isClick = false;
                    edt.setText("");
                    main_lisrview.setSelectionAfterHeaderView();
                    // mSearchView
                    funPostdata();


                } else {

                }

                break;
            case R.id.tv_customer:
                cityListDialog.show();
                break;

        }
    }

    private int SPLASH_TIME_OUT = 1000;
    private ProgressDialog dialog;

    void funPostdata() {
        dialog.setMessage("Doing something, please wait.");
        dialog.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                MyConstants.mArrayProducts_selected.clear();
                MyConstants.mArrayProducts_selected = adapter_tAkeOrder.getCheckedItems();
                if (MyConstants.mArrayProducts_selected.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), View_Order_Activity.class);
                    intent.putExtra("totoel_price", totleprice);

                    intent.putExtra("current_latitude", current_latitude);
                    intent.putExtra("current_longitude", current_longitude);


                    intent.putExtra("curent_userID", cityListDialog.curent_userID);
                    intent.putExtra("curent_address", address_);


                    startActivity(intent);
                    isClick = true;
                    if (dialog != null)
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                } else {
                    Gernal_Dialog("No Product Select", "Please add at least one Product", "", "OK");
                }

            }
        }, SPLASH_TIME_OUT);
    }

    public double current_latitude, current_longitude;

    GPSTracker gps;

    void findLaction() throws IOException {

        LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            GpsSetting_Dialog("GPS SETTINGS", "Please enable GPS in order to use this app", "SETTINGS", "CANCEL");

        } else {
            gps = new GPSTracker(Take_Order_Activity.this);

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

    void Gernal_Dialog(String Title1, String Title, String strBtn, String strBtn2) {

        // custom dialog
        final Dialog dialog = new Dialog(Take_Order_Activity.this);
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
        dialogButton.setVisibility(View.GONE);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

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

    void GpsSetting_Dialog(String Title1, String Title, String strBtn, String strBtn2) {

        // custom dialog
        final Dialog dialog = new Dialog(Take_Order_Activity.this);
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

    void setDialoguData() {
        araList_warwhouse = new ArrayList<String>();
        for (int i = 0; i < MyConstants.listCustomer.size(); i++) {
            araList_warwhouse.add(MyConstants.listCustomer.get(i).get_CUSTOMER_NAME());
        }
        array = araList_warwhouse.toArray(new String[araList_warwhouse.size()]);

        if (array.length > 0)
            tv_customer.setText(array[0] + "");
        cityListDialog = new CityListDialog(Take_Order_Activity.this, array, tv_customer, 200);
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (TextUtils.isEmpty(newText)) {
            main_lisrview.clearTextFilter();
        } else {
            main_lisrview.setFilterText(newText);
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //main_lisrview.invalidateViews();
    }
}