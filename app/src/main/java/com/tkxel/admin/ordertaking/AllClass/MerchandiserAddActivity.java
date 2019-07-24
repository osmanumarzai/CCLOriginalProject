package com.tkxel.admin.ordertaking.AllClass;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tkxel.admin.ordertaking.Fragments.MyDatePickerFragment;
import com.tkxel.admin.ordertaking.ModelClass.Merchandiser;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.Api;
import com.tkxel.admin.ordertaking.Utilities.ProgressBarHandler;
import com.tkxel.admin.ordertaking.Utilities.RetrofitClient;
import com.tkxel.admin.ordertaking.Utilities.SharedPreferencesSingletonStringKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchandiserAddActivity extends AppCompatActivity{

    private EditText inputName,inputCNIC,inputMobile,inputAddressArea,inputCity;
//    inputFatherName,;

//    public EditText  inputDOB;

    private TextInputLayout inputLayoutName, inputLayoutCNIC, inputLayoutMobile,inputLayoutAddressArea,inputLayoutDOB,inputLayoutCity;
         //   inputLayoutFatherName;
    private Button btn_save;

    int UserID;

    private  String Name,CNIC,Mobile,Address,DOB,Channel,City;
//            FatherName;

    ProgressBarHandler progressBarHandler;
    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchandiser_add);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_Name);
        inputLayoutCNIC = (TextInputLayout) findViewById(R.id.input_layout_CNIC);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.input_layout_Mobile);
//        inputLayoutFatherName = (TextInputLayout) findViewById(R.id.input_layout_FatherName);
        inputLayoutAddressArea = (TextInputLayout) findViewById(R.id.input_layout_AddressArea);
//        inputLayoutDOB = (TextInputLayout) findViewById(R.id.input_layout_DOB);
        inputLayoutCity = (TextInputLayout) findViewById(R.id.input_layout_City);


        inputName = (EditText) findViewById(R.id.input_Name);
        inputCNIC = (EditText) findViewById(R.id.input_CNIC);
        inputMobile = (EditText) findViewById(R.id.input_Mobile);
//        inputFatherName = (EditText) findViewById(R.id.input_FatherName);
        inputAddressArea = (EditText) findViewById(R.id.input_AddressArea);
//        inputDOB = (EditText) findViewById(R.id.input_DOB);
        inputCity = (EditText) findViewById(R.id.input_City);


//        inputDOB = (EditText) findViewById(R.id.input_DOB);

        btn_save = (Button) findViewById(R.id.btn_save);

        inputName.addTextChangedListener(new MerchandiserAddActivity.MyTextWatcher(inputName));
        inputCNIC.addTextChangedListener(new MerchandiserAddActivity.MyTextWatcher(inputCNIC));
        inputMobile.addTextChangedListener(new MerchandiserAddActivity.MyTextWatcher(inputMobile));
//        inputFatherName.addTextChangedListener(new MerchandiserAddActivity.MyTextWatcher(inputFatherName));
        inputAddressArea.addTextChangedListener(new MerchandiserAddActivity.MyTextWatcher(inputAddressArea));
//        inputDOB.addTextChangedListener(new MerchandiserAddActivity.MyTextWatcher(inputDOB));
        inputCity.addTextChangedListener(new MerchandiserAddActivity.MyTextWatcher(inputCity));

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        setListener();

        UserID = SharedPreferencesSingletonStringKey.getInstance(MerchandiserAddActivity.this).getInt(SharedPreferencesSingletonStringKey.Key.UserID);


    }
    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateName()) {
            return;
        }
        if (!validateCNIC()) {
            return;
        }
        if (!validateMobile()) {
            return;
        }
//        if (!validateFatherName()) {
//            return;
//        }
        if (!validateAddressArea()) {
            return;
        }
//        if (!validateDOB()) {
//            return;
//        }
        if (!validateCity()) {
            return;
        }

        Name =  inputName.getText().toString();
        CNIC =inputCNIC.getText().toString();
        Mobile =inputMobile.getText().toString();
//        FatherName =inputFatherName.getText().toString();
        Address =inputAddressArea.getText().toString();
//        DOB = (String) inputDOB.getText().toString();
        City = (String) inputCity.getText().toString();


        Merchandiser merchandiser = new Merchandiser(Name, CNIC, Mobile, "father", Address, "2019-01-01", UserID,City);

        insertMerchandiser(merchandiser);

        //Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }


    private void insertMerchandiser(Merchandiser merchandiser)
    {

        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.PostMerchandiser(merchandiser);

        mProgressDialog = new ProgressDialog(MerchandiserAddActivity.this);
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

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCNIC() {
        if (inputCNIC.getText().toString().trim().isEmpty()) {
            inputLayoutCNIC.setError(getString(R.string.err_msg_cnic));
            requestFocus(inputCNIC);
            return false;
        } else {
            inputLayoutCNIC.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobile() {
        if (inputMobile.getText().toString().trim().isEmpty()) {
            inputLayoutMobile.setError(getString(R.string.err_msg_mobile));
            requestFocus(inputMobile);
            return false;
        } else {
            inputLayoutMobile.setErrorEnabled(false);
        }

        return true;
    }

//    private boolean validateFatherName() {
//        if (inputFatherName.getText().toString().trim().isEmpty()) {
//            inputLayoutFatherName.setError(getString(R.string.err_msg_fathername));
//            requestFocus(inputFatherName);
//            return false;
//        } else {
//            inputLayoutFatherName.setErrorEnabled(false);
//        }
//
//        return true;
//    }

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

//    private boolean validateDOB() {
//        if (inputDOB.getText().toString().trim().isEmpty()) {
//            inputLayoutDOB.setError(getString(R.string.err_msg_dob));
//            requestFocus(inputDOB);
//            return false;
//        } else {
//            inputLayoutDOB.setErrorEnabled(false);
//        }
//
//        return true;
//    }

    private boolean validateCity() {
        if (inputCity.getText().toString().trim().isEmpty()) {
            inputLayoutCity.setError(getString(R.string.err_msg_city));
            requestFocus(inputCity);
            return false;
        } else {
            inputLayoutCity.setErrorEnabled(false);
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
                case R.id.input_Name:
                    validateName();
                    break;
                case R.id.input_CNIC:
                    validateCNIC();
                    break;
                case R.id.input_Mobile:
                    validateMobile();
                    break;
//                case R.id.input_FatherName:
//                    validateFatherName();
//                    break;
                case R.id.input_AddressArea:
                    validateAddressArea();
                    break;
//                case R.id.input_DOB:
//                    validateDOB();
//                    break;
                case R.id.input_City:
                    validateCity();
                    break;
            }
        }
    }

    public void showDatePicker(View v) {
        DialogFragment newFragment = new MyDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }


    private void setListener() {

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

}