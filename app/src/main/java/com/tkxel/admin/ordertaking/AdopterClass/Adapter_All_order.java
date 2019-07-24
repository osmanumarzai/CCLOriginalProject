package com.tkxel.admin.ordertaking.AdopterClass;

/**
 * Created by admin on 10/6/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.ModelClass.Order_array;
import com.tkxel.admin.ordertaking.R;

import java.util.ArrayList;


public class Adapter_All_order extends BaseAdapter {
    Context context;
    ArrayList<Order_array> mOrderListByCustomer;
    LayoutInflater inflter;
    int type = 0;

    public Adapter_All_order(Context applicationContext, ArrayList<Order_array> mOrderListByCustomer, int type) {
        this.context = applicationContext;

        this.mOrderListByCustomer = mOrderListByCustomer;
        this.type = type;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        if (mOrderListByCustomer != null)
            return mOrderListByCustomer.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //  view = inflter.inflate(R.layout.adopter_alluser_item, null);


        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.adopter_allorder_item, viewGroup, false);
            holder = new ViewHolder();
            holder.tv_id = (TextView) view.findViewById(R.id.tv_id_date);
            holder.tv_customer = (TextView) view.findViewById(R.id.tv_customer);
            holder.tv_amount = (TextView) view.findViewById(R.id.tv_amount);
            holder.tv_status = (TextView) view.findViewById(R.id.tv_status);
            holder.tv_date = (TextView) view.findViewById(R.id.tv_date);


            // holder.img_1 = (ImageView) view.findViewById(R.id.img_1);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }

// double DELIVERY_ORDER_ID;
//  String DELIVERY_ORDER_DATE, STATUS, REGION, CUSTOMER_NAME, SALES_PERSON_NAME;


        fun_set_MultipalTextColor(holder.tv_date, "Order Date : ", mOrderListByCustomer.get(i).get_DELIVERY_ORDER_DATE());
        fun_set_MultipalTextColor(holder.tv_id, "Order Id : # ", mOrderListByCustomer.get(i).get_DELIVERY_ORDER_ID() + "");
        fun_set_MultipalTextColor(holder.tv_amount, "Region : ", mOrderListByCustomer.get(i).get_REGION() + "");
        fun_set_MultipalTextColor(holder.tv_customer, "Customer : ", mOrderListByCustomer.get(i).get_CUSTOMER_NAME() + "");
        fun_set_MultipalTextColor(holder.tv_status, "Status : ", mOrderListByCustomer.get(i).get_STATUS() + "");

        return view;
    }


    public class ViewHolder {

        TextView tv_id, tv_status, tv_date;
        TextView tv_amount;
        TextView tv_customer;


    }

    void fun_set_MultipalTextColor(TextView tv_blance, String str1, String str2) {
        //Audit Journal
        Spannable word0 = new SpannableString(str1);
        word0.setSpan(new ForegroundColorSpan(Color.parseColor("#616161")), 4, word0.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_blance.setText(word0);
        Spannable word = new SpannableString(str2);
        word.setSpan(new ForegroundColorSpan(Color.parseColor("#616161")), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_blance.append(word);
    }
}