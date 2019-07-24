package com.tkxel.admin.ordertaking.LoadingClass;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.AllClass.Add_DeepFrezer;
import com.tkxel.admin.ordertaking.AllClass.AllCustomer_list;
import com.tkxel.admin.ordertaking.AllClass.Take_Order_Activity;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.ModelClass.ModelCustomer;
import com.tkxel.admin.ordertaking.ModelClass.PaymentPostmodel;
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

public class Get_AllCustomer_Task extends
        AsyncTask<ArrayList<ModelCustomer>, Void, ArrayList<ModelCustomer>> {
    Context context;
    DatabaseHelper databaseHelper;

    int type = 0, EmployeeId;
    String RegionId;

    public Get_AllCustomer_Task(Context context, int type, int EmployeeId, String RegionId) {
        this.context = context;
        this.type = type;
        this.EmployeeId = EmployeeId;
        this.RegionId = RegionId;
        databaseHelper = new DatabaseHelper(this.context);
    }


    ProgressDialog myDialog;

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        DialogsUtils dialogsUtils = new DialogsUtils(this.context);
        myDialog = dialogsUtils.showProgressDialog(this.context, "Loading Customer..." + "");

    }

    String responseData;

    JSONArray JsonArryobj;
    ModelCustomer questions_Helper;
    SharedPreferences sharedPreferences_form1;

    boolean IsLive_save() {
        sharedPreferences_form1 = this.context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        return sharedPreferences_form1.getBoolean("is_Live", false);

    }

    public static ArrayList<ModelCustomer> mArrayQuestion;
    HttpGet httpget;

    @Override
    protected ArrayList<ModelCustomer> doInBackground(ArrayList<ModelCustomer>... params) {

        mArrayQuestion = new ArrayList<ModelCustomer>();
        mArrayQuestion.clear();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            if (IsLive_save()) {
                httpget = new HttpGet((MyConstants.used_IP_Live + "/api/GetCustomerByRegion/" + RegionId + "/" + EmployeeId).replace(" ", "%20"));
                Log.i("External_u", "" + MyConstants.used_IP_Live + "/api/GetCustomerByRegion/" + RegionId + "/" + EmployeeId);

            } else {
                httpget = new HttpGet((MyConstants.used_IP + "/api/GetCustomerByRegion/" + RegionId + "/" + EmployeeId).replace(" ", "%20"));
                Log.i("External_u", "" + MyConstants.used_IP + "/api/GetCustomerByRegion/" + RegionId + "/" + EmployeeId);

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

            if (responseData != null) {
                JsonArryobj = new JSONArray(responseData);

                for (int i = 0; i < JsonArryobj.length(); i++) {

                    JSONObject jsonRootObject = JsonArryobj.getJSONObject(i);
                    questions_Helper = new ModelCustomer();


                    questions_Helper.set_CUSTOMER_ID(jsonRootObject.optDouble("CUSTOMER_ID"));
                    questions_Helper.set_SALES_PERSON_ID(jsonRootObject.optInt("SALES_PERSON_ID"));
                    questions_Helper.set_CUSTOMER_NAME(jsonRootObject.optString("CUSTOMER_NAME"));
                    questions_Helper.set_ADDRESS(jsonRootObject.optString("ADDRESS"));
                    questions_Helper.set_REGION(jsonRootObject.optString("REGION"));

                    questions_Helper.set_CREDIT_LIMIT(jsonRootObject.optDouble("CREDIT_LIMIT"));

                    questions_Helper.set_CUSTOMER_TYPE(jsonRootObject.optString("CUSTOMER_TYPE"));


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

    Intent intent;

    @Override
    protected void onPostExecute(ArrayList<ModelCustomer> reuslt) {

        MyConstants.listCustomer = reuslt;

       /*  Add Customer to database*/
        if (reuslt != null) {
            if (reuslt.size() > 0) {
                funDatabase_Customer(reuslt);
            }
        }
         /* End  Add Customer to database*/


        if (MyConstants.listCustomer.size() > 0) {
            if (this.type == 100) {
                intent = new Intent(context, Take_Order_Activity.class);
                context.startActivity(intent);

            } else if (this.type == 200) {
                if (MyConstants.listCustomer.size() > 0) {
                    int customerid = (int) MyConstants.listCustomer.get(0).get_CUSTOMER_ID();
                    new GetOrderBySalesPersonId_Task(context, customerid + "", 0).execute();
                }

            } else if (this.type == 300) {

                intent = new Intent(context, AllCustomer_list.class);
                context.startActivity(intent);
            } else if (this.type == 400) {
                Get_AllPayment_Task get_allPayment_task = new Get_AllPayment_Task(
                        context, 0, (int) MyConstants.listCustomer.get(0).get_CUSTOMER_ID());
                ArrayList<PaymentPostmodel> params_catgory2 = new ArrayList<PaymentPostmodel>();
                get_allPayment_task.execute(params_catgory2);
            } else if (this.type == 600) {
                context.startActivity(new Intent(context, Add_DeepFrezer.class));

                //startActivity(new Intent(getApplicationContext(), Add_DeepFrezer.class));
            }
        } else {
            Toast.makeText(context, "No Customer Found!", Toast.LENGTH_LONG).show();
        }


//        if (reuslt != null) {
//            if (reuslt.size() > 0) {
//                Get_AllProducts_Task backroundTask_coupon_catgory = new Get_AllProducts_Task(
//                        context, 0);
//                ArrayList<Product_model> params_catgory = new ArrayList<Product_model>();
//                backroundTask_coupon_catgory.execute(params_catgory);
//            } else {
//                Toast.makeText(context, "No Customer Found!", Toast.LENGTH_LONG).show();
//            }
//
//        } else {
//            Toast.makeText(context, "No Customer Found!", Toast.LENGTH_LONG).show();
//        }

        myDialog.dismiss();

    }

    public List<ModelCustomer> listCustomer_datebase = new ArrayList<>();

    void funDatabase_Customer(ArrayList<ModelCustomer> reuslt) {
        // First Delete All Previous Customer
        listCustomer_datebase = databaseHelper.getAllUser();
        for (int i = 0; i < listCustomer_datebase.size(); i++) {
            databaseHelper.deleteUser(listCustomer_datebase.get(i));
        }

        for (int i = 0; i < reuslt.size(); i++) {
            databaseHelper.addUser(reuslt.get(i));
        }


    }

    void handlerException() {
//        Intent intent = new Intent(this.context, Error_Activity.class);
//        intent.putExtra("Error", "Categories deals");
//        this.context.startActivity(intent);
//        ((Activity) context).finish();


    }
}
