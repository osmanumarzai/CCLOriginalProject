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

import com.tkxel.admin.ordertaking.R;


public class LoyaltyProducts_adopter extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;

    public LoyaltyProducts_adopter(Context c, String[] web, int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
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

    ViewHolder holder;

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub

        if (view == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            view = inflater.inflate(R.layout.loyaltyproduct_single, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.grid_text);
            holder.imageView = (ImageView) view.findViewById(R.id.grid_image);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(web[position]);
        holder.imageView.setImageResource(Imageid[position]);

        return view;
    }

    public class ViewHolder {
        TextView textView;
        ImageView imageView;


    }


}