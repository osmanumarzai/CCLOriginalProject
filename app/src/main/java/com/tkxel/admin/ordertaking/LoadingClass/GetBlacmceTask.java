package com.tkxel.admin.ordertaking.LoadingClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.AllClass.AllCustomer_list;
import com.tkxel.admin.ordertaking.AllClass.View_Order_Activity;
import com.tkxel.admin.ordertaking.Utilities.DialogsUtils;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

/**
 * Created by Administrator on 01-Jan-16.
 */
public class GetBlacmceTask extends AsyncTask<String, Void, String> {
    public Context context;
    double blacnelime = 0.0;
    int customerid = 0;

    public GetBlacmceTask(Context context, int customerid, double blacnelime) {

        this.context = context;
        this.customerid = customerid;
        this.blacnelime = blacnelime;


    }

    ProgressDialog myDialog;

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        DialogsUtils dialogsUtils = new DialogsUtils(this.context);
        myDialog = dialogsUtils.showProgressDialog(this.context, "Wait..." + "");

    }

    String responseData = "0.0";
// change iocn here

    SharedPreferences sharedPreferences_form1;

    boolean IsLive_save() {
        sharedPreferences_form1 = this.context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        return sharedPreferences_form1.getBoolean("is_Live", false);

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            HttpHandler sh = new HttpHandler();


            if (IsLive_save()) {
                responseData = sh.makeServiceCall((MyConstants.used_IP_Live + "/api/GetCustomerPayable/" + customerid).replace(" ", "%20"));
                Log.i("External_u", MyConstants.used_IP_Live + "/api/GetCustomerPayable/" + customerid + "");

            } else {
                responseData = sh.makeServiceCall((MyConstants.used_IP + "/api/GetCustomerPayable/" + customerid).replace(" ", "%20"));

                Log.i("External_u", MyConstants.used_IP + "/api/GetCustomerPayable/" + customerid + "");

            }


        } catch (Exception e) {
            responseData = "0.0";
        }
        return responseData + "";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String blance) {
        try {

            if (blacnelime == 00000) {

                ((AllCustomer_list) context).Gernal_Dialog("Payable Balance", "Payable balance is \n"+blance, "OK", "CANCEL");

            } else {
                if (blance.trim().equalsIgnoreCase("null") || blance == null) {

                    if (this.blacnelime <= MyConstants.listCustomer.get(0).get_CREDIT_LIMIT()) {
                        ((View_Order_Activity) context).Gernal_Dialog("Submit Order", "Are you sure you want to submit Order ?", "OK", "CANCEL");
                    } else {
                        Toast.makeText(context, "Balance Limit Exceed !", Toast.LENGTH_LONG).show();
                    }
                } else {

                    if (this.blacnelime <= (MyConstants.listCustomer.get(0).get_CREDIT_LIMIT() - Double.parseDouble(blance))) {
                        ((View_Order_Activity) context).Gernal_Dialog("Submit Order", "Are you sure you want to submit Order ?", "OK", "CANCEL");

                    } else {
                        Toast.makeText(context, "Balance Limit Exceed !", Toast.LENGTH_LONG).show();
                    }

                }
            }


        } catch (Exception e) {
            if (blacnelime == 00000) {

            } else {
                if (this.blacnelime <= MyConstants.listCustomer.get(0).get_CREDIT_LIMIT()) {

                    ((View_Order_Activity) context).Gernal_Dialog("Submit Order", "Are you sure you want to submit Order ?", "OK", "CANCEL");

                } else {
                    Toast.makeText(context, "Balance Limit Exceed !", Toast.LENGTH_LONG).show();
                }
            }


            //  Toast.makeText(context, "Balance Limit Exceed !!", Toast.LENGTH_LONG).show();
        }

        myDialog.dismiss();

    }


}
