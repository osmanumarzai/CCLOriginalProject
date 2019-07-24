package com.tkxel.admin.ordertaking.LoadingClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tkxel.admin.ordertaking.Utilities.DialogsUtils;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 01-Jan-16.
 */
public class PostTask_Deepferzer extends AsyncTask<String, Void, String> {
    //CustomerId CustomerName  ,Image1 Image2  ,IsFifo  ,IsCorrectOrder
    int CustomerId;
    Context context;
    int empId;
    boolean IsFifo, IsCorrectOrder;
    String CustomerName;

    public PostTask_Deepferzer(Context context, int CustomerId, String CustomerName, boolean IsFifo, boolean IsCorrectOrder
    ) {
        this.empId = empId;
        this.context = context;
        this.CustomerId = CustomerId;
        this.CustomerName = CustomerName;
        this.IsFifo = IsFifo;
        this.IsCorrectOrder = IsCorrectOrder;

        //   dialog = new ProgressDialog(this.context);
        Log.i("External_u", "5: " + CustomerId);
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
        myDialog = dialogsUtils.showProgressDialog(this.context, "" + "");
        myDialog.show();
    }


    @Override
    protected String doInBackground(String... params) {

        Log.i("External_u", MyConstants.used_IP_Live + "/api/StocksApi");
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(MyConstants.used_IP_Live + "/api/StocksApi");// change method


            String str_json = "";
            JSONObject jsonObject_main = new JSONObject();

            jsonObject_main.accumulate("SalesPersonId", MyConstants.listLoginDetails.get(0).get_EmployeeId());
            jsonObject_main.accumulate("CustomerName", this.CustomerName);
            jsonObject_main.accumulate("CustomerId", this.CustomerId);
            jsonObject_main.accumulate("IsFifo", this.IsFifo);
            jsonObject_main.accumulate("IsCorrectOrder", this.IsCorrectOrder);

            jsonObject_main.accumulate("Image1", MyConstants.image1_64);
            jsonObject_main.accumulate("Image2", MyConstants.image2_64);


            str_json = jsonObject_main + "";


            Log.i("External_u", str_json);

            StringEntity se = new StringEntity(str_json);
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


        Log.i("External_u", "" + reuslt);
        myDialog.dismiss();

        ((Activity) context).finish();
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
