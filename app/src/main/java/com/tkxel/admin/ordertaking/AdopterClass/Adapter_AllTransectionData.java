package com.tkxel.admin.ordertaking.AdopterClass;

/**
 * Created by admin on 10/6/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.ModelClass.ModelCustomer;
import com.tkxel.admin.ordertaking.R;

import java.util.List;


public class Adapter_AllTransectionData extends BaseAdapter {
    Context context;
    private List<ModelCustomer> listUsers;
    LayoutInflater inflter;

    public Adapter_AllTransectionData(Context applicationContext, List<ModelCustomer> listUsers) {
        this.context = applicationContext;

        this.listUsers = listUsers;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return listUsers.size();
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
            view = inflater.inflate(R.layout.adopter_alluser_item, viewGroup, false);
            holder = new ViewHolder();
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);


            // holder.img_1 = (ImageView) view.findViewById(R.id.img_1);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }
        holder.tv_name.setText(listUsers.get(i).get_CUSTOMER_NAME() + "");

        holder.tv_cnic.setText("CNIC : " + listUsers.get(i).get_CUSTOMER_NAME() + "");
        holder.tv_email.setText("" + listUsers.get(i).get_CUSTOMER_NAME() + "");


        holder.tv_address.setText(listUsers.get(i).get_CUSTOMER_NAME() + ", " + listUsers.get(i).get_CUSTOMER_NAME() + ", " + listUsers.get(i).get_CUSTOMER_NAME() + ", " + listUsers.get(i).get_CUSTOMER_NAME() + "");
        return view;
    }


    public class ViewHolder {
        TextView tv_name, tv_address;
        TextView tv_cnic;
        TextView tv_email;
        ImageView img_1;

    }
}