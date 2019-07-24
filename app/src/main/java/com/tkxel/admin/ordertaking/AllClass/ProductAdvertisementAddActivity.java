package com.tkxel.admin.ordertaking.AllClass;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tkxel.admin.ordertaking.AdopterClass.AdapterAllProductAdvertisement;
import com.tkxel.admin.ordertaking.AdopterClass.AdapterSpinShop;
import com.tkxel.admin.ordertaking.ModelClass.ProductAdvertisement;
import com.tkxel.admin.ordertaking.ModelClass.ProductLevel1;
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


public class ProductAdvertisementAddActivity extends AppCompatActivity {

    private int  ShopID ;
    List<Shop> mListShop= new ArrayList<>();
    private Spinner shopSpinner;
    private AdapterSpinShop adapterSpinShop;

    private Button btn_save;
    List<ProductLevel1> mListProductAdvertisement = new ArrayList<>();

    public ListView productadvertisement_listview;

    ProgressBarHandler progressBarHandler;
    ProgressDialog mProgressDialog;


    int CustomerID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_advertisement_add);

        setListener();

        productadvertisement_listview  =  (ListView) findViewById(R.id.productadvertisement_listview);

        shopSpinner = (Spinner) findViewById(R.id.sp_shop);

        CustomerID = SharedPreferencesSingletonStringKey.getInstance(ProductAdvertisementAddActivity.this).getInt(SharedPreferencesSingletonStringKey.Key.CustomerID);

        populateShopSpinner();
        getProductsLevel1();

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

//        mProgressDialog = new ProgressDialog(ProductAdvertisementAddActivity.this);
//        progressBarHandler = new ProgressBarHandler(mProgressDialog,"Shop");
//        progressBarHandler.show(mProgressDialog);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if(response.isSuccessful()) {

                    mListShop= GetShopList(response);

                    adapterSpinShop = new AdapterSpinShop(ProductAdvertisementAddActivity.this,android.R.layout.simple_list_item_1, mListShop);

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
//                            Toast.makeText(ProductAdvertisementAAddActivity.this, "ID: " + shop.getId() + "\nName: " + shop.getName(),
//                                    Toast.LENGTH_SHORT).show();
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

//                progressBarHandler.hide(mProgressDialog);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
//                progressBarHandler.hide(mProgressDialog);
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

    private void getProductsLevel1() {

        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.GetProductsLevel1();

        mProgressDialog = new ProgressDialog(ProductAdvertisementAddActivity.this);
        progressBarHandler = new ProgressBarHandler(mProgressDialog,"Product");
        progressBarHandler.show(mProgressDialog);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if(response.isSuccessful()) {

//                    mListProductAdvertisement.clear();
                    mListProductAdvertisement= GetProductLevel1List(response);
                    productadvertisement_listview.setAdapter(new AdapterAllProductAdvertisement(ProductAdvertisementAddActivity.this, mListProductAdvertisement, 1));


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

    private List<ProductLevel1> GetProductLevel1List(Response<Object> response) {

        //ArrayList<ProductLevel1> mArrayList = new ArrayList<ProductLevel1>();

        List<ProductLevel1> mList = new ArrayList<>();
        Gson gson = new Gson();
        String json = gson.toJson(response.body());
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(json);

            mList = Arrays.asList(gson.fromJson(jArray.toString(), ProductLevel1[].class));

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        for (int i=0 ; i<mList.size() ; i++)
//        {
//            mArrayList.add(mList.get(i));
//        }
//
//        return mArrayList;

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

                int UserID = SharedPreferencesSingletonStringKey.getInstance(ProductAdvertisementAddActivity.this).getInt(SharedPreferencesSingletonStringKey.Key.UserID);
                List<ProductAdvertisement> mproductAdvertisementList = new ArrayList<ProductAdvertisement>();

                for (int x = 0; x < productadvertisement_listview.getChildCount(); x++) {
                    EditText et_1 = (EditText) productadvertisement_listview.getChildAt(x).findViewById(R.id.et_1);
                    EditText et_2 = (EditText) productadvertisement_listview.getChildAt(x).findViewById(R.id.et_2);
                    EditText et_3 = (EditText) productadvertisement_listview.getChildAt(x).findViewById(R.id.et_3);
//                    EditText et_4 = (EditText)productadvertisement_listview.getChildAt(x).findViewById(R.id.et_4);
                    EditText et_5 = (EditText) productadvertisement_listview.getChildAt(x).findViewById(R.id.et_5);
                    EditText et_6 = (EditText) productadvertisement_listview.getChildAt(x).findViewById(R.id.et_6);
                    EditText et_7 = (EditText) productadvertisement_listview.getChildAt(x).findViewById(R.id.et_7);
//                    EditText et_8 = (EditText) productadvertisement_listview.getChildAt(x).findViewById(R.id.et_8);

                    EditText et_ProductID = (EditText) productadvertisement_listview.getChildAt(x).findViewById(R.id.et_ProductID);

                    String st_1, st_2, st_3, st_4, st_5, st_6, st_7;
//                            ,st_8;
                    int p_id;

                    st_1 = et_1.getText().toString();
                    st_2 = et_2.getText().toString();
                    st_3 = et_3.getText().toString();
//                    st_4 = et_4.getText().toString();
                    st_5 = et_5.getText().toString();
                    st_6 = et_6.getText().toString();
                    st_7 = et_7.getText().toString();
//                    st_8 = et_8.getText().toString();

                    p_id = Integer.parseInt(et_ProductID.getText().toString());

                    int in_1 = 0, in_2 = 0, in_3 = 0, in_4 = 0, in_5 = 0, in_6 = 0, in_7 = 0;
//                            ,in_8 = 0;

                    if (!TextUtils.isEmpty(st_1) || !TextUtils.isEmpty(st_2) || !TextUtils.isEmpty(st_3) || !TextUtils.isEmpty(st_5) || !TextUtils.isEmpty(st_6) || !TextUtils.isEmpty(st_7)) {
//                            || !TextUtils.isEmpty(st_8)


                        if (!TextUtils.isEmpty(st_1)) {
                            in_1 = Integer.parseInt(st_1);
                        }

                        if (!TextUtils.isEmpty(st_2)) {
                            in_2 = Integer.parseInt(st_2);
                        }

                        if (!TextUtils.isEmpty(st_3)) {
                            in_3 = Integer.parseInt(st_3);
                        }

//                        if (st_4 != "") {
//                            in_4 = Integer.parseInt(st_4);
//                        }

                        if (!TextUtils.isEmpty(st_5)) {
                            in_5 = Integer.parseInt(st_5);
                        }

                        if (!TextUtils.isEmpty(st_6)) {
                            in_6 = Integer.parseInt(st_6);
                        }

                        if (!TextUtils.isEmpty(st_7)) {
                            in_7 = Integer.parseInt(st_7);
                        }

//                        if (!TextUtils.isEmpty(st_8)) {
//                            in_8 = Integer.parseInt(st_8);
//                        }

                        mproductAdvertisementList.add(new ProductAdvertisement(ShopID, UserID, p_id, in_1, in_2, in_3, 0, in_5, in_6, in_7,1));

                    }
                }

                insertProductAdvertisements(mproductAdvertisementList);



            }
        });

    }


    private void insertProductAdvertisements(List<ProductAdvertisement> productadvertisement)
    {
        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.PostProductAdvertisement(productadvertisement);

        mProgressDialog = new ProgressDialog(ProductAdvertisementAddActivity.this);
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

}


