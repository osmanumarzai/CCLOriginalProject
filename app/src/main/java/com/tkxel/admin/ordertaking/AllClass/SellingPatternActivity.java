package com.tkxel.admin.ordertaking.AllClass;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tkxel.admin.ordertaking.AdopterClass.AdapterSpinShop;
import com.tkxel.admin.ordertaking.ModelClass.SellingPattern;
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

public class SellingPatternActivity extends AppCompatActivity {

    List<Shop> mListShop= new ArrayList<>();
    private Spinner shopSpinner;
    private AdapterSpinShop adapterSpinShop;

    private Button btn_save;
    ProgressBarHandler progressBarHandler;
    ProgressDialog mProgressDialog;

    private EditText inputOTC, inputRX;
    private TextInputLayout inputLayoutOTC, inputLayoutRX;


    private  int ShopID;
    private  int OTC,RX;
    int UserID;

    int CustomerID = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_pattern);

        btn_save = (Button) findViewById(R.id.btn_save);

        inputLayoutOTC = (TextInputLayout) findViewById(R.id.input_layout_OTC);
        inputLayoutRX= (TextInputLayout) findViewById(R.id.input_layout_RX);
        inputOTC = (EditText) findViewById(R.id.input_OTC);
        inputRX = (EditText) findViewById(R.id.input_RX);

        shopSpinner = (Spinner) findViewById(R.id.sp_shop);


        inputOTC.addTextChangedListener(new SellingPatternActivity.MyTextWatcher(inputOTC));
        inputRX.addTextChangedListener(new SellingPatternActivity.MyTextWatcher(inputRX));

        CustomerID = SharedPreferencesSingletonStringKey.getInstance(SellingPatternActivity.this).getInt(SharedPreferencesSingletonStringKey.Key.CustomerID);

        populateShopSpinner();

        setListener();

        UserID = SharedPreferencesSingletonStringKey.getInstance(SellingPatternActivity.this).getInt(SharedPreferencesSingletonStringKey.Key.UserID);


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

                    adapterSpinShop = new AdapterSpinShop(SellingPatternActivity.this,android.R.layout.simple_list_item_1, mListShop);

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
//                            Toast.makeText(SellingPatternActivity.this, "ID: " + shop.getId() + "\nName: " + shop.getName(),
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

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateOTC()) {
            return;
        }

        if (!validateRX()) {
            return;
        }


        OTC =  Integer.parseInt(inputOTC.getText().toString());
//        Channel = String.valueOf(shopSpinner.getSelectedItem());
        RX =  Integer.parseInt(inputRX.getText().toString()) ;



        SellingPattern sellingpattern = new SellingPattern(ShopID,1,OTC,RX,UserID);
        insertSellingPattern(sellingpattern);



        //Toast.makeText(getApplicationContext(), "you", Toast.LENGTH_SHORT).show();

    }

    private void insertSellingPattern(SellingPattern sellingpattern)
    {

        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.PostSellingPattern(sellingpattern);

        mProgressDialog = new ProgressDialog(SellingPatternActivity.this);
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

    private boolean validateOTC() {
        if (inputOTC.getText().toString().trim().isEmpty()) {
            inputLayoutOTC.setError("OTC");
            requestFocus(inputOTC);
            return false;
        } else {
            inputLayoutOTC.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateRX() {
        if (inputRX.getText().toString().trim().isEmpty()) {
            inputLayoutRX.setError("RX");
            requestFocus(inputRX);
            return false;
        } else {
            inputLayoutRX.setErrorEnabled(false);
        }

        return true;
    }

    private void setListener() {


        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_OTC:
                    validateOTC();
                    break;
                case R.id.input_RX:
                    validateRX();
                    break;
            }
        }
    }

}
