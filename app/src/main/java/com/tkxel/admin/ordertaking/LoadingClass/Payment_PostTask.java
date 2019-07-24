package com.tkxel.admin.ordertaking.LoadingClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by Administrator on 01-Jan-16.
 */
public class Payment_PostTask extends AsyncTask<String, Void, String> {


    PaymentPostmodel mPDetails;
    Context context;


    public Payment_PostTask(Context context, PaymentPostmodel mPDetails
    ) {

        this.context = context;
        this.mPDetails = mPDetails;

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
            for (int j = 0; j < 1; j++) {
                jsonObject_main = new JSONObject();
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

            }

            myArrayMain.put(jsonObject_main);

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
        myDialog.dismiss();

        Get_AllPayment_Task backroundTask_coupon_catgory = new Get_AllPayment_Task(
                context, 1, (int) MyConstants.listCustomer.get(0).get_CUSTOMER_ID());
        ArrayList<PaymentPostmodel> params_catgory = new ArrayList<PaymentPostmodel>();
        backroundTask_coupon_catgory.execute(params_catgory);
        ((Activity) context).finish();
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
