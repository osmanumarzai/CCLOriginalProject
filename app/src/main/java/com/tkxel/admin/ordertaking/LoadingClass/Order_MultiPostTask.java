package com.tkxel.admin.ordertaking.LoadingClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.AllClass.Main_View;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.ModelClass.OfflineDetails_ArrModel;
import com.tkxel.admin.ordertaking.ModelClass.Order;
import com.tkxel.admin.ordertaking.ModelClass.Order_Details;
import com.tkxel.admin.ordertaking.ModelClass.RejectedItemsDetails;
import com.tkxel.admin.ordertaking.ModelClass.RejectedOrderModle;
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
import java.util.ArrayList;
import java.util.List;

import static com.tkxel.admin.ordertaking.Utilities.MyConstants.listOrderOffline;

/**
 * Created by Administrator on 01-Jan-16.
 */
public class Order_MultiPostTask extends AsyncTask<String, Void, String> {


    int CustomerId;
    DatabaseHelper databaseHelper;
    Context context;
    int empId;
    public List<OfflineDetails_ArrModel> mArraydetails_offline2;

    public Order_MultiPostTask(Context context, int empId, int CustomerId, List<OfflineDetails_ArrModel> mArraydetails_offline2 ) {
        this.empId = empId;
        this.context = context;

        this.CustomerId = CustomerId;


        this.mArraydetails_offline2 = mArraydetails_offline2;
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
            return POST(MyConstants.used_IP_Live + "/api/Order");
        else
            return POST(MyConstants.used_IP + "/api/Order");
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    public String POST(String url) {


        Log.i("json_rating", url);
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);// change method


            String json = "";
            JSONObject jsonObject_main = null;


            JSONArray myArrayMain = new JSONArray();
            for (int jjwa = 0; jjwa < mArraydetails_offline2.size(); jjwa++) {

                jsonObject_main = new JSONObject();
                jsonObject_main.accumulate("SalesPersonId", this.empId);
                jsonObject_main.accumulate("CustomerId", mArraydetails_offline2.get(jjwa).get_customerId());



                jsonObject_main.accumulate("SalesPersonName", mArraydetails_offline2.get(jjwa).get_SalesPersonName() + "");
                jsonObject_main.accumulate("LOCATION",mArraydetails_offline2.get(jjwa).get_Location() + "");
                jsonObject_main.accumulate("Lat", mArraydetails_offline2.get(jjwa).get_strLat()+ "");
                jsonObject_main.accumulate("Log", mArraydetails_offline2.get(jjwa).get_strLog() + "");
                jsonObject_main.accumulate("Region", mArraydetails_offline2.get(jjwa).get_Region() + "");



                jsonObject_main.accumulate("CREDIT_LIMIT", mArraydetails_offline2.get(jjwa).get_CrdtLimit()+"");
                jsonObject_main.accumulate("TotalAmount", mArraydetails_offline2.get(jjwa).get_TottleAmount()+"");








                Log.i("json_rating", " : " + mArraydetails_offline2.get(jjwa).get_customerId());

                JSONArray myArray = new JSONArray();
                for (int i = 0; i < mArraydetails_offline2.get(jjwa).getDetailsList().size(); i++) {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.accumulate("DeliveryQuantity", mArraydetails_offline2.get(jjwa).getDetailsList().get(i).get_itemQty()+"");// int
                    jsonObj.accumulate("BalanceQuantity", mArraydetails_offline2.get(jjwa).getDetailsList().get(i).get_itemPRICE()+"");
                    jsonObj.accumulate("itemId", mArraydetails_offline2.get(jjwa).getDetailsList().get(i).get_PRODUCT_ID());
                    jsonObj.accumulate("Name", mArraydetails_offline2.get(jjwa).getDetailsList().get(i).get_itemName());// int
                    jsonObj.accumulate("SalesPersonId", this.empId);
                    jsonObj.accumulate("Description", null);
                    myArray.put(jsonObj);
                }
                jsonObject_main.accumulate("OrderItems", myArray);
                myArrayMain.put(jsonObject_main);
            }


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

    Intent intent;
    public List<RejectedOrderModle> listRejected = new ArrayList<>();

    public List<RejectedItemsDetails> listRejectedDetails = new ArrayList<>();

    @Override
    protected void onPostExecute(String reuslt) {

        Log.i("json_rating", "" + reuslt);

        //{"m_Item1":[1018110.0,1018111.0],"m_Item2":[]}
        myDialog.dismiss();

        try {

            JSONObject jsonObject = new JSONObject(reuslt);

            JSONArray js = jsonObject.getJSONArray("m_Item1");
            if (js != null) {
                if (js.length() > 0) {
                    // delete from database

                    fun_Delete();
                    intent = new Intent(context, Main_View.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    Toast.makeText(context, "Order  submit successfully ", Toast.LENGTH_LONG).show();
                    ((Activity) context).finish();
                } else {
                    Toast.makeText(context, "Order not submit try again", Toast.LENGTH_LONG).show();
                }
            }
            JSONArray jsRejected = jsonObject.getJSONArray("m_Item2");
            listRejected.clear();
            for (int j = 0; j < jsRejected.length(); j++) {
                RejectedOrderModle rejectedOrderModle = new RejectedOrderModle();

                JSONObject jsonRootObject = jsRejected.getJSONObject(j);
                rejectedOrderModle.set_CustomerId(jsonRootObject.optInt("CustomerId"));
                rejectedOrderModle.set_SalesPersonId(jsonRootObject.optInt("SalesPersonId"));
                rejectedOrderModle.set_CREDIT_LIMIT(jsonRootObject.optInt("CREDIT_LIMIT"));
                rejectedOrderModle.set_TotalAmount(jsonRootObject.optInt("TotalAmount"));

                JSONArray jsOrderItem = jsonObject.getJSONArray("OrderItems");
                listRejectedDetails.clear();
                for (int jj = 0; jj < jsOrderItem.length(); jj++) {
                    JSONObject jObject = jsOrderItem.getJSONObject(jj);

                    RejectedItemsDetails rejectedItemsDetails = new RejectedItemsDetails();

                    rejectedItemsDetails.set_DeliveryOrderId(jObject.optInt("DeliveryOrderId"));
                    rejectedItemsDetails.set_itemId(jObject.optInt("itemId"));
                    rejectedItemsDetails.set_BalanceQuantity(jObject.optInt("BalanceQuantity"));
                    rejectedItemsDetails.set_DeliveryQuantity(jObject.optInt("DeliveryQuantity"));
                    listRejectedDetails.add(rejectedItemsDetails);
                }
                rejectedOrderModle.set_LIST(listRejectedDetails);

                listRejected.add(rejectedOrderModle);
            }
            addorder(listRejected);

        } catch (JSONException e) {
            Toast.makeText(context, "Order not submit try again !", Toast.LENGTH_LONG).show();
        }

    }


    public void addorder(List<RejectedOrderModle> listRejected) {
        Order userOrder = new Order();
        for (int i = 0; i < listRejected.size(); i++) {
            userOrder.set_USER_ID(listRejected.get(i).get_CustomerId());
            userOrder.set_Name("");
            userOrder.set_Location("");
            userOrder.set_PRICE("" + 0);
            userOrder.set_QtyOrder("");
            userOrder.set_Status("OPEN");
            // Here is customer actually SalesPersonId
            userOrder.set_customer("");
            userOrder.set_SalesPersonId(listRejected.get(i).get_SalesPersonId());
            userOrder.set_Date("");
            userOrder.set_TotleAmount(listRejected.get(i).get_TotalAmount() + "");
            userOrder.set_CrdLimit(listRejected.get(i).get_CREDIT_LIMIT() + "");
            addOrderDetait((int) databaseHelper.addorder(userOrder),listRejected.get(i).get_LIST());
        }


    }

    Order_Details order_details;
    List<Order_Details> mPDetails = new ArrayList<>();

    void addOrderDetait(int lstID,List<RejectedItemsDetails> listRejected) {
        mPDetails.clear();
        for (int i = 0; i <listRejected.size(); i++) {
            order_details = new Order_Details();
            order_details.set_Order_ID(lstID);
            order_details.set_PRODUCT_ID(listRejected.get(i).get_itemId());
            order_details.set_itemName(""+listRejected.get(i).get_BalanceQuantity());
            order_details.set_itemPRICE(""+listRejected.get(i).get_DeliveryQuantity());
            order_details.set_itemQty(""+listRejected.get(i).get_DeliveryQuantity());
            mPDetails.add(order_details);
            databaseHelper.add_OrderDetails(order_details);

        }
    }


    void fun_Delete() {

        for (int jjwa = 0; jjwa < listOrderOffline.size(); jjwa++) {
            databaseHelper.deleteOrder(listOrderOffline.get(jjwa));
        }
        for (int jjwa = 0; jjwa < mArraydetails_offline2.size(); jjwa++) {
            for (int i = 0; i < mArraydetails_offline2.get(jjwa).getDetailsList().size(); i++) {
                databaseHelper.deleteOrderDetail(mArraydetails_offline2.get(jjwa).getDetailsList().get(i));
            }
        }
        listOrderOffline.clear();
        mArraydetails_offline2.clear();
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
