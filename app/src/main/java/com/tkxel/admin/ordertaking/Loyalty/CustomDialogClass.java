package com.tkxel.admin.ordertaking.Loyalty;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.R;

/**
 * Created by admin on 2/16/2018.
 */
public class CustomDialogClass extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    TextView txt_dia;
    String satr = "";

    public CustomDialogClass(Activity a, String satr) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.satr = satr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        txt_dia = (TextView) findViewById(R.id.txt_dia);


        if (URLUtil.isValidUrl(this.satr)) {
            yes.setVisibility(View.VISIBLE);
            txt_dia.setText("Open this url  \n" + this.satr);
        } else {
            yes.setVisibility(View.GONE);
            txt_dia.setText("Scan Item Data  " + this.satr);
        }

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.satr));
                this.c.startActivity(browserIntent);
                dismiss();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}