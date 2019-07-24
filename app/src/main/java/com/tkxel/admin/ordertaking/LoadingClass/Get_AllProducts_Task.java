package com.tkxel.admin.ordertaking.LoadingClass;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.tkxel.admin.ordertaking.AllClass.Main_View;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.ModelClass.Product_model;
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

public class Get_AllProducts_Task extends
        AsyncTask<ArrayList<Product_model>, Void, ArrayList<Product_model>> {
    Context context;
    DatabaseHelper databaseHelper;

    int type = 0;

    public Get_AllProducts_Task(Context context, int type) {
        this.context = context;
        this.type = type;
        databaseHelper = new DatabaseHelper(this.context);
    }

    ProgressDialog myDialog;

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        DialogsUtils dialogsUtils = new DialogsUtils(context);
        myDialog = dialogsUtils.showProgressDialog(context, "Loading Products..." + "");

    }


    String responseData;

    JSONArray JsonArryobj;
    Product_model questions_Helper;


    public ArrayList<Product_model> mArrayQuestion;
    SharedPreferences sharedPreferences_form1;

    boolean IsLive_save() {
        sharedPreferences_form1 = this.context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        return sharedPreferences_form1.getBoolean("is_Live", false);

    }

    HttpGet httpget;

    @Override
    protected ArrayList<Product_model> doInBackground(ArrayList<Product_model>... params) {

        mArrayQuestion = new ArrayList<Product_model>();
        mArrayQuestion.clear();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            if (IsLive_save()) {
                httpget = new HttpGet((MyConstants.used_IP_Live + "/api/GetProducts/"+MyConstants.listLoginDetails.get(0).get_RegionId()).replace(" ", "%20"));
                Log.i("External_u", MyConstants.used_IP_Live + "/api/GetProducts/"+MyConstants.listLoginDetails.get(0).get_RegionId());
            } else {
                httpget = new HttpGet((MyConstants.used_IP + "/api/GetProducts/"+MyConstants.listLoginDetails.get(0).get_RegionId()).replace(" ", "%20"));
                Log.i("External_u", MyConstants.used_IP + "/api/GetProducts/"+MyConstants.listLoginDetails.get(0).get_RegionId());
            }




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

            Log.i("External_u",responseData);

            if (responseData != null) {
                JsonArryobj = new JSONArray(responseData);

                for (int i = 0; i < JsonArryobj.length(); i++) {

                    JSONObject jsonRootObject = JsonArryobj.getJSONObject(i);
                    questions_Helper = new Product_model();

                    questions_Helper.setCount(jsonRootObject.optInt("Count"));

                    questions_Helper.setisSelected(jsonRootObject.optBoolean("isSelected"));
                    questions_Helper.set_isBox(jsonRootObject.optBoolean("isBox"));


                    //    questions_Helper.setId(jsonRootObject.optInt("ProductId"));


                    questions_Helper.set_ITEM_CODE(jsonRootObject.optString("ITEM_CODE"));
                    questions_Helper.set_ITEM_DESC(jsonRootObject.optString("ITEM_DESC"));
                    questions_Helper.set_PART_NO(jsonRootObject.optString("PART_NO"));
                    questions_Helper.set_UOM(jsonRootObject.optString("UOM"));
                    questions_Helper.set_PRICEREGION(jsonRootObject.optString("PRICEREGION"));
                    questions_Helper.set_ITEM_ID(jsonRootObject.optDouble("ITEM_ID"));
                    questions_Helper.set_PRICERATE(jsonRootObject.optDouble("PRICERATE"));

                    questions_Helper.set_WEIGHT_IN_KG(jsonRootObject.optString("WEIGHT_IN_KG"));


                    mArrayQuestion.add(questions_Helper);
                }

            } else {
                handlerException();
            }
        } catch (Exception e) {
            handlerException();
        }
        return mArrayQuestion;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<Product_model> reuslt) {
        MyConstants.mArrayProducts = reuslt;

        if (reuslt != null) {
            if (reuslt.size() > 0) {
                funDatabase_Product(reuslt);
            }
        }
        Intent intent = new Intent(context, Main_View.class);
        context.startActivity(intent);
        myDialog.dismiss();
        ((Activity) context).finish();


    }

    public List<Product_model> listCustomer_datebase = new ArrayList<>();

    void funDatabase_Product(ArrayList<Product_model> reuslt) {
        listCustomer_datebase.clear();
        listCustomer_datebase = databaseHelper.getAllProducts();
        for (int i = 0; i < listCustomer_datebase.size(); i++) {
            databaseHelper.deleteProduct(listCustomer_datebase.get(i));
        }

        for (int i = 0; i < reuslt.size(); i++) {
            databaseHelper.add_Products(reuslt.get(i));
        }
    }

    void handlerException() {
//        Intent intent = new Intent(this.context, Error_Activity.class);
//        intent.putExtra("Error", "Categories deals");
//        this.context.startActivity(intent);
//        ((Activity) context).finish();


    }
}
