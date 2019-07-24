package com.tkxel.admin.ordertaking.AdopterClass;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.tkxel.admin.ordertaking.ModelClass.Product;
import com.tkxel.admin.ordertaking.R;

import java.util.List;

public class AdapterAllProduct extends BaseAdapter {
    Context context;
    List<Product> mProductList;
    LayoutInflater inflter;
    int type = 0;

    public AdapterAllProduct(Context applicationContext, List<Product> mProductList, int type) {
        this.context = applicationContext;

        this.mProductList = mProductList;
        this.type = type;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        if (mProductList != null)
            return mProductList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return mProductList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        AdapterAllProduct.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.adapter_productavail_row, viewGroup, false);

            holder = new AdapterAllProduct.ViewHolder();
            holder.tv_Description1 = (TextView) view.findViewById(R.id.tv_Description1);
            holder.tv_Description2 = (TextView) view.findViewById(R.id.tv_Description2);
            holder.et_ProductID = (EditText) view.findViewById(R.id.et_ProductID);
            CheckBox ch_box1 = (CheckBox) view.findViewById(R.id.ch_box1);


            Product product =   mProductList.get(i);

            int et_ProductID = product.getProductID();
            String Description1 = product.getName1() ;
            String Description2 = product.getName3();

            holder.tv_Description1.setText(Description1);
            holder.tv_Description2.setText(Description2);
            holder.et_ProductID.setText(Integer.toString(et_ProductID));

            holder.ch_box1  = ch_box1;


            view.setTag(holder);
        } else {
            holder = (AdapterAllProduct.ViewHolder) view.getTag();

        }

        return view;
    }




public class ViewHolder {

        TextView tv_Description1, tv_Description2;
        CheckBox ch_box1;
        EditText et_ProductID;

    }

}