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


public class Adapter_Product extends BaseAdapter implements Filterable {
    Context context;

    LayoutInflater inflter;
    List<Product_model> listProducts;

    CostChangedListener changeItemInterface;
    double _price = 0.0;
    public List<Product_model> orig;

    public Adapter_Product(Context applicationContext, List<Product_model> listProducts, CostChangedListener changeItemInterface) {
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

    private View lastSelectedView = null;

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        //  view = inflter.inflate(R.layout.adopter_alluser_item, null);
        ViewHolder holder = null;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.list_row_products, viewGroup, false);
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

        //   holder.etp_counter.setId(i);
        holder.item = listProducts.get(i);

        holder.p_name.setText(holder.item.get_ITEM_DESC() + "");
        holder.p_qty.setVisibility(View.INVISIBLE);
        holder.p_price.setText(holder.item.get_PRICERATE() + "");
        holder.etp_counter.setText("" + holder.item.getCount());
        holder.cbBox.setChecked(holder.item.getisSelected());


        if (holder.item.getisSelected()) {
            holder.mainview.setBackgroundColor(Color.parseColor("#e4e4e4"));
        } else {

            holder.mainview.setBackgroundColor(Color.parseColor("#00ffffff"));
        }

        setVal2TextListeners(holder);
//        holder.p_plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int increase_count = holder.item.getCount();
//                holder.item.setCount(++increase_count);
//
//                double p_price = holder.item.get_PRICERATE();
//                _price = Double.parseDouble(String.format("%.2f", _price)) + Double.parseDouble(String.format("%.2f", p_price));
//                //  _price = _price + p_price;
//                changeItemInterface.onCostChanged(_price);
//
//                if (increase_count > 0) {
//                    holder.item.setisSelected(true);
//                } else {
//                    holder.item.setisSelected(false);
//                }
//                notifyDataSetChanged();
//            }
//        });


        return view;
    }

    //int increase_count = 0;

    public static class ViewHolder {
        CheckBox cbBox;
        RelativeLayout mainview;
        TextView p_name, p_qty, p_price, p_plus, p_minus;
        EditText etp_counter;
        Product_model item;
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

    private void setVal2TextListeners(final ViewHolder holder) {

        holder.etp_counter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    String s = Caption.getText().toString();
                    if (s.toString().length() > 0) {

                        holder.item.setCount(Integer.parseInt(s.toString()));

                        double _count = holder.item.getCount();
                        double PreiceItm = Double.parseDouble(String.format("%.2f", holder.item.get_PRICERATE()));

                        holder.item.set_tottle_Price(_count * PreiceItm);

                        //   _price = _price + (_count * PreiceItm);

                        holder.p_name.setText(holder.item.get_ITEM_DESC() + "");
                      //  changeItemInterface.onCostChanged(_price);
                    }


                }
            }

        });


//
//        holder.etp_counter.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                try {
//                    if (s.toString().length() > 0) {
//                        holder.item.setCount(Integer.parseInt(s.toString()));
//                    }
//                } catch (NumberFormatException e) {
//
//                }
//            }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString().length() > 0) {
//                    int _count = holder.item.getCount();
//                    double PreiceItm = Double.parseDouble(String.format("%.2f", holder.item.get_PRICERATE()));
//                    _price=  _price+ (_count * PreiceItm);
//
//                    changeItemInterface.onCostChanged(_price);
//
//                }
//
//            }
//        });
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