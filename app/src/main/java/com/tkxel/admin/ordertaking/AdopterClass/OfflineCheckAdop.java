package com.tkxel.admin.ordertaking.AdopterClass;

/**
 * Created by admin on 10/28/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.ModelClass.Product_model;
import com.tkxel.admin.ordertaking.R;

import java.util.List;


public class OfflineCheckAdop extends BaseAdapter {
    private Context mContext;
    List<Product_model> arrayList ;


    public OfflineCheckAdop(Context c, List<Product_model> arrayList) {
        mContext = c;

        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            view = inflater.inflate(R.layout.grid_single, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.grid_text);
            holder.imageView = (ImageView) view.findViewById(R.id.grid_image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(arrayList.get(position).get_ITEM_DESC());
        holder.imageView.setVisibility(View.GONE);
        return view;
    }

    public class ViewHolder {
        TextView textView;
        ImageView imageView;

    }
}