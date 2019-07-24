package com.tkxel.admin.ordertaking.LoadingClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.AllClass.Main_View;
import com.tkxel.admin.ordertaking.ModelClass.Order_Details;
import com.tkxel.admin.ordertaking.Utilities.DialogsUtils;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Administrator on 01-Jan-16.
 */
public class Order_PostTask extends AsyncTask<String, Void, String> {


    String SalesPersonName, LOCATION, strLat, strLog, Region;

    int CustomerId;
    List<Order_Details> mPDetails;
    Context context;
    int empId;
    String CREDIT_LIMIT, TotalAmount;

    public Order_PostTask(Context context, int empId, List<Order_Details> mPDetails, int CustomerId, String CREDIT_LIMIT, String TotalAmount
            , String SalesPersonName, String LOCATION, String strLat, String strLog, String Region) {
        this.empId = empId;
        this.context = context;
        this.CustomerId = CustomerId;
        this.CREDIT_LIMIT = CREDIT_LIMIT;
        this.TotalAmount = TotalAmount;
        this.mPDetails = mPDetails;

        this.SalesPersonName = SalesPersonName;
        this.LOCATION = LOCATION;
        this.strLat = strLat;
        this.strLog = strLog;
        this.Region = Region;

        Log.i("SArdarG","Post "+strLat) ;
        Log.i("SArdarG","Post "+strLog) ;
        //   dialog = new ProgressDialog(this.context);
        Log.i("curent_userID", "5: " + CustomerId);
    }

    ProgressDialog myDialog;
    // private ProgressDialog dialog;

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
//        dialog.setMessage("Doing something, please wait.");
//        dialog.show();
        DialogsUtils dialogsUtils = new DialogsUtils(this.context);
        myDialog = dialogsUtils.showProgressDialog(this.context, "Submit Order..." + "");
        myDialog.show();
    }


    @Override
    protected String doInBackground(String... params) {
        //http://smdpreview.com/Api_Mobile/CouponRatingReview/aa973342-eae9-4a48-9eea-2f02b21a6dd9/

//            return POST(MyConstants.used_IP + "/api/Order");


        Log.i("External_u", MyConstants.used_IP_Live + "/api/Order");
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(MyConstants.used_IP_Live + "/api/Order");// change method

            String json = "";
            JSONObject jsonObject_main = null;
            JSONArray myArrayMain = new JSONArray();
            for (int j = 0; j < 1; j++) {
                jsonObject_main = new JSONObject();
                jsonObject_main.accumulate("SalesPersonId", this.empId);
                jsonObject_main.accumulate("CustomerId", this.CustomerId);

                jsonObject_main.accumulate("CREDIT_LIMIT", this.CREDIT_LIMIT + "");
                jsonObject_main.accumulate("TotalAmount", this.TotalAmount + "");

                jsonObject_main.accumulate("SalesPersonName", this.SalesPersonName + "");
                jsonObject_main.accumulate("LOCATION", this.LOCATION + "");
                jsonObject_main.accumulate("Region", this.Region + "");

                jsonObject_main.accumulate("Lat", this.strLat + "");
                jsonObject_main.accumulate("Long", this.strLog + "");




                //

                JSONArray myArray = new JSONArray();
                for (int i = 0; i < mPDetails.size(); i++) {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.accumulate("DeliveryQuantity", Double.parseDouble(mPDetails.get(i).get_itemQty() + ""));// int
                    jsonObj.accumulate("BalanceQuantity", Double.parseDouble(mPDetails.get(i).get_itemPRICE() + ""));
                    jsonObj.accumulate("itemId", mPDetails.get(i).get_PRODUCT_ID());
                    jsonObj.accumulate("Name", mPDetails.get(i).get_itemName());// int
                    jsonObj.accumulate("SalesPersonId", this.empId);
                    jsonObj.accumulate("Description", null);
                    myArray.put(jsonObj);
                }
                jsonObject_main.accumulate("OrderItems", myArray);
            }

            myArrayMain.put(jsonObject_main);
            json = myArrayMain.toString();
            Log.i("json_rating", json);

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
            Log.d("json_rating", e.getLocalizedMessage());
        }


        return result;
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }





    @Override
    protected void onPostExecute(String reuslt) {

        Log.i("json_rating", "" + reuslt);
        try {

            JSONObject jsonObject = new JSONObject(reuslt);
            JSONArray js = jsonObject.getJSONArray("m_Item1");
              JSONArray js2 = jsonObject.getJSONArray("m_Item2");
            if (js != null) {
                if (js.length() > 0) {

                    Intent intent = new Intent(context, Main_View.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                    Toast.makeText(context, "Order Submit Successfully ", Toast.LENGTH_LONG).show();
                    ((Activity) context).finish();

                } else if (js2!=null ) {
                    if(js2.length()>0){
                        Toast.makeText(context, "Check your Payable Balance ", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(context, "Order not submit try again", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(context, "Order not submit try again", Toast.LENGTH_LONG).show();
                }
            }
            //  JSONArray jsRejected = jsonObject.getJSONArray("m_Item2");
            myDialog.dismiss();

        } catch (JSONException e) {
            Toast.makeText(context, "Order not submit try again !", Toast.LENGTH_LONG).show();
        }

    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


}
