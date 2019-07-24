package com.tkxel.admin.ordertaking.AdopterClass;

/**
 * Created by admin on 10/27/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.ModelClass.ModelCustomer;
import com.tkxel.admin.ordertaking.R;

import java.util.List;


public class Spinner_CustomAdapter extends BaseAdapter {
    Context context;

    List<ModelCustomer> listUsers;
    LayoutInflater inflter;

    public Spinner_CustomAdapter(Context applicationContext, List<ModelCustomer> listUsers) {
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
        view = inflter.inflate(R.layout.custom_spinner_items, null);

        TextView names = (TextView) view.findViewById(R.id.textView);

        names.setText(listUsers.get(i).get_CUSTOMER_NAME() + "");
        return view;
    }
}