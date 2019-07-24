package com.tkxel.admin.ordertaking.LoadingClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.AllClass.Main_View_Offline;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.ModelClass.LoginDetails;
import com.tkxel.admin.ordertaking.ModelClass.Product_model;
import com.tkxel.admin.ordertaking.Utilities.DialogsUtils;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tkxel.admin.ordertaking.Utilities.MyConstants.listLoginDetails;

/**
 * Created by Administrator on 01-Jan-16.
 */
public class Offline_LoginTask extends AsyncTask<String, Void, String> {
    public Context context;
    String saveJson;
    DatabaseHelper databaseHelper;
    boolean isOffline = false;

    public Offline_LoginTask(Context context, String saveJson, boolean isOffline) {
        this.context = context;
        this.saveJson = saveJson;
        this.isOffline = isOffline;
        databaseHelper = new DatabaseHelper(context);

    }

    ProgressDialog myDialog;

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        DialogsUtils dialogsUtils = new DialogsUtils(this.context);
        myDialog = dialogsUtils.showProgressDialog(this.context, "Login wait..." + "");

    }

    String responseData;

    @Override
    protected String doInBackground(String... params) {
        try {
            responseData = saveJson;
            Log.i("External_u", responseData + "");
        } catch (Exception e) {
        }
        return responseData + "";
    }

    JSONObject obj;

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String reuslt) {
        myDialog.dismiss();
        listLoginDetails.clear();
        try {
            obj = new JSONObject(reuslt);

            if (obj.optBoolean("isValid")) {
                LoginDetails loginDetails = new LoginDetails();
                loginDetails.set_EmployeeId(obj.optInt("EmployeeId"));
                loginDetails.set_Name(obj.optString("Name"));
                loginDetails.set_RegionId(obj.optString("RegionId"));
                loginDetails.set_Email(obj.optString("Email"));
                listLoginDetails.add(loginDetails);

                // Here Call Database function to Retrieve All Customer and Products

                if (isOffline) {

                    MyConstants.listCustomer = databaseHelper.getAllUser();
                    MyConstants.mArrayProducts = databaseHelper.getAllProducts();

                    if (MyConstants.listCustomer.size() > 0) {

                        if (MyConstants.mArrayProducts.size() > 0) {

                            Intent intent = new Intent(context, Main_View_Offline.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        } else {
                            Toast.makeText(context, "Please connect to  Internet", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, "Please connect to  Internet", Toast.LENGTH_SHORT).show();
                    }
                } else {



                    Get_AllProducts_Task backroundTask_coupon_catgory = new Get_AllProducts_Task(
                            context, 0);
                    ArrayList<Product_model> params_catgory = new ArrayList<Product_model>();
                    backroundTask_coupon_catgory.execute(params_catgory);



                }


            } else {
                Toast.makeText(context, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
