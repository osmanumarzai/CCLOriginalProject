package com.tkxel.admin.ordertaking.AdopterClass;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.ModelClass.Product;
import com.tkxel.admin.ordertaking.R;

import java.util.List;

public class AdapterAllProductPlacementVisibility extends BaseAdapter {
    Context context;
    List<Product> mProductList;
    LayoutInflater inflter;
    int type = 0;

    public AdapterAllProductPlacementVisibility(Context applicationContext, List<Product> mproduct1list, int type) {
        this.context = applicationContext;
        this.mProductList = mproduct1list;
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

        AdapterAllProductPlacementVisibility.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.adapter_productplacementvisibility_row, viewGroup, false);

            holder = new AdapterAllProductPlacementVisibility.ViewHolder();
            holder.tv_Description1 = (TextView) view.findViewById(R.id.tv_Description1);
            holder.et_ProductID = (EditText) view.findViewById(R.id.et_ProductID);
            EditText et_1 = (EditText) view.findViewById(R.id.et_1);
            EditText et_2 = (EditText) view.findViewById(R.id.et_2);
            EditText et_3 = (EditText) view.findViewById(R.id.et_3);
            EditText et_4 = (EditText) view.findViewById(R.id.et_4);



            Product product =   mProductList.get(i);

            int et_ProductID = product.getProductID();
            String Description1 = product.getName1()  ;
            holder.tv_Description1.setText(Description1);
            holder.et_ProductID.setText(Integer.toString(et_ProductID));
//
            holder.et_1  = et_1;
            holder.et_2  = et_2;
            holder.et_3  = et_3;
            holder.et_4  = et_4;
//
            view.setTag(holder);
        } else {
            holder = (AdapterAllProductPlacementVisibility.ViewHolder) view.getTag();

        }

        return view;
    }


    public class ViewHolder {

        TextView tv_Description1;
        EditText et_1,et_2,et_3,et_4;
        EditText et_ProductID;

    }

}