package com.tkxel.admin.ordertaking.AdopterClass;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import com.tkxel.admin.ordertaking.ModelClass.SpinnerObj;
import com.tkxel.admin.ordertaking.R;

import java.util.List;

public class AdapterCustomSpinner extends BaseAdapter {

    Context context;
    List<SpinnerObj> mSpinnerObjList;
    LayoutInflater inflter;
    int type = 0;

    public AdapterCustomSpinner(Context applicationContext, List<SpinnerObj> mSpinnerObjList, int type) {
        this.context = applicationContext;

        this.mSpinnerObjList = mSpinnerObjList;
        this.type = type;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        if (mSpinnerObjList != null)
            return mSpinnerObjList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return mSpinnerObjList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        AdapterCustomSpinner.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.adapter_productavail_row, viewGroup, false);

            holder = new AdapterCustomSpinner.ViewHolder();
            holder.et_ID = (EditText) view.findViewById(R.id.et_ID);
            holder.tv_Name = (TextView) view.findViewById(R.id.tv_Name);

            SpinnerObj spinnerObj =   mSpinnerObjList.get(i);

            int ID = spinnerObj.getID();
            String Name = spinnerObj.getName();

            holder.tv_Name.setText(Name);
            holder.et_ID.setText(Integer.toString(ID));

            view.setTag(holder);
        } else {
            holder = (AdapterCustomSpinner.ViewHolder) view.getTag();

        }

        return view;
    }


    public class ViewHolder {

        EditText et_ID;
        TextView tv_Name;


    }

}
