package com.tkxel.admin.ordertaking.Utilities;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by admin on 9/15/2017.
 */
public class ProgressBarHandler {

    private Context mContext;
    private ProgressDialog mProgressDialog;
//    public ProgressBarHandler(Context context) {
//        mContext = context;
//        mProgressDialog = new ProgressDialog(context);
//        mProgressDialog.setMessage("Signing........");
//        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mProgressDialog.setCancelable(false);
//    }

    public ProgressBarHandler(ProgressDialog mProgressDialog, String title ) {
        mProgressDialog.setMax(100);
        mProgressDialog.setMessage("Loading........");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle(title);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

    }

    public void show(ProgressDialog mProgressDialog) {
        mProgressDialog.show();
    }

    public void hide(ProgressDialog mProgressDialog) {
        mProgressDialog.dismiss();
    }

}