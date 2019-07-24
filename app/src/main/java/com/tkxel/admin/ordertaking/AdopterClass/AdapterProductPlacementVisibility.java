package com.tkxel.admin.ordertaking.AdopterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.ModelClass.Product;
import com.tkxel.admin.ordertaking.ModelClass.ProductLevel1;
import com.tkxel.admin.ordertaking.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AdapterProductPlacementVisibility extends BaseAdapter {


    Context mContext;
    List<ProductLevel1> linkedList;
    protected LayoutInflater vi;
    int type = 0;

    private boolean[] checkBoxState_1 = null;
    private HashMap<ProductLevel1, Boolean> checkedForProductLevel_1 = new HashMap<>();



    public AdapterProductPlacementVisibility(Context context, List<ProductLevel1> linkedList, int type) {
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

        AdapterProductPlacementVisibility.ViewHolder holder;
        if (view == null) {
            view = vi.inflate(R.layout.adapter_productplacementvisibility_rowcheck, parent, false);
            holder = new AdapterProductPlacementVisibility.ViewHolder();
            holder.tv_Description1 = (TextView) view.findViewById(R.id.tv_Description1);

            holder.et_ProductID = (EditText) view.findViewById(R.id.et_ProductID);
            holder.ch_box1 = (CheckBox) view.findViewById(R.id.ch_box1);


            view.setTag(holder);

        }
        else {
            holder = (AdapterProductPlacementVisibility.ViewHolder) view.getTag();
        }

        final ProductLevel1 productLevel1 = linkedList.get(i);
        checkBoxState_1 = new boolean[linkedList.size()];

        int et_ProductID = productLevel1.getId();
        String Description1 = productLevel1.getName() ;

//        Toast.makeText(mContext.getApplicationContext(), Description1, Toast.LENGTH_SHORT).show();

        holder.tv_Description1.setText(Description1);

        holder.et_ProductID.setText(Integer.toString(et_ProductID));


        /** checkBoxState has the value of checkBox ie true or false,
         * The position is used so that on scroll your selected checkBox maintain its state **/
        if(checkBoxState_1 != null)
            holder.ch_box1.setChecked(checkBoxState_1[i]);

        holder.ch_box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    checkBoxState_1[i] = true;
                    ischecked(i,true);
                }
                else {
                    checkBoxState_1[i] = false;
                    ischecked(i,false);
                }
            }
        });


        /**if country is in checkedForCountry then set the checkBox to true **/
        if (checkedForProductLevel_1.get(productLevel1) != null) {
            holder.ch_box1.setChecked(checkedForProductLevel_1.get(productLevel1));
        }

        /**Set tag to all checkBox**/
        holder.ch_box1.setTag(productLevel1);

        return view;
    }


    public class ViewHolder {

        TextView tv_Description1;
                //, tv_Description2;
        CheckBox ch_box1;
        EditText et_ProductID;

    }

    public void ischecked(int position,boolean flag )
    {
        checkedForProductLevel_1.put(this.linkedList.get(position), flag);
    }



}