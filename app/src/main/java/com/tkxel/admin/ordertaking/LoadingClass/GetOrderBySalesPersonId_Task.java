package com.tkxel.admin.ordertaking.LoadingClass;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.tkxel.admin.ordertaking.AdopterClass.Adapter_All_order;
import com.tkxel.admin.ordertaking.AllClass.All_Order_List;
import com.tkxel.admin.ordertaking.ModelClass.Order_array;
import com.tkxel.admin.ordertaking.ModelClass.Product_array;
import com.tkxel.admin.ordertaking.Utilities.DialogsUtils;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.tkxel.admin.ordertaking.AllClass.All_Order_List.listvew_allOrder;


public class GetOrderBySalesPersonId_Task extends
        AsyncTask<ArrayList<Order_array>, Void, ArrayList<Order_array>> {
    Context context;
    String customerId = "";

    int typ = 0;

    public GetOrderBySalesPersonId_Task(Context context, String customerId, int typ) {
        this.context = context;
        this.customerId = customerId;
        this.typ = typ;
    }


    ProgressDialog myDialog;

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        DialogsUtils dialogsUtils = new DialogsUtils(this.context);
        myDialog = dialogsUtils.showProgressDialog(this.context, "Loading..." + "");

    }


    String responseData;


    public ArrayList<Order_array> mOrder_arrayList;
    // public ArrayList<GetOrdersByCustmer> mOrderByCustomer;
    List<Product_array> Product_array;
    SharedPreferences sharedPreferences_form1;

    boolean IsLive_save() {
        sharedPreferences_form1 = this.context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        return sharedPreferences_form1.getBoolean("is_Live", false);

    }

    HttpGet httpget;

    @Override
    protected ArrayList<Order_array> doInBackground(ArrayList<Order_array>... params) {


        try {
            HttpClient httpclient = new DefaultHttpClient();

            if (IsLive_save()) {
                httpget = new HttpGet((MyConstants.used_IP_Live + "/api/GetOrderBySalesPersonId/" + MyConstants.listLoginDetails.get(0).get_EmployeeId() + "/" + customerId).replace(" ", "%20"));
                Log.i("External_u", MyConstants.used_IP_Live + "/api/GetOrderBySalesPersonId/" + MyConstants.listLoginDetails.get(0).get_EmployeeId() + "/" + customerId + "");

            } else {
                httpget = new HttpGet((MyConstants.used_IP + "/api/GetOrderBySalesPersonId/" + MyConstants.listLoginDetails.get(0).get_EmployeeId() + "/" + customerId).replace(" ", "%20"));

                Log.i("External_u", MyConstants.used_IP + "/api/GetOrderBySalesPersonId/" + MyConstants.listLoginDetails.get(0).get_EmployeeId() + "/" + customerId + "");

            }


            Log.i("URLS", "GetOrderBySalesPersonId\n" + MyConstants.used_IP + "/api/GetOrderBySalesPersonId/" + MyConstants.listLoginDetails.get(0).get_EmployeeId() + "/" + customerId);
            if (httpclient != null) {
                HttpResponse response = httpclient.execute(httpget);

                if (response != null) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        responseData = EntityUtils.toString(entity);
                    } else {
                        handlerException();
                    }
                } else {
                    handlerException();
                }

            } else {
                handlerException();
            }


            Log.i("OrderGet", responseData);
            mOrder_arrayList = new ArrayList<Order_array>();
            mOrder_arrayList.clear();
            if (responseData != null) {

                JSONArray json = new JSONArray(responseData);

                for (int jj = 0; jj < json.length(); jj++) {

                    JSONObject jsonRootObject = json.getJSONObject(jj);
                    Order_array questions_Helper = new Order_array();
                    ;


//                    double DELIVERY_ORDER_ID;
//                    String DELIVERY_ORDER_DATE, STATUS, REGION, CUSTOMER_NAME, SALES_PERSON_NAME;
                    questions_Helper.set_DELIVERY_ORDER_ID(jsonRootObject.optDouble("DELIVERY_ORDER_ID"));

                    questions_Helper.set_DELIVERY_ORDER_DATE(jsonRootObject.optString("DELIVERY_ORDER_DATE"));
                    questions_Helper.set_STATUS(jsonRootObject.optString("STATUS"));
                    questions_Helper.set_REGION(jsonRootObject.optString("REGION"));
                    questions_Helper.set_CUSTOMER_NAME(jsonRootObject.optString("CUSTOMER_NAME"));
                    questions_Helper.set_SALES_PERSON_NAME(jsonRootObject.optString("SALES_PERSON_NAME"));

                    mOrder_arrayList.add(questions_Helper);
                }


            } else {
                handlerException();
            }
        } catch (Exception e) {
            handlerException();
        }
        return mOrder_arrayList;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<Order_array> reuslt) {
        Log.i("Sardar : ", reuslt.size() + "");
        MyConstants.mOrderListByCustomer.clear();
        MyConstants.mOrderListByCustomer = reuslt;

        if (typ == 0) {
            Intent intent = new Intent(context, All_Order_List.class);
            context.startActivity(intent);

        } else {

            listvew_allOrder.setAdapter(new Adapter_All_order(context, MyConstants.mOrderListByCustomer, 1));
        }

        myDialog.dismiss();

    }

    void handlerException() {
//        Intent intent = new Intent(this.context, Error_Activity.class);
//        intent.putExtra("Error", "Categories deals");
//        this.context.startActivity(intent);
//        ((Activity) context).finish();


    }
}
