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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.ModelClass.Product_model;
import com.tkxel.admin.ordertaking.R;

import java.util.ArrayList;
import java.util.List;

import static com.tkxel.admin.ordertaking.R.id.p_price;


public class Adapter_View_Product extends BaseAdapter implements Filterable {
    Context context;

    LayoutInflater inflter;
    List<Product_model> listProducts;


    int _price = 0;
    public List<Product_model> orig;

    public Adapter_View_Product(Context applicationContext, List<Product_model> listProducts) {
        this.context = applicationContext;
        this.listProducts = listProducts;
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
    ViewHolder holder;
      @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.list_row_view_products, viewGroup, false);
            holder = new ViewHolder();
            holder.p_name = (TextView) view.findViewById(R.id.p_name);
            holder.p_qty = (TextView) view.findViewById(R.id.p_qty);
            holder.p_price = (TextView) view.findViewById(p_price);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.p_name.setText(listProducts.get(i).get_ITEM_DESC() + "");
      //  holder.p_qty.setText(listProducts.get(i).get_p_Qty() + "");
          holder.p_qty.setVisibility(View.INVISIBLE);
        holder.p_price.setText(listProducts.get(i).get_PRICERATE() + "");
        return view;
    }


    public class ViewHolder {
        TextView p_name, p_qty, p_price;
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