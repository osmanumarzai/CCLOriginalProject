package com.tkxel.admin.ordertaking.LoadingClass;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.tkxel.admin.ordertaking.AllClass.All_TargetList;
import com.tkxel.admin.ordertaking.AllClass.GraphActivity;
import com.tkxel.admin.ordertaking.ModelClass.EmployeeTarget;
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

public class Get_EmployeeTarget_Task extends
        AsyncTask<ArrayList<EmployeeTarget>, Void, ArrayList<EmployeeTarget>> {
    Context context;


    int type = 0;

    public Get_EmployeeTarget_Task(Context context, int type) {
        this.context = context;
        this.type = type;
    }


    ProgressDialog myDialog;

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        DialogsUtils dialogsUtils = new DialogsUtils(this.context);
        myDialog = dialogsUtils.showProgressDialog(this.context, "Loading Target" + "");

    }

    String responseData;

    JSONArray JsonArryobj;
    EmployeeTarget questions_Helper;

    SharedPreferences sharedPreferences_form1;

    boolean IsLive_save() {
        sharedPreferences_form1 = this.context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        return sharedPreferences_form1.getBoolean("is_Live", false);

    }

    public ArrayList<EmployeeTarget> mArrayQuestion;
    HttpGet httpget;

    @Override
    protected ArrayList<EmployeeTarget> doInBackground(ArrayList<EmployeeTarget>... params) {

        mArrayQuestion = new ArrayList<EmployeeTarget>();

        mArrayQuestion.clear();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            //   HttpGet httpget = new HttpGet((MyConstants.url_EmployeeTarget + MyConstants.listLoginDetails.get(0).get_EmployeeId()).replace(" ", "%20"));

            if (IsLive_save()) {
                httpget = new HttpGet((MyConstants.used_IP_Live + "/api/GetEmployeeTarget/" + MyConstants.listLoginDetails.get(0).get_EmployeeId()).replace(" ", "%20"));
                Log.i("External_u", MyConstants.used_IP_Live + "/api/GetEmployeeTarget/" + MyConstants.listLoginDetails.get(0).get_EmployeeId() + "");
            } else {
                httpget = new HttpGet((MyConstants.used_IP + "/api/GetEmployeeTarget/" + MyConstants.listLoginDetails.get(0).get_EmployeeId()).replace(" ", "%20"));
                Log.i("External_u", MyConstants.used_IP + "/api/GetEmployeeTarget/" + MyConstants.listLoginDetails.get(0).get_EmployeeId() + "");
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
            Log.i("External_u", "" + responseData);

            if (responseData != null) {
                JsonArryobj = new JSONArray(responseData);

                for (int i = 0; i < JsonArryobj.length(); i++) {
                    JSONObject jsonRootObject = JsonArryobj.getJSONObject(i);
                    questions_Helper = new EmployeeTarget();
                    questions_Helper.set_Month(jsonRootObject.optString("Month"));
                    questions_Helper.set_EmployeeName(jsonRootObject.optString("EmployeeName"));
                    questions_Helper.set_RegionId(jsonRootObject.optInt("RegionId"));
                    questions_Helper.set_SaleTarget(jsonRootObject.optInt("SaleTarget"));
                    questions_Helper.set_TargetAchieved(jsonRootObject.optInt("TargetAchieved"));
                    mArrayQuestion.add(questions_Helper);
                }

            } else {
                handlerException();
            }
        } catch (Exception e) {
            handlerException();
            Log.i("External_u", "" + e);

        }
        return mArrayQuestion;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<EmployeeTarget> reuslt) {
        MyConstants.listEmployeeTarget.clear();
        MyConstants.listEmployeeTarget = reuslt;


//            }

// type = 0 then main view go to listview customer
// type = 1 then its a customer of customer
        if (this.type == 0) {
            //   if (MyConstants.listEmployeeTarget.size() > 0) {
            intent = new Intent(this.context, All_TargetList.class);
            this.context.startActivity(intent);
//            }else {
//
//            }
        } else {
            intent = new Intent(this.context, GraphActivity.class);
            this.context.startActivity(intent);


        }

        myDialog.dismiss();

    }

    Intent intent;

    void handlerException() {

// waheed sardar
// waheed sardar
// waheed sardar
// waheed sardar
// WAHEED SARDAR
// waheed sardar

//        Intent intent = new Intent(this.context, Error_Activity.class);
//        intent.putExtra("Error", "Categories deals");
//        this.context.startActivity(intent);
//        ((Activity) context).finish();


    }
}
