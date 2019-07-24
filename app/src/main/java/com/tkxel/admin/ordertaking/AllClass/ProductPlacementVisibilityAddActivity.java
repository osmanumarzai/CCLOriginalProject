package com.tkxel.admin.ordertaking.AllClass;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import com.tkxel.admin.ordertaking.AdopterClass.AdapterProductPlacementVisibility;
import com.tkxel.admin.ordertaking.AdopterClass.AdapterSpinShop;
import com.tkxel.admin.ordertaking.ModelClass.ProductLevel1;
import com.tkxel.admin.ordertaking.ModelClass.ProductPlacementVisibility;
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

public class ProductPlacementVisibilityAddActivity extends AppCompatActivity {

    List<Shop> mListShop= new ArrayList<>();
    private Spinner shopSpinner;
    private AdapterSpinShop adapterSpinShop;

    private  int ShopID;
    private Button btn_save;
    List<ProductLevel1> mListProduct= new ArrayList<>();

    public ListView productplacementvisibility_listview;

    ProgressBarHandler progressBarHandler;
    ProgressDialog mProgressDialog;

    int CustomerID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_placement_visibility);

        productplacementvisibility_listview  =  (ListView) findViewById(R.id.productplacementvisibility_listview);
        shopSpinner = (Spinner) findViewById(R.id.sp_shop);

        CustomerID = SharedPreferencesSingletonStringKey.getInstance(ProductPlacementVisibilityAddActivity.this).getInt(SharedPreferencesSingletonStringKey.Key.CustomerID);

        populateShopSpinner();
       getProductsPlacementVisibility();

        setListener();

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

                    adapterSpinShop = new AdapterSpinShop(ProductPlacementVisibilityAddActivity.this,android.R.layout.simple_list_item_1, mListShop);

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
//                            Toast.makeText(ProductPlacementVisibilityAddActivity.this, "ID: " + shop.getId() + "\nName: " + shop.getName(),
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

    private void getProductsPlacementVisibility() {
        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.GetProductsLevel1();

        mProgressDialog = new ProgressDialog(ProductPlacementVisibilityAddActivity.this);
        progressBarHandler = new ProgressBarHandler(mProgressDialog,"Product");
        progressBarHandler.show(mProgressDialog);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if(response.isSuccessful()) {


                    mListProduct= GetProductLevel1List(response);
                    //productplacementvisibility_listview.setAdapter(new AdapterAllProductPlacementVisibility(ProductPlacementVisibilityAddActivity.this, mListProduct, 1));
                    productplacementvisibility_listview.setAdapter(new AdapterProductPlacementVisibility(ProductPlacementVisibilityAddActivity.this, mListProduct, 1));

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

                int UserID = SharedPreferencesSingletonStringKey.getInstance(ProductPlacementVisibilityAddActivity.this).getInt(SharedPreferencesSingletonStringKey.Key.UserID);


                List<ProductPlacementVisibility> productPlacementVisibilities = new ArrayList<ProductPlacementVisibility>();

                for (int x = 0; x < productplacementvisibility_listview.getChildCount(); x++) {
                    CheckBox ch_box1 = (CheckBox) productplacementvisibility_listview.getChildAt(x).findViewById(R.id.ch_box1);
                    CheckBox ch_box2 = (CheckBox) productplacementvisibility_listview.getChildAt(x).findViewById(R.id.ch_box2);
                    CheckBox ch_box3 = (CheckBox) productplacementvisibility_listview.getChildAt(x).findViewById(R.id.ch_box3);
                    CheckBox ch_box4 = (CheckBox) productplacementvisibility_listview.getChildAt(x).findViewById(R.id.ch_box4);
                    CheckBox ch_box5 = (CheckBox) productplacementvisibility_listview.getChildAt(x).findViewById(R.id.ch_box5);

                    EditText et_ProductID = (EditText) productplacementvisibility_listview.getChildAt(x).findViewById(R.id.et_ProductID);

                    int p_id = Integer.parseInt(et_ProductID.getText().toString());


                    int ch1 = 0, ch2 = 0, ch3 = 0, ch4 = 0,ch5 = 0;

                    if (ch_box1.isChecked() || ch_box2.isChecked() || ch_box3.isChecked() || ch_box4.isChecked() || ch_box5.isChecked()) {


                    if (ch_box1.isChecked()) {
                        ch1 = 1;
                    }

                    if (ch_box2.isChecked()) {
                        ch2 = 1;
                    }

                    if (ch_box3.isChecked()) {
                        ch3 = 1;
                    }

                    if (ch_box4.isChecked()) {
                        ch4 = 1;
                    }
                    if (ch_box5.isChecked()) {
                            ch5 = 1;
                    }


                    productPlacementVisibilities.add(new ProductPlacementVisibility(ShopID , UserID, p_id, ch1, ch2, ch3, ch4,ch5));

                }
            }



                insertProductPlacementVisibilities(productPlacementVisibilities);


            }
        });

    }

    private void insertProductPlacementVisibilities(List<ProductPlacementVisibility> productPlacementVisibilities) {
        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.PostProductPlacementVisibility(productPlacementVisibilities);

        mProgressDialog = new ProgressDialog(ProductPlacementVisibilityAddActivity.this);
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
