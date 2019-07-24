package com.tkxel.admin.ordertaking.LoadingClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.ModelClass.LoginDetails;
import com.tkxel.admin.ordertaking.ModelClass.Product_model;
import com.tkxel.admin.ordertaking.Utilities.DialogsUtils;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;
import com.tkxel.admin.ordertaking.Utilities.ProgressBarHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tkxel.admin.ordertaking.Utilities.MyConstants.listLoginDetails;

/**
 * Created by Administrator on 01-Jan-16.
 */
public class LoginTask extends AsyncTask<String, Void, String> {
    public Context context;
    ProgressBarHandler mProgressBarHandler;

    String Username, Password;
    boolean isSave = false;

    public LoginTask(Context context, String Username, String Password, boolean isSave) {

        this.context = context;
        this.Username = Username;
        this.Password = Password;
        this.isSave = isSave;

    }

    ProgressDialog myDialog;

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        DialogsUtils dialogsUtils = new DialogsUtils(this.context);
        myDialog = dialogsUtils.showProgressDialog(this.context, "Login wait..." + "");

    }

    boolean IsLive_save() {
        sharedPreferences_form1 = this.context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        return sharedPreferences_form1.getBoolean("is_Live", false);

    }

    String responseData;

    String myurls;

    @Override
    protected String doInBackground(String... params) {
        try {
            HttpClient httpclient = new DefaultHttpClient();

            if (IsLive_save()) {
                //myurls = (MyConstants.used_IP_Live + "/api/IsUserValid/" + Username + "/" + Password).trim().replace(" ", "%20");

                myurls = (MyConstants.used_IP_Live + "/api/IsUserValid/" + Username + "/" + Password).trim().replace(" ", "%20");

                Log.i("External_u", MyConstants.used_IP_Live + "/api/IsUserValid/" + Username + "/" + Password+  "");

            } else {


                //myurls = (MyConstants.used_IP + "/api/IsUserValid/" + Username + "/" + Password).trim().replace(" ", "%20");

                myurls = (MyConstants.used_IP + "/api/IsUserValid/" + Username + "/" + Password).trim().replace(" ", "%20");
                Log.i("External_u", MyConstants.used_IP + "/api/IsUserValid/" + Username + "/" + Password+  "");
            }
            Log.e("External_u", myurls);
            HttpGet httpget = new HttpGet(myurls);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            responseData = EntityUtils.toString(entity);
            Log.e("External_u", responseData + "");

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
            //{"EmployeeId":1000013,"Name":"Khuram javeed","RegionId":"15","isValid":true,"Email":"khuram@gmail.com"}
            if (obj.optBoolean("isValid")) {
                loginDetails_save(true);
                loginDetails_save(reuslt);
                LoginDetails loginDetails = new LoginDetails();
                loginDetails.set_EmployeeId(obj.optInt("EmployeeId"));
                loginDetails.set_Name(obj.optString("Name"));
                loginDetails.set_RegionId(obj.optString("RegionId"));
                loginDetails.set_Email(obj.optString("Email"));
                listLoginDetails.add(loginDetails);

//                Get_AllCustomer_Task backroundTask_coupon_catgory = new Get_AllCustomer_Task(
//                        context, 1, MyConstants.listLoginDetails.get(0).get_EmployeeId(), MyConstants.listLoginDetails.get(0).get_RegionId());
//                ArrayList<ModelCustomer> params_catgory = new ArrayList<ModelCustomer>();
//                backroundTask_coupon_catgory.execute(params_catgory);


                Get_AllProducts_Task backroundTask_coupon_catgory = new Get_AllProducts_Task(
                        context, 0);
                ArrayList<Product_model> params_catgory = new ArrayList<Product_model>();
                backroundTask_coupon_catgory.execute(params_catgory);
            } else {
                Toast.makeText(context, "Invalid Username or Password111", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    SharedPreferences sharedPreferences_form1;

    void loginDetails_save(boolean b) {
        sharedPreferences_form1 = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences_form1.edit();
        editor.putBoolean("is_save", b);
        editor.commit();
    }

    void loginDetails_save(String saveJson) {
        sharedPreferences_form1 = context.getSharedPreferences("saveJson", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences_form1.edit();
        editor.putString("saveJson", saveJson);
        editor.commit();
    }


}
