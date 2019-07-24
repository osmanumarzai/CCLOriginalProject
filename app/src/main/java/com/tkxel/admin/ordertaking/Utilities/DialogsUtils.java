package com.tkxel.admin.ordertaking.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.R;


/**
 * Created by admin on 11/17/2017.
 */

public class DialogsUtils {
    Context context;

    public DialogsUtils(Context context) {
        this.context = context;
    }

    public static TextView tv;
  //  public static ProgressBar progress_bar;

    public static ProgressDialog showProgressDialog(Context context, String message) {
        ProgressDialog dialog = ProgressDialog.show(context, null,
                null, true);
        dialog.setContentView(R.layout.loadingdialugelayout);


        tv = (TextView) dialog.findViewById(R.id.textView);
        tv.setText("" + message);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return dialog;
    }



}
