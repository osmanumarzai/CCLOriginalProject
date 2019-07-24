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

import static com.tkxel.admin.ordertaking.R.id.p_minus;


public class Adapter_TAkeOrder extends BaseAdapter implements Filterable {
    Context context;

    LayoutInflater inflter;
    List<Product_model> listProducts;


    List<Product_model> listProducts_order;

    public List<Product_model> orig;
    CostChangedListener changeItemInterface;
    int _price = 0;

    public Adapter_TAkeOrder(Context applicationContext, List<Product_model> listProducts, CostChangedListener changeItemInterface) {
        this.context = applicationContext;
        this.listProducts = listProducts;
        this.changeItemInterface = changeItemInterface;
        listProducts_order = new ArrayList<>();
        listProducts_order.clear();
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {

        return listProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    int currentPrice = 0;
    ViewHolder holder;

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        //  view = inflter.inflate(R.layout.adopter_alluser_item, null);

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.list_row_products, viewGroup, false);
            holder = new ViewHolder();

            holder.p_name = (TextView) view.findViewById(R.id.p_name);
            holder.p_qty = (TextView) view.findViewById(R.id.p_qty);
            holder.p_price = (TextView) view.findViewById(R.id.p_price);
            holder.cbBuy = (CheckBox) view.findViewById(R.id.cbBox);

            holder.p_plus = (TextView) view.findViewById(R.id.p_plus);
            holder.p_minus = (TextView) view.findViewById(p_minus);
            holder.etp_counter = (EditText) view.findViewById(R.id.etp_counter);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.p_name.setTag(i);
        holder.cbBuy.setTag(i);
        holder.cbBuy.setChecked(listProducts.get(i).get_isBox());


        holder.p_name.setText(listProducts.get(i).get_ITEM_DESC() + "");
       // holder.p_qty.setText(listProducts.get(i).get_p_Qty() + "");

        holder.p_qty.setVisibility(View.INVISIBLE);
        holder.p_price.setText(listProducts.get(i).get_PRICERATE() + "");
        holder.p_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickHandler_plus(v, i);
            }
        });
        holder.p_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickHandler_Minus(v);

            }
        });

        return view;
    }

    Product_model getProduct(int position) {
        return ((Product_model) getItem(position));
    }

    ArrayList<Product_model> getBox() {
        ArrayList<Product_model> box = new ArrayList<Product_model>();

        for (int i = 0; i < listProducts.size(); i++) {
            if (listProducts.get(i).get_isBox())
                box.add(listProducts.get(i));
        }
        return box;
    }


    public class ViewHolder {
        TextView p_name, p_qty, p_price, p_plus, p_minus;
        EditText etp_counter;
        CheckBox cbBuy;
    }

    int totle_price;

    public void myClickHandler_plus(View v, int position) {
        RelativeLayout vwParent = (RelativeLayout) v.getParent();
        TextView child_1 = (TextView) vwParent.getChildAt(0);
        TextView child_2 = (TextView) vwParent.getChildAt(1);
        TextView child_3 = (TextView) vwParent.getChildAt(2);

        int p_price = Integer.parseInt(child_3.getText() + "");

        EditText edit_3 = (EditText) vwParent.getChildAt(4);

        int totleQty_pervious = Integer.parseInt(edit_3.getText() + "");

        int totleQty = Integer.parseInt(edit_3.getText() + "");

        totleQty = totleQty + 1;
        edit_3.setText("" + totleQty);

        totle_price = p_price * (totleQty - totleQty_pervious);

        _price = _price + totle_price;

        this.changeItemInterface.onCostChanged(_price,0);
        int remaingQty = Integer.parseInt(child_2.getText() + "") - 1;

        child_2.setText(remaingQty + "");


        if (totleQty == 0) {
            listProducts_order.remove(this.listProducts.get(position));
            vwParent.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.cbBuy.setChecked(true);
           listProducts.get(position).set_isBox(false );

        } else {
            listProducts_order.add(this.listProducts.get(position));
            holder.cbBuy.setChecked(false);
            listProducts.get(position).set_isBox(true);
            vwParent.setBackgroundColor(Color.parseColor("#a9a9a9"));


        }
        //   vwParent.refreshDrawableState();

    }

    public void myClickHandler_Minus(View v) {
        RelativeLayout vwParent = (RelativeLayout) v.getParent();
        TextView child_1 = (TextView) vwParent.getChildAt(0);
        TextView child_2 = (TextView) vwParent.getChildAt(1);
        TextView child_3 = (TextView) vwParent.getChildAt(2);
        EditText edit_3 = (EditText) vwParent.getChildAt(4);

        int p_price = Integer.parseInt(child_3.getText() + "");
        int totleQty_pervious = Integer.parseInt(edit_3.getText() + "");
        int totleQty = Integer.parseInt(edit_3.getText() + "");
        totleQty = totleQty - 1;
        if (totleQty <= 0) {
            totleQty = 0;
            edit_3.setText("" + 0);
            totle_price = (p_price * (totleQty_pervious));
        } else {

            edit_3.setText("" + totleQty);
            totle_price = (p_price * (totleQty_pervious - totleQty));
        }
        _price = _price - totle_price;
        this.changeItemInterface.onCostChanged(_price,0);
        int remaingQty = Integer.parseInt(child_2.getText() + "") + 1;
        child_2.setText(remaingQty + "");
        if (totleQty <= 0) {

            vwParent.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            vwParent.setBackgroundColor(Color.parseColor("#a9a9a9"));
        }
        //    vwParent.refreshDrawableState();
    }


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

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


}