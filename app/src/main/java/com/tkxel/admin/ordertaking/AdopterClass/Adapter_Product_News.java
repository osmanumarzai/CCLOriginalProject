package com.tkxel.admin.ordertaking.AdopterClass;

/**
 * Created by admin on 10/6/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.AllClass.CostChangedListener;
import com.tkxel.admin.ordertaking.ModelClass.Product_model;
import com.tkxel.admin.ordertaking.R;

import java.util.ArrayList;
import java.util.List;


public class Adapter_Product_News extends BaseAdapter implements Filterable {
    Context context;

    LayoutInflater inflter;
    List<Product_model> listProducts;

    CostChangedListener changeItemInterface;
    double _price = 0.0;
    public List<Product_model> orig;

    public Adapter_Product_News(Context applicationContext, List<Product_model> listProducts, CostChangedListener changeItemInterface) {
        this.context = applicationContext;
        this.listProducts = listProducts;
        this.changeItemInterface = changeItemInterface;

        inflter = (LayoutInflater.from(applicationContext));


    }

    @Override
    public int getCount() {

        return listProducts.size();
    }

    @Override
    public Product_model getItem(int position) {
        return listProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    int currentPrice = 0;
    ViewHolder holder;
    private View lastSelectedView = null;

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        //  view = inflter.inflate(R.layout.adopter_alluser_item, null);

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.list_row_products_new, viewGroup, false);
            holder = new ViewHolder();
            holder.mainview = (RelativeLayout) view.findViewById(R.id.mainview);
            holder.p_name = (TextView) view.findViewById(R.id.p_name);
            holder.p_qty = (TextView) view.findViewById(R.id.p_qty);
            holder.p_price = (TextView) view.findViewById(R.id.p_price);
            holder.p_plus = (TextView) view.findViewById(R.id.p_plus);
            holder.p_minus = (TextView) view.findViewById(R.id.p_minus);
            holder.etp_counter = (EditText) view.findViewById(R.id.etp_counter);

            holder.cbBox = (CheckBox) view.findViewById(R.id.cbBox);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.p_name.setText(listProducts.get(i).get_ITEM_DESC() + "");

        holder.p_qty.setVisibility(View.INVISIBLE);
        holder.p_price.setText(listProducts.get(i).get_PRICERATE() + "");

     //   int totleQty = Integer.parseInt(holder.etp_counter. getText()+ "");


        final Product_model item = listProducts.get(i);
        holder.etp_counter.setText("" + item.getCount());



        holder.cbBox.setChecked(item.getisSelected());

        if (item.getisSelected()) {
            holder.mainview.setBackgroundColor(Color.parseColor("#e4e4e4"));
        } else {

            holder.mainview.setBackgroundColor(Color.parseColor("#ffffff"));
        }


        holder.p_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long viewId = v.getId();

//                int increase_count = item.getCount();
//                item.setCount(++increase_count);
//
//                double p_price = item.get_PRICERATE();
//                _price = _price + p_price;
//                changeItemInterface.onCostChanged(_price);
//
//                if (increase_count > 0) {
//                    item.setisSelected(true);
//                } else {
//                    item.setisSelected(false);
//                }
//                notifyDataSetChanged();
            }
        });


        holder.p_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double decrease_count = item.getCount();

                if (decrease_count <= 0) {
                    item.setCount(0);
                    changeItemInterface.onCostChanged(_price,0);

                } else {
                   // item.setCount(--decrease_count);
                    double p_price = item.get_PRICERATE();
                    _price = _price - p_price;
                    changeItemInterface.onCostChanged(_price,0);
                }
                //   holder.etp_counter.setText("" + item.getCount());
                if (decrease_count > 0) {
                    item.setisSelected(true);
                } else {
                    item.setisSelected(false);
                }

                notifyDataSetChanged();
            }
        });



        return view;
    }

    //int increase_count = 0;

    public class ViewHolder {
        CheckBox cbBox;
        RelativeLayout mainview;
        TextView p_name, p_qty, p_price, p_plus, p_minus;
        EditText etp_counter;
    }


    public List<Product_model> getCheckedItems() {

        List<Product_model> mTempArry = new ArrayList<>();
        for (int i = 0; i < listProducts.size(); i++) {
            if (listProducts.get(i).getisSelected()) {
                mTempArry.add(listProducts.get(i));
            }
        }
        return mTempArry;


    }


    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Product_model> results = new ArrayList<Product_model>();
                if (orig == null)
                    orig = listProducts;
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
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                listProducts = (ArrayList<Product_model>) results.values;
                notifyDataSetChanged();
            }
        };
    }


}