package com.tkxel.admin.ordertaking.LoadingClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.ModelClass.PaymentPostmodel;
import com.tkxel.admin.ordertaking.Utilities.DialogsUtils;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 01-Jan-16.
 */
public class Payment_PostMultiTask extends AsyncTask<String, Void, String> {


    Context context;
    List<PaymentPostmodel> mListPayment;
    DatabaseHelper databaseHelper;

    public Payment_PostMultiTask(Context context, List<PaymentPostmodel> mListPayment
    ) {

        this.context = context;
        this.mListPayment = mListPayment;
        databaseHelper = new DatabaseHelper(this.context);
    }

    ProgressDialog myDialog;

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        DialogsUtils dialogsUtils = new DialogsUtils(this.context);
        myDialog = dialogsUtils.showProgressDialog(this.context, "Submit Order..." + "");
        myDialog.show();
    }

    SharedPreferences sharedPreferences_form1;

    boolean IsLive_save() {
        sharedPreferences_form1 = this.context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        return sharedPreferences_form1.getBoolean("is_Live", false);

    }

    @Override
    protected String doInBackground(String... params) {
        //http://smdpreview.com/Api_Mobile/CouponRatingReview/aa973342-eae9-4a48-9eea-2f02b21a6dd9/
        if (IsLive_save())
            return POST(MyConstants.used_IP_Live + "/api/PaymentReceived");
        else
            return POST(MyConstants.used_IP + "/api/PaymentReceived");

    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    public String POST(String url) {


        Log.i("External_u", url);
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);// change method
            String json = "";
            JSONObject jsonObject_main = null;
            JSONArray myArrayMain = new JSONArray();
            for (int j = 0; j < mListPayment.size(); j++) {
                jsonObject_main = new JSONObject();
                PaymentPostmodel mPDetails = mListPayment.get(j);
                jsonObject_main.accumulate("CustomerId", mPDetails.get_CustomerId());
                jsonObject_main.accumulate("SalesPersonId", mPDetails.get_SalesPersonId());
                jsonObject_main.accumulate("PaymentAmount", mPDetails.get_PaymentAmount());
                jsonObject_main.accumulate("CustomerName", mPDetails.get_CustomerName());
                jsonObject_main.accumulate("PaymentDate", mPDetails.get_PaymentDate());
                jsonObject_main.accumulate("PaymentMethod", mPDetails.get_PaymentMethod());
                jsonObject_main.accumulate("ChequeNo", mPDetails.get_ChequeNo());
                jsonObject_main.accumulate("Detail", mPDetails.get_Detail());
                jsonObject_main.accumulate("Image", mPDetails.get_Image());
                jsonObject_main.accumulate("Region", mPDetails.get_Region());
                myArrayMain.put(jsonObject_main);
            }

            json = myArrayMain.toString();
            Log.i("External_u", json);
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("External_u", e.getLocalizedMessage());
        }


        return result;
    }


    @Override
    protected void onPostExecute(String reuslt) {

        Log.i("External_u", "" + reuslt);
        // [28,29]


        if (reuslt == null || reuslt.equalsIgnoreCase("null")) {
            Toast.makeText(this.context, "No Payment Add Please Try Again", Toast.LENGTH_SHORT).show();
        } else {
            funDeletePayment();
        }

        myDialog.dismiss();

        Get_AllPayment_Task backroundTask_coupon_catgory = new Get_AllPayment_Task(
                context, 1, (int) MyConstants.listCustomer.get(0).get_CUSTOMER_ID());
        ArrayList<PaymentPostmodel> params_catgory = new ArrayList<PaymentPostmodel>();
        backroundTask_coupon_catgory.execute(params_catgory);
        ((Activity) context).finish();
    }

    void funDeletePayment() {
        for (int i = 0; i < mListPayment.size(); i++) {
            databaseHelper.deletePayment(mListPayment.get(i));
        }

    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


}
