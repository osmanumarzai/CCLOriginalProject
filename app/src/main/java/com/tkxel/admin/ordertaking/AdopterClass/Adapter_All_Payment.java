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

import com.tkxel.admin.ordertaking.ModelClass.PaymentPostmodel;
import com.tkxel.admin.ordertaking.R;

import java.util.List;


public class Adapter_All_Payment extends BaseAdapter {
    Context context;
    List<PaymentPostmodel> mOrderListByCustomer;
    LayoutInflater inflter;
    int type = 0;

    public Adapter_All_Payment(Context applicationContext, List<PaymentPostmodel> mOrderListByCustomer, int type) {
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
            view = inflater.inflate(R.layout.adopter_payment_item, viewGroup, false);
            holder = new ViewHolder();
            holder.tv_id = (TextView) view.findViewById(R.id.tv_id_date);
            holder.tv_customer = (TextView) view.findViewById(R.id.tv_customer);
            holder.tv_amount = (TextView) view.findViewById(R.id.tv_amount);
            holder.tv_status = (TextView) view.findViewById(R.id.tv_status);
            holder.tv_date = (TextView) view.findViewById(R.id.tv_date);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }

// double DELIVERY_ORDER_ID;
//  String DELIVERY_ORDER_DATE, STATUS, REGION, CUSTOMER_NAME, SALES_PERSON_NAME;

        //  holder.tv_date.setVisibility(View.GONE);
        // fun_set_MultipalTextColor(holder.tv_date, "Shipping Date : ", mOrderListByCustomer.get(i).get_DELIVERY_ORDER_DATE());
        fun_set_MultipalTextColor(holder.tv_id, "Name : ", mOrderListByCustomer.get(i).get_CustomerName() + "");
        fun_set_MultipalTextColor(holder.tv_amount, "Date : ", mOrderListByCustomer.get(i).get_PaymentDate() + "");
        fun_set_MultipalTextColor(holder.tv_customer, "Amount : ", mOrderListByCustomer.get(i).get_PaymentAmount() + "");
        fun_set_MultipalTextColor(holder.tv_date, "Method : ", mOrderListByCustomer.get(i).get_PaymentMethod() + "");

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
        word.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_blance.append(word);
    }
}