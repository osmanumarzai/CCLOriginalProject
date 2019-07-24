package com.tkxel.admin.ordertaking.Loyalty;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.AdopterClass.LoyaltyProducts_adopter;
import com.tkxel.admin.ordertaking.R;


/**
 * Created by admin on 10/28/2017.
 */

public class Loyalty_ProductsCutomer extends Activity {
    GridView _listview;
    private int TIME_DELAY = 2000;
    private long back_pressed;
    String[] web = {
            "Chapli Kabab",
            "Burger Patty",
            "Chicken Donuts",
            "Crispy Bits",
            "Chapli Kabab",
            "Burger Patty",
            "Chicken Donuts",
            "Crispy Bits"
    };
    int[] imageId_2 = {
            R.drawable.icon_1,
            R.drawable.icon_2,
            R.drawable.icon_3,
            R.drawable.icon_4,
            R.drawable.icon_1,
            R.drawable.icon_2,
            R.drawable.icon_3,
            R.drawable.icon_4
    };

    TextView tv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layouty_custme_order);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setText("Products");
        LoyaltyProducts_adopter adapter = new LoyaltyProducts_adopter(Loyalty_ProductsCutomer.this, web, imageId_2);
        _listview = (GridView) findViewById(R.id.grid);
        _listview.setAdapter(adapter);
        _listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Gernal_Dialog(position);
            }
        });

    }

    int _counter = 0;

    public void Gernal_Dialog(int postion) {

        // custom dialog
        final Dialog dialog = new Dialog(Loyalty_ProductsCutomer.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dia_productdetails);
        dialog.setCanceledOnTouchOutside(false);
        // dialog.getWindow().setLayout(275, 350);

        final EditText edites = (EditText) dialog.findViewById(R.id.edites);
        TextView name_product = (TextView) dialog.findViewById(R.id.title_d1);
        name_product.setText(web[postion]);


        ImageView imageView = (ImageView) dialog.findViewById(R.id.img_1);
        imageView.setImageResource(imageId_2[postion]);


        dialog.findViewById(R.id.plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _counter = Integer.parseInt(edites.getText() + "");
                _counter = _counter + 1;
                if (_counter < 0) {
                    _counter = 0;
                    edites.setText(_counter + "");
                } else {
                    edites.setText(_counter + "");

                }

            }
        });
        dialog.findViewById(R.id.minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _counter = Integer.parseInt(edites.getText() + "");
                _counter = _counter - 1;
                if (_counter < 0) {
                    _counter = 0;
                    edites.setText(_counter + "");
                } else {
                    edites.setText(_counter + "");

                }

            }
        });

        dialog.findViewById(R.id.img_cross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_d2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }


}