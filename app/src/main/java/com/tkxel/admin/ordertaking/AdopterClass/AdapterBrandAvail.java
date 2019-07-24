package com.tkxel.admin.ordertaking.AdopterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.ModelClass.Product;
import com.tkxel.admin.ordertaking.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AdapterBrandAvail extends BaseAdapter {


    Context mContext;
    List<Product> linkedList;
    protected LayoutInflater vi;
    int type = 0;

    private boolean[] checkBoxState = null;
    private HashMap<Product, Boolean> checkedForProduct = new HashMap<>();



    public AdapterBrandAvail(Context context, List<Product> linkedList, int type) {
        this.mContext = context;
        this.linkedList = linkedList;
        this.type = type;
        this.vi = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return linkedList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {

        AdapterBrandAvail.ViewHolder holder;
        if (view == null) {
            view = vi.inflate(R.layout.adapter_productavail_row, parent, false);
            holder = new AdapterBrandAvail.ViewHolder();
            holder.tv_Description1 = (TextView) view.findViewById(R.id.tv_Description1);
            holder.tv_Description2 = (TextView) view.findViewById(R.id.tv_Description2);
            holder.et_ProductID = (EditText) view.findViewById(R.id.et_ProductID);
            holder.ch_box1 = (CheckBox) view.findViewById(R.id.ch_box1);


            view.setTag(holder);

        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        final Product product = linkedList.get(i);
        checkBoxState = new boolean[linkedList.size()];

        int et_ProductID = product.getProductID();
        String Description1 = product.getName1() ;
        String Description2 = product.getName3();

//        String name = Description1 + "-" + Description2;
        holder.tv_Description1.setText(Description1);
        holder.tv_Description2.setText(Description2);
        holder.et_ProductID.setText(Integer.toString(et_ProductID));


        /** checkBoxState has the value of checkBox ie true or false,
         * The position is used so that on scroll your selected checkBox maintain its state **/
        if(checkBoxState != null)
            holder.ch_box1.setChecked(checkBoxState[i]);

        holder.ch_box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    checkBoxState[i] = true;
                    ischecked(i,true);
                }
                else {
                    checkBoxState[i] = false;
                    ischecked(i,false);
                }
            }
        });


        /**if country is in checkedForCountry then set the checkBox to true **/
        if (checkedForProduct.get(product) != null) {
            holder.ch_box1.setChecked(checkedForProduct.get(product));
        }

        /**Set tag to all checkBox**/
        holder.ch_box1.setTag(product);

        return view;
    }


    public class ViewHolder {

        TextView tv_Description1,tv_Description2;
        CheckBox ch_box1;
        EditText et_ProductID;

    }

    public void ischecked(int position,boolean flag )
    {
        checkedForProduct.put(this.linkedList.get(position), flag);
    }


    public LinkedList<String> getSelectedCountry(){
        LinkedList<String> List = new LinkedList<>();
        for (Map.Entry<Product, Boolean> pair : checkedForProduct.entrySet()) {
            if(pair.getValue()) {
                List.add(pair.getKey().getName3());
            }
        }
        return List;
    }
}