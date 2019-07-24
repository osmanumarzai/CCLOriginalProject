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

import com.tkxel.admin.ordertaking.R;


public class Adapter_OderCatogory extends BaseAdapter {
    Context context;
    String[] title;
    String[] description;
    LayoutInflater inflter;
    int[] myImageList;

    public Adapter_OderCatogory(Context applicationContext, String[] title, String[] description, int[] myImageList) {
        this.context = applicationContext;
        this.title = title;
        this.description = description;
        this.myImageList = myImageList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {

        return title.length;
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
            view = inflater.inflate(R.layout.list_row, viewGroup, false);
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.quenty = (TextView) view.findViewById(R.id.quenty);
            holder.descp = (TextView) view.findViewById(R.id.descp);

            holder.list_image = (ImageView) view.findViewById(R.id.list_image);

            // holder.img_1 = (ImageView) view.findViewById(R.id.img_1);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.title.setText(title[i] + "");
        holder.quenty.setText("Quantity 152");
        holder.descp.setText(description[i] + "");


        holder.list_image.setImageResource(myImageList[i]);
        return view;
    }


    public class ViewHolder {

        ImageView list_image;
        TextView title, descp;
        TextView quenty;


    }
}