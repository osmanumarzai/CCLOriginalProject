package com.tkxel.admin.ordertaking.AdopterClass;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.ModelClass.ProductLevel1;
import com.tkxel.admin.ordertaking.R;

import java.util.List;

public class AdapterAllProductAdvertisement extends BaseAdapter {
    Context context;
    List<ProductLevel1> mProductLevel1List;
    LayoutInflater inflter;
    int type = 0;

    public AdapterAllProductAdvertisement(Context applicationContext, List<ProductLevel1> mProductLevel1List, int type) {
        this.context = applicationContext;

        this.mProductLevel1List = mProductLevel1List;
        this.type = type;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        if (mProductLevel1List != null)
            return mProductLevel1List.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return mProductLevel1List.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        AdapterAllProductAdvertisement.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.adapter_productadvertisement_row, viewGroup, false);

            holder = new AdapterAllProductAdvertisement.ViewHolder();
            holder.tv_Description1 = (TextView) view.findViewById(R.id.tv_Description1);
            holder.et_ProductID = (EditText) view.findViewById(R.id.et_ProductID);
            EditText et_1 = (EditText) view.findViewById(R.id.et_1);
            EditText et_2 = (EditText) view.findViewById(R.id.et_2);
            EditText et_3 = (EditText) view.findViewById(R.id.et_3);
            EditText et_4 = (EditText) view.findViewById(R.id.et_4);
            EditText et_5 = (EditText) view.findViewById(R.id.et_5);
            EditText et_6 = (EditText) view.findViewById(R.id.et_6);


            ProductLevel1 productLevel1 =   mProductLevel1List.get(i);

            int et_ProductID = productLevel1.getId();
            String Description1 = productLevel1.getName() ;
            holder.tv_Description1.setText(Description1);
            holder.et_ProductID.setText(Integer.toString(et_ProductID));

            holder.et_1  = et_1;
            holder.et_2  = et_2;
            holder.et_3  = et_3;
            holder.et_4  = et_4;
            holder.et_5  = et_5;
            holder.et_6  = et_6;


            view.setTag(holder);
        } else {
            holder = (AdapterAllProductAdvertisement.ViewHolder) view.getTag();

        }

        return view;
    }


    public class ViewHolder {

        TextView tv_Description1;
        EditText et_1,et_2,et_3,et_4,et_5,et_6;
        EditText et_ProductID;

    }

}