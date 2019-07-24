package com.tkxel.admin.ordertaking.LoadingClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.Utilities.DialogsUtils;
import com.tkxel.admin.ordertaking.Utilities.ProgressBarHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Administrator on 01-Jan-16.
 */
public class DeleteOrderTask extends AsyncTask<String, Void, String> {
    public Context context;
    ProgressBarHandler mProgressBarHandler;

    String Orderid;

    public DeleteOrderTask(Context context, String Orderid) {
        this.context = context;
        this.Orderid = Orderid;
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

    @Override
    protected String doInBackground(String... params) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            String myurls = ("http://110.36.230.78:33811/api/Order?id=" + Orderid).trim().replace(" ", "%20");
            Log.e("External_u", myurls);
            HttpDelete httpget = new HttpDelete(myurls);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            responseData = EntityUtils.toString(entity);
            Log.e("External_u", responseData + "");
        } catch (Exception e) {

        }
        return responseData + "";
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String reuslt) {
        myDialog.dismiss();

        if (reuslt.equalsIgnoreCase("true")) {
            Toast.makeText(context, "Order Delete Successfully", Toast.LENGTH_SHORT).show();
            ((Activity) context).finish();
        } else {
            Toast.makeText(context, "Error ! Try again", Toast.LENGTH_SHORT).show();
        }
    }
}
