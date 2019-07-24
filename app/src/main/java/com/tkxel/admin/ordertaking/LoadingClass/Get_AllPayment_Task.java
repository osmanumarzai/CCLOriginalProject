package com.tkxel.admin.ordertaking.LoadingClass;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.tkxel.admin.ordertaking.AdopterClass.Adapter_All_Payment;
import com.tkxel.admin.ordertaking.AllClass.All_PaymentList;
import com.tkxel.admin.ordertaking.ModelClass.PaymentPostmodel;
import com.tkxel.admin.ordertaking.Utilities.DialogsUtils;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Get_AllPayment_Task extends
        AsyncTask<ArrayList<PaymentPostmodel>, Void, ArrayList<PaymentPostmodel>> {
    Context context;
    int customerid = 0;

    int type = 0;

    public Get_AllPayment_Task(Context context, int type, int customerid) {
        this.context = context;
        this.type = type;
        this.customerid = customerid;

    }

    ProgressDialog myDialog;

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        DialogsUtils dialogsUtils = new DialogsUtils(context);
        myDialog = dialogsUtils.showProgressDialog(context, "Loading Payments..." + "");

    }

    String responseData = "SArdar";

    JSONArray JsonArryobj;
    PaymentPostmodel questions_Helper;

    //
    public static ArrayList<PaymentPostmodel> mArrayQuestion;
    SharedPreferences sharedPreferences_form1;

    boolean IsLive_save() {
        sharedPreferences_form1 = this.context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        return sharedPreferences_form1.getBoolean("is_Live", false);

    }

    @Override
    protected ArrayList<PaymentPostmodel> doInBackground(ArrayList<PaymentPostmodel>... params) {

        mArrayQuestion = new ArrayList<PaymentPostmodel>();
        mArrayQuestion.clear();

        try {
            HttpHandler sh = new HttpHandler();
            if (IsLive_save()) {
                responseData = sh.makeServiceCall((MyConstants.used_IP_Live + "/api/GetPaymentsBySalePersonandCustomer/" +
                        MyConstants.listLoginDetails.get(0).get_EmployeeId() + "/" + customerid).replace(" ", "%20"));


                Log.i("External_u", MyConstants.used_IP_Live + "/api/GetPaymentsBySalePersonandCustomer/" +
                        MyConstants.listLoginDetails.get(0).get_EmployeeId() + "/" + customerid);
            } else {
                responseData = sh.makeServiceCall((MyConstants.used_IP + "/api/GetPaymentsBySalePersonandCustomer/" +
                        MyConstants.listLoginDetails.get(0).get_EmployeeId() + "/" + customerid).replace(" ", "%20"));


                Log.i("External_u", MyConstants.used_IP + "/api/GetPaymentsBySalePersonandCustomer/" +
                        MyConstants.listLoginDetails.get(0).get_EmployeeId() + "/" + customerid);
            }


            Log.i("External_u", responseData);


            if (responseData != null) {
                JsonArryobj = new JSONArray(responseData);
                for (int i = 0; i < JsonArryobj.length(); i++) {
                    JSONObject jsonRootObject = JsonArryobj.getJSONObject(i);
                    questions_Helper = new PaymentPostmodel();
                    questions_Helper.set_PaymentId(jsonRootObject.optInt("PaymentId"));

                    questions_Helper.set_PaymentAmount(jsonRootObject.optDouble("PaymentAmount"));
                    questions_Helper.set_CustomerId(jsonRootObject.optInt("CustomerId"));
                    questions_Helper.set_SalesPersonId(jsonRootObject.optInt("SalesPersonId"));
                    questions_Helper.set_CustomerName(jsonRootObject.optString("CustomerName"));
                    questions_Helper.set_PaymentDate(jsonRootObject.optString("PaymentDate"));
                    questions_Helper.set_PaymentMethod(jsonRootObject.optString("PaymentMethod"));
                    questions_Helper.set_ChequeNo(jsonRootObject.optString("ChequeNo"));
                    questions_Helper.set_Detail(jsonRootObject.optString("Detail"));
                    questions_Helper.set_ImageUrl(jsonRootObject.optString("ImageUrl"));
                    questions_Helper.set_Region(jsonRootObject.optString("Region"));
                    mArrayQuestion.add(questions_Helper);
                }

            } else {
                handlerException();
            }
        } catch (Exception e) {
            Log.i("waheed", e + "");
            //  handlerException();
        }
        return mArrayQuestion;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<PaymentPostmodel> reuslt) {

        MyConstants.mListPayment = reuslt;
//
        if (type == 0) {
            Intent intent = new Intent(context, All_PaymentList.class);
            intent.putExtra("isOnline", true);
            context.startActivity(intent);
        } else {
            All_PaymentList.main_lisrview.setAdapter(new Adapter_All_Payment(context, MyConstants.mListPayment, 1));
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
