package com.tkxel.admin.ordertaking.AllClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.support.v7.widget.SearchView;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tkxel.admin.ordertaking.AdopterClass.AdapterAllCustomer;
import com.tkxel.admin.ordertaking.AdopterClass.AdapterSpinShop;
import com.tkxel.admin.ordertaking.ModelClass.Customer;
import com.tkxel.admin.ordertaking.ModelClass.Shop;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.Api;
import com.tkxel.admin.ordertaking.Utilities.ProgressBarHandler;
import com.tkxel.admin.ordertaking.Utilities.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerListActivity extends Activity implements View.OnClickListener  {

    private RecyclerView customer_recycler_view;
    private AdapterAllCustomer adapterAllCustomer;
    ArrayList<Customer> mListCustomer = new ArrayList<>();

    ProgressBarHandler progressBarHandler;
    ProgressDialog mProgressDialog;

    private Spinner ShopByCitySpinner;
    private AdapterSpinShop AdapterSpinShopByCity;


    private Spinner ShopByAreaSpinner;
    private AdapterSpinShop AdapterSpinShopByArea;

    // Search EditText
    SearchView inputSearch,inputCity,inputArea;
//    EditText inputSearch;

    List<Shop> mListShopByCity = new ArrayList<>();
    List<Shop> mListShopByArea = new ArrayList<>();

    String City,Area;

    private Button btn_load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        ShopByCitySpinner = (Spinner) findViewById(R.id.sp_city);
        ShopByAreaSpinner = (Spinner) findViewById(R.id.sp_area);
        btn_load = (Button) findViewById(R.id.btn_load);

        setListener();

        customer_recycler_view  =  (RecyclerView) findViewById(R.id.customer_recycler_view);
        inputSearch =  (SearchView) findViewById(R.id.et_searchbox);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CustomerListActivity.this);
        customer_recycler_view.setLayoutManager(layoutManager);
        customer_recycler_view.setItemAnimator(new DefaultItemAnimator());

        //SEARCH
        inputSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                adapterAllCustomer.getFilter().filter(query);
                return false;
            }
        });


        populateCitySpinner();

    }

    private void populateAreaSpinner(String city)
    {

        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.GetShopsByArea(city);

        mProgressDialog = new ProgressDialog(CustomerListActivity.this);
        progressBarHandler = new ProgressBarHandler(mProgressDialog,"City");
        progressBarHandler.show(mProgressDialog);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if(response.isSuccessful()) {

                    mListShopByArea= GetShopList(response);

                    AdapterSpinShopByArea= new AdapterSpinShop(CustomerListActivity.this,android.R.layout.simple_list_item_1, mListShopByArea);

                    ShopByAreaSpinner.setAdapter(AdapterSpinShopByArea); // Set the custom adapter to the spinner

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }

                progressBarHandler.hide(mProgressDialog);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                progressBarHandler.hide(mProgressDialog);
            }
        });


    }

    private void populateCitySpinner()
    {

        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.GetShopsCity();

        mProgressDialog = new ProgressDialog(CustomerListActivity.this);
        progressBarHandler = new ProgressBarHandler(mProgressDialog,"City");
        progressBarHandler.show(mProgressDialog);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if(response.isSuccessful()) {

                    mListShopByCity = GetShopList(response);

                    AdapterSpinShopByCity = new AdapterSpinShop(CustomerListActivity.this,android.R.layout.simple_list_item_1, mListShopByCity);

                    ShopByCitySpinner.setAdapter(AdapterSpinShopByCity); // Set the custom adapter to the spinner





                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }

                progressBarHandler.hide(mProgressDialog);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                progressBarHandler.hide(mProgressDialog);
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



    private void GetCustomerByCityArea(String city,String area) {
        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.GetCustomerByCityArea(city,area);

        mProgressDialog = new ProgressDialog(CustomerListActivity.this);
        progressBarHandler = new ProgressBarHandler(mProgressDialog,"Customer");
        progressBarHandler.show(mProgressDialog);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if(response.isSuccessful()) {

                    mListCustomer.clear();
                    mListCustomer = GetCustomerList(response);

                    adapterAllCustomer  = new AdapterAllCustomer(CustomerListActivity.this, mListCustomer);

                    //ADAPTER
                    final AdapterAllCustomer adapter=new AdapterAllCustomer(CustomerListActivity.this,mListCustomer);
                    customer_recycler_view.setAdapter(adapter);
                    customer_recycler_view.setAdapter(adapterAllCustomer);

                    // refreshing recycler view
                    adapterAllCustomer.notifyDataSetChanged();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }

                progressBarHandler.hide(mProgressDialog);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                // close it after response
                progressBarHandler.hide(mProgressDialog);

            }
        });

    }

    private ArrayList<Customer> GetCustomerList(Response<Object> response) {

        ArrayList<Customer> mArrayList = new ArrayList<Customer>();

        List<Customer> mList = new ArrayList<>();
        Gson gson = new Gson();
        String json = gson.toJson(response.body());
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(json);

            mList = Arrays.asList(gson.fromJson(jArray.toString(), Customer[].class));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        for (int i=0 ; i<mList.size() ; i++)
        {
            mArrayList.add(mList.get(i));
        }

        return mArrayList;
    }


    private void setListener() {

        findViewById(R.id.imgnew_customer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerListActivity.this, CustomerAddActivity.class);
                intent.putExtra("isOnline", true);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        ShopByCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                 Shop shop = AdapterSpinShopByCity.getItem(position);
                 City  = shop.getName();
                 populateAreaSpinner(City);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });



        ShopByAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                Shop shop = AdapterSpinShopByArea.getItem(position);
                Area = shop.getName();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetCustomerByCityArea(City,Area);
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
          //  case R.id.btn_sync:


        }

    }


//    @Override
//    public void onCustomerSelected(Customer customer) {
//        Toast.makeText(getApplicationContext(), "Selected: " + customer.getShopName(), Toast.LENGTH_LONG).show();
//    }
}

