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
import android.widget.TextView;

import com.tkxel.admin.ordertaking.ModelClass.Product_model;
import com.tkxel.admin.ordertaking.R;

import java.util.List;


public class Adapter_Order_View extends BaseAdapter {
    Context context;

    LayoutInflater inflter;
    List<Product_model> listProducts;

    public Adapter_Order_View(Context applicationContext, List<Product_model> listProducts) {
        this.context = applicationContext;
        this.listProducts = listProducts;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return listProducts.size();
    }

    @Override
    public Product_model getItem(int position) {
        return listProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ViewHolder holder;

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        //  view = inflter.inflate(R.layout.adopter_alluser_item, null);

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.list_row_products_order, viewGroup, false);
            holder = new ViewHolder();

            holder.p_name = (TextView) view.findViewById(R.id.name);
            holder.p_qty = (TextView) view.findViewById(R.id.qty);
            holder.p_price = (TextView) view.findViewById(R.id.price);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.p_name.setText(listProducts.get(i).get_ITEM_DESC() + "");
         holder.p_qty.setText(listProducts.get(i).getCount()+"");
       // holder.p_qty.setText("Waheed");
        holder.p_price.setText(listProducts.get(i).get_PRICERATE() + "");

        return view;
    }


    public class ViewHolder {
        TextView p_name, p_qty, p_price;

    }


}