package com.tkxel.admin.ordertaking.AllClass;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.gson.Gson;
import com.tkxel.admin.ordertaking.ModelClass.Customer;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.Api;
import com.tkxel.admin.ordertaking.Utilities.ProgressBarHandler;
import com.tkxel.admin.ordertaking.Utilities.RetrofitClient;
import com.tkxel.admin.ordertaking.Utilities.SharedPreferencesSingletonStringKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerAddActivity extends AppCompatActivity{


    private EditText inputShopName, inputShopKeeperName, inputContactNum,inputAddressArea,inputCity,inputArea;
    private TextInputLayout inputLayoutShopName, inputLayoutShopKeeperName, inputLayoutContactNum,inputLayoutAddressArea,inputLayoutCity,inputLayoutArea;
    private Button btn_save;
    private Spinner spinner1;

    private  String ShopName,ShopKeeperName,Channel,ContactNum,AddressArea,City,Area;
    private int UserID;

    ProgressBarHandler progressBarHandler;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add);

        inputLayoutShopName = (TextInputLayout) findViewById(R.id.input_layout_ShopName);
        inputLayoutShopKeeperName = (TextInputLayout) findViewById(R.id.input_layout_ShopKeeperName);
        inputLayoutContactNum = (TextInputLayout) findViewById(R.id.input_layout_ContactNum);
        inputLayoutAddressArea = (TextInputLayout) findViewById(R.id.input_layout_AddressArea);
        inputLayoutCity = (TextInputLayout) findViewById(R.id.input_layout_City);
        inputLayoutArea = (TextInputLayout) findViewById(R.id.input_layout_Area);


        inputShopName = (EditText) findViewById(R.id.input_ShopName);
        inputShopKeeperName = (EditText) findViewById(R.id.input_ShopKeeperName);
        inputContactNum = (EditText) findViewById(R.id.input_ContactNum);
        inputAddressArea = (EditText) findViewById(R.id.input_AddressArea);
        inputCity = (EditText) findViewById(R.id.input_City);
        inputArea = (EditText) findViewById(R.id.input_Area);


        btn_save = (Button) findViewById(R.id.btn_save);
        spinner1 = (Spinner) findViewById(R.id.spinner1);

        inputShopName.addTextChangedListener(new CustomerAddActivity.MyTextWatcher(inputShopName));
        inputShopKeeperName.addTextChangedListener(new CustomerAddActivity.MyTextWatcher(inputShopKeeperName));
        inputContactNum.addTextChangedListener(new CustomerAddActivity.MyTextWatcher(inputContactNum));
        inputAddressArea.addTextChangedListener(new CustomerAddActivity.MyTextWatcher(inputAddressArea));
        inputCity.addTextChangedListener(new CustomerAddActivity.MyTextWatcher(inputLayoutCity));
        inputArea.addTextChangedListener(new CustomerAddActivity.MyTextWatcher(inputLayoutArea));


        setListener();

        UserID = SharedPreferencesSingletonStringKey.getInstance(CustomerAddActivity.this).getInt(SharedPreferencesSingletonStringKey.Key.UserID);

    }


    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateShopName()) {
            return;
        }

        if (!validateShopKeeperName()) {
            return;
        }

        if (!validateContactNum()) {
            return;
        }
        if (!validateAddressArea()) {
            return;
        }
        if (!validateCity()) {
            return;
        }
        if (!validateArea()) {
            return;
        }

        ShopName =  inputShopName.getText().toString();
        Channel = String.valueOf(spinner1.getSelectedItem());
        ShopKeeperName =  inputShopKeeperName.getText().toString();
        ContactNum =  inputContactNum.getText().toString();
        AddressArea =  inputAddressArea.getText().toString();
        City =  inputCity.getText().toString();
        Area =  inputArea.getText().toString();

         Customer cust = new Customer(ShopName, Channel, ShopKeeperName, ContactNum,AddressArea,UserID,City,Area);

         insertCustomer(cust);

        //Toast.makeText(getApplicationContext(), "you", Toast.LENGTH_SHORT).show();

    }

    private void insertCustomer(Customer customer)
    {

        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.PostCustomer(customer);

        mProgressDialog = new ProgressDialog(CustomerAddActivity.this);
        progressBarHandler = new ProgressBarHandler(mProgressDialog,"Customer");
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

    private boolean validateShopName() {
        if (inputShopName.getText().toString().trim().isEmpty()) {
            inputLayoutShopName.setError(getString(R.string.err_msg_shopname));
            requestFocus(inputShopName);
            return false;
        } else {
            inputLayoutShopName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateShopKeeperName() {
        if (inputShopKeeperName.getText().toString().trim().isEmpty()) {
            inputLayoutShopKeeperName.setError(getString(R.string.err_msg_shopkeepername));
            requestFocus(inputShopKeeperName);
            return false;
        } else {
            inputLayoutShopKeeperName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateContactNum() {
        if (inputContactNum.getText().toString().trim().isEmpty()) {
            inputLayoutContactNum.setError(getString(R.string.err_msg_contactnum));
            requestFocus(inputContactNum);
            return false;
        } else {
            inputLayoutContactNum.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAddressArea() {
        if (inputAddressArea.getText().toString().trim().isEmpty()) {
            inputLayoutAddressArea.setError(getString(R.string.err_msg_addressarea));
            requestFocus(inputAddressArea);
            return false;
        } else {
            inputLayoutAddressArea.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCity() {
        if (inputCity.getText().toString().trim().isEmpty()) {
            inputLayoutCity.setError(getString(R.string.err_msg_city));
            requestFocus(inputAddressArea);
            return false;
        } else {
            inputLayoutCity.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateArea() {
        if (inputArea.getText().toString().trim().isEmpty()) {
            inputLayoutArea.setError(getString(R.string.err_msg_area));
            requestFocus(inputArea);
            return false;
        } else {
            inputLayoutArea.setErrorEnabled(false);
        }

        return true;
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
                case R.id.input_ShopName:
                    validateShopName();
                    break;
                case R.id.input_ShopKeeperName:
                    validateShopKeeperName();
                    break;
                case R.id.input_ContactNum:
                    validateContactNum();
                    break;
                case R.id.input_AddressArea:
                    validateAddressArea();
                    break;
                case R.id.input_City:
                    validateCity();
                    break;

                case R.id.input_Area:
                    validateArea();
                    break;
            }
        }
    }

    private void setListener() {

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

}

