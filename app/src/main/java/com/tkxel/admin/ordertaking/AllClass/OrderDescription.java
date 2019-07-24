package com.tkxel.admin.ordertaking.AllClass;

/**
 * Created by admin on 10/16/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.AdopterClass.Adapter_Order_Description;
import com.tkxel.admin.ordertaking.LoadingClass.DeleteOrderTask;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;


public class OrderDescription extends Activity implements View.OnClickListener {
    ListView lisview_products;

    int position = 0;
    TextView totl_qty, totleprice, cutomer_name_1, cutomer_name_2, customer_city, customer_address, Location, orer_no, orer_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdescription_view);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            position = bundle.getInt("position");

        }

        fun_loads();
        findViewById(R.id.add_new_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    void fun_loads() {

        Location = (TextView) findViewById(R.id.orer_address);
        orer_date = (TextView) findViewById(R.id.orer_date);
        orer_no = (TextView) findViewById(R.id.orer_no);
        cutomer_name_1 = (TextView) findViewById(R.id.cutomer_name_1);
        cutomer_name_2 = (TextView) findViewById(R.id.cutomer_name_2);
        customer_city = (TextView) findViewById(R.id.customer_city);
        customer_address = (TextView) findViewById(R.id.customer_address);


        cutomer_name_1.setText("Customer : " + MyConstants.mOrderListByCustomer.get(position).get_obj_customer().get_Name());
        cutomer_name_2.setText(MyConstants.mOrderListByCustomer.get(position).get_obj_customer().get_Name() + "");


        Location.setText(MyConstants.mOrderListByCustomer.get(position).get_OrderLocation());

        customer_city.setText("City : " + MyConstants.mOrderListByCustomer.get(position).get_obj_customer().get_City() + "");
        customer_address.setText(MyConstants.mOrderListByCustomer.get(position).get_obj_customer().get_Address1e() + "");


        //  findViewById(R.id.save_order).setOnClickListener(this);
        totl_qty = (TextView) findViewById(R.id.totl_qty);
        totleprice = (TextView) findViewById(R.id.totleprice);


        totl_qty.setText("Qty " + (MyConstants.mOrderListByCustomer.get(position).get_TotalItemQuantity() + ""));
        totleprice.setText("" + MyConstants.mOrderListByCustomer.get(position).get_TotalPrice());

        orer_no.setText("Order id : # " + MyConstants.mOrderListByCustomer.get(position).get_Id());
        orer_date.setText("Date : " + MyConstants.mOrderListByCustomer.get(position).get_CreatedDate());

        lisview_products = (ListView) findViewById(R.id.lisview_products);
        findViewById(R.id.delete).setOnClickListener(this);

//        for (int i = 0; i < mArrayOrderPDetails.size(); i++) {
//            Log.i("WaheedDetails", mArrayOrderPDetails.get(i).get_itemQty());
//        }

// listProducts
        Adapter_Order_Description adapter_tAkeOrder = new Adapter_Order_Description(OrderDescription.this, MyConstants.mOrderListByCustomer.get(position).get_mProduct_array());
        lisview_products.setAdapter(adapter_tAkeOrder);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete:
                Gernal_Dialog("Delete Order", "Are you sure you want to Delete Order ?", "OK", "CANCEL");
                break;
        }
    }

    void Gernal_Dialog(String Title1, String Title, String strBtn, String strBtn2) {

        // custom dialog
        final Dialog dialog = new Dialog(OrderDescription.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.newiponedialoge2);
        dialog.setCanceledOnTouchOutside(false);
        // dialog.getWindow().setLayout(275, 350);

        TextView text_title1 = (TextView) dialog.findViewById(R.id.title_d1);
        text_title1.setText(Title1);

        TextView text_title = (TextView) dialog.findViewById(R.id.title_d);
        text_title.setText(Title);


        TextView dialogButton = (TextView) dialog.findViewById(R.id.btn_d);
        dialogButton.setText(strBtn);


        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                new DeleteOrderTask(OrderDescription.this, MyConstants.mOrderListByCustomer.get(position).get_Id() + "").execute();

            }
        });
        TextView dialogButton2 = (TextView) dialog.findViewById(R.id.btn_d2);
        dialogButton2.setText(strBtn2);
        dialogButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });
        dialog.show();
    }

}