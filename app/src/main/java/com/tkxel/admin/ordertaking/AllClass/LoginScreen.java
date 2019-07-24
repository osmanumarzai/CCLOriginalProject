package com.tkxel.admin.ordertaking.AllClass;

/**
 * Created by admin on 10/16/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.LoadingClass.LoginTask;
import com.tkxel.admin.ordertaking.LoadingClass.Offline_LoginTask;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.Api;
import com.tkxel.admin.ordertaking.Utilities.InputValidation;
import com.tkxel.admin.ordertaking.Utilities.ProgressBarHandler;
import com.tkxel.admin.ordertaking.Utilities.RetrofitClient;
import com.tkxel.admin.ordertaking.Utilities.SharedPreferencesSingletonStringKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginScreen extends Activity implements View.OnClickListener {

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    EditText et_userName, et_password;

    // aabbasi@gmail.com
    //12345


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);
        initObjects();
        LoadIDs();
        findViewById(R.id.btn_login).setOnClickListener(this);


    }

    private void LoadIDs() {
        et_userName = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(LoginScreen.this);
        inputValidation = new InputValidation(LoginScreen.this);

    }

    Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                if (isConnected()) {
                    verifyFromSQLite();


                } else {
                    // Offline Apps
                    if (get_jsonDetails().equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), "No Internet Connection !!", Toast.LENGTH_LONG).show();
                    } else {
                        new Offline_LoginTask(LoginScreen.this, get_jsonDetails(), true).execute();
                    }
                }

                break;
        }
    }






    private void verifyFromSQLite() {

        if (!inputValidation.isInputEditTextFilled(et_userName, "Enter E-mail")) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(et_password, "Enter Password")) {
            return;
        }


        // 03247714241
//        if (!inputValidation.isInputEditTextEmail(et_userName, "Please Enter Valid Email")) {
//            return;
//        }
        new LoginTask(LoginScreen.this, et_userName.getText() + "", et_password.getText() + "", false).execute();
    }


    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            Log.d("info","connect successed");
            return true;
        }

        else
        {
            Log.d("info","connect failed");
            return false;

        }

    }

    SharedPreferences sharedPreferences_form1;

    boolean getlogin() {
        sharedPreferences_form1 = getSharedPreferences("Login", Context.MODE_PRIVATE);
        return sharedPreferences_form1.getBoolean("is_save", false);

    }

    String get_jsonDetails() {
        sharedPreferences_form1 = getSharedPreferences("saveJson", Context.MODE_PRIVATE);
        return sharedPreferences_form1.getString("saveJson", "");

    }


}