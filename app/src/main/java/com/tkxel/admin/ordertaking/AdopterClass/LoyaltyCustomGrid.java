package com.tkxel.admin.ordertaking.AdopterClass;

/**
 * Created by admin on 10/28/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.R;


public class LoyaltyCustomGrid extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;

    public LoyaltyCustomGrid(Context c, String[] web, int[] Imageid) {
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
            view = inflater.inflate(R.layout.loyaltygrid_single, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.grid_text);
            holder.imageView = (ImageView) view.findViewById(R.id.grid_image);
            holder.layout_01 = (RelativeLayout) view.findViewById(R.id.layout_01);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(web[position]);
        holder.imageView.setImageResource(Imageid[position]);
        fun_Color(position);
        return view;
    }

    public class ViewHolder {
        TextView textView;
        ImageView imageView;
        RelativeLayout layout_01;

    }

    void fun_Color(int postion) {
        switch (postion) {
            case 0:
                holder.layout_01.setBackgroundColor(Color.parseColor("#71beab"));
                break;
            case 1:
                holder.layout_01.setBackgroundColor(Color.parseColor("#d063c5"));
                break;
            case 2:
                holder.layout_01.setBackgroundColor(Color.parseColor("#61558a"));
                break;
            case 3:
                holder.layout_01.setBackgroundColor(Color.parseColor("#f4816e"));
                break;
            case 4:
                holder.layout_01.setBackgroundColor(Color.parseColor("#595e66"));
                break;
            case 5:
                holder.layout_01.setBackgroundColor(Color.parseColor("#7d51ed"));
                break;
        }
    }
}