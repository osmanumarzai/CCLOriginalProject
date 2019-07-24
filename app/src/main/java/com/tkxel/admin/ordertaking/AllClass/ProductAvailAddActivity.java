package com.tkxel.admin.ordertaking.AllClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tkxel.admin.ordertaking.AdopterClass.AdapterAllProduct;
import com.tkxel.admin.ordertaking.AdopterClass.AdapterBrandAvail;
import com.tkxel.admin.ordertaking.AdopterClass.AdapterSpinShop;
import com.tkxel.admin.ordertaking.ModelClass.Product;
import com.tkxel.admin.ordertaking.ModelClass.ProductAvailability;
import com.tkxel.admin.ordertaking.ModelClass.Shop;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.Api;
import com.tkxel.admin.ordertaking.Utilities.ProgressBarHandler;
import com.tkxel.admin.ordertaking.Utilities.RetrofitClient;
import com.tkxel.admin.ordertaking.Utilities.SharedPreferencesSingletonStringKey;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAvailAddActivity extends Activity implements View.OnClickListener {

    List<Shop> mListShop= new ArrayList<>();
    List<Product> mListProduct = new ArrayList<>();
    public ListView productavail_listview;

    private Spinner shopSpinner;
    private AdapterSpinShop adapterSpinShop;

    private Button btn_save;

    ProgressBarHandler progressBarHandler;
    ProgressDialog mProgressDialog;

    int CustomerID = -1;

    private  int ShopID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productavail_add);

        setListener();

        productavail_listview  =  (ListView) findViewById(R.id.productavail_listview);
        shopSpinner = (Spinner) findViewById(R.id.sp_shop);


        CustomerID = SharedPreferencesSingletonStringKey.getInstance(ProductAvailAddActivity.this).getInt(SharedPreferencesSingletonStringKey.Key.CustomerID);


        populateShopSpinner();
        getProductsAvail();
    }

    private void selectValue(int customerID) {
        for (int i = 0; i < shopSpinner.getCount(); i++) {
            Shop shop = (Shop) shopSpinner.getItemAtPosition(i);

             if (shop.getId() == customerID ) {
                shopSpinner.setSelection(i);
                break;
            }

        }
    }

    private void populateShopSpinner()
    {
        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.GetShops();

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if(response.isSuccessful()) {

                    mListShop= GetShopList(response);

                    adapterSpinShop = new AdapterSpinShop(ProductAvailAddActivity.this,android.R.layout.simple_list_item_1, mListShop);

                    shopSpinner.setAdapter(adapterSpinShop); // Set the custom adapter to the spinner


                    if (CustomerID != -1)
                    {
                        selectValue(CustomerID);
                    }

                    shopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                Shop shop = adapterSpinShop.getItem(position);
                ShopID = shop.getId();
//                Toast.makeText(ProductAvailAddActivity.this, "ID: " + shop.getId() + "\nName: " + shop.getName(),
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

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

    private void getProductsAvail() {
        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.GetProducts();

        mProgressDialog = new ProgressDialog(ProductAvailAddActivity.this);
        progressBarHandler = new ProgressBarHandler(mProgressDialog,"Product");
        progressBarHandler.show(mProgressDialog);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if(response.isSuccessful()) {


                    mListProduct= GetProductList(response);
                    productavail_listview.setAdapter(new AdapterBrandAvail(ProductAvailAddActivity.this, mListProduct,1));

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

    private List<Product> GetProductList(Response<Object> response) {

        List<Product> mList = new ArrayList<>();
        Gson gson = new Gson();
        String json = gson.toJson(response.body());
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(json);

            mList = Arrays.asList(gson.fromJson(jArray.toString(), Product[].class));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mList;
    }


    private void setListener() {

        btn_save = (Button) findViewById(R.id.btn_save);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = 0;

                List<Integer> mProductIDList = new ArrayList<Integer>();

                for (int x = 0; x<productavail_listview.getChildCount();x++){
                    CheckBox  ch_box1 = (CheckBox)productavail_listview.getChildAt(x).findViewById(R.id.ch_box1);
                    EditText et_ProductID = (EditText)productavail_listview.getChildAt(x).findViewById(R.id.et_ProductID);

                    if(ch_box1.isChecked()){


                        count++;
                        mProductIDList.add(Integer.valueOf(et_ProductID.getText().toString()) );

                    }
                }

                List<ProductAvailability> productAvailabilities = new ArrayList();

                int UserID = SharedPreferencesSingletonStringKey.getInstance(ProductAvailAddActivity.this).getInt(SharedPreferencesSingletonStringKey.Key.UserID);


                for (int i = 0 ; i <mProductIDList.size(); i++ )
                {
                    productAvailabilities.add(new ProductAvailability(ShopID,UserID, mProductIDList.get(i),1));

                }

                insertProductAvailability(productAvailabilities);

                //Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void insertProductAvailability(List<ProductAvailability> productAvailability)
    {

        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.PostProductAvailability(productAvailability);

        mProgressDialog = new ProgressDialog(ProductAvailAddActivity.this);
        progressBarHandler = new ProgressBarHandler(mProgressDialog,"Saving");
        progressBarHandler.show(mProgressDialog);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response)
            {

                if(response.isSuccessful()) {


                    int result = GetResult(response);
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }

                progressBarHandler.hide(mProgressDialog);
                finish();


            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                // close it after response
                progressBarHandler.hide(mProgressDialog);

            }
        });

    }

    private int GetResult(Response<Object> response) {

        Gson gson = new Gson();
        String json = gson.toJson(response.body());
        return 1;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //   case R.id.btn_sync:



        }

    }
}
