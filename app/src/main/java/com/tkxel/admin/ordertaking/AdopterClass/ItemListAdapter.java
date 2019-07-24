package com.tkxel.admin.ordertaking.AdopterClass;

/**
 * Created by admin on 10/6/2017.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.AllClass.CostChangedListener;
import com.tkxel.admin.ordertaking.ModelClass.Product_model;
import com.tkxel.admin.ordertaking.R;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends ArrayAdapter<Product_model> implements Filterable {
    protected static final String LOG_TAG = ItemListAdapter.class.getSimpleName();
    CostChangedListener changeItemInterface;
    private List<Product_model> items;
    private int layoutResourceId;
    private Context context;
    public List<Product_model> orig;
    ////////////////////////////////

    private List<Product_model> worldpopulationlist = null;
    private ArrayList<Product_model> arraylist;

    ///////////////////////////////




    public ItemListAdapter(Context context, int layoutResourceId, List<Product_model> items, CostChangedListener changeItemInterface) {
        super(context, layoutResourceId, items);
        this.changeItemInterface = changeItemInterface;
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;

        //////////////////////////////
        this.worldpopulationlist = items;
        this.arraylist = new ArrayList<Product_model>();
        this.arraylist.addAll(worldpopulationlist);

        ///////////////////////////////


      //  this.orig = orig;

    }
    @Override
    public int getCount() {

        return items.size();
    }
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Holder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(R.layout.row_item, parent, false);

        holder = new Holder();
        if(items.size()>=position)
        holder.bean = items.get(position);

        holder.ibDelete = (TextView) row.findViewById(R.id.ibDelete);
        // holder.ibDelete.setTag(holder.bean);

        holder.tv_name = (TextView) row.findViewById(R.id.edVal1);
        holder.etVal2 = (EditText) row.findViewById(R.id.edValue2);
        holder.tvTotal = (TextView) row.findViewById(R.id.txtTotal);
        holder.mainview = (LinearLayout) row.findViewById(R.id.mainview);


        row.setTag(holder);
        setupItem(holder);
        setVal2TextListeners(holder);
        return row;
    }

    private void setupItem(Holder holder) {
        holder.tv_name.setText(String.valueOf(holder.bean.get_ITEM_DESC()));
        holder.etVal2.setText(String.valueOf(holder.bean.getCount()));



       // tv_Tprice.setText("PKR. " + String.format("%.2f", round(holder.bean.get_tottle_Price(), 2)));

        holder.tvTotal.setText(String.format("%.2f", round(holder.bean.get_tottle_Price(), 2))+"");


        holder.ibDelete.setText(String.valueOf(holder.bean.get_PRICERATE()));

        if (holder.bean.getisSelected()) {
            holder.mainview.setBackgroundColor(Color.parseColor("#e4e4e4"));
        } else {

            holder.mainview.setBackgroundColor(Color.parseColor("#00ffffff"));
        }

    }
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    public static class Holder {
        Product_model bean;
        TextView tv_name;
        EditText etVal2;
        TextView tvTotal;
        TextView ibDelete;
        LinearLayout mainview;
    }

    double tottleQty = 0;

    private void setVal2TextListeners(final Holder holder) {
        holder.etVal2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                try {
//                    if (s.toString().length() > 0) {
//                        tottleQty = tottleQty + Double.parseDouble(s.toString());
//                        holder.bean.setCount(Double.parseDouble(s.toString()));
//                    //    notifyDataSetChanged();
//                    } else {
//                        tottleQty = tottleQty + 0;
//                        holder.bean.setCount(0);
//                    //    notifyDataSetChanged();
//                    }
//
//                } catch (NumberFormatException e) {
//                    Log.e(LOG_TAG, "error reading double value: " + s.toString());
//                }
             //   setupItem(holder);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {

                    tottleQty = tottleQty + Double.parseDouble(s.toString());
                      holder.bean.setCount(Double.parseDouble(s.toString()));
                     //   holder.bean.set_tottle_Price(Double.parseDouble(s.toString()));
                    double val1 = Double.parseDouble(holder.bean.get_PRICERATE() + "");
                    double val2 = Double.parseDouble(holder.etVal2.getText().toString());

                    holder.bean.set_tottle_Price(val1 * val2);

                    holder.tvTotal.setText(String.valueOf(val1 * val2));
                    if (val2 > 0) {
                        holder.bean.setisSelected(true);
                    } else {
                        holder.bean.setisSelected(false);
                    }


                    TotalCost();

                   //
                }

            }
        });
    }

    public List<Product_model> getCheckedItems() {

        List<Product_model> mTempArry = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getisSelected()) {
                mTempArry.add(items.get(i));
            }
        }
        return mTempArry;


    }

    double cost = 0.0;

    void TotalCost() {

        cost = 0.0;
        List<Product_model> mTempArry = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getisSelected()) {
                mTempArry.add(items.get(i));
                cost = cost + items.get(i).get_tottle_Price();
            }
        }
        changeItemInterface.onCostChanged(cost,tottleQty);

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Product_model> results = new ArrayList<Product_model>();
                if (orig == null)
                    orig = items;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Product_model g : orig) {
                            if (g.get_ITEM_DESC().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);

                        }
                    }
                    oReturn.values = results;

                }
             //   notifyDataSetChanged();
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                items = (ArrayList<Product_model>) results.values;
           //   notifyDataSetChanged();
            }

        };

    }


//    // Filter Class
//    public void filterSArdar(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        worldpopulationlist.clear();
//        if (charText.length() == 0) {
//            worldpopulationlist.addAll(arraylist);
//        }
//        else
//        {
//            for (Product_model wp : arraylist)
//            {
//                if (wp.get_ITEM_DESC().toLowerCase(Locale.getDefault()).contains(charText))
//                {
//                    worldpopulationlist.add(wp);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }


}