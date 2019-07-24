package com.tkxel.admin.ordertaking.AdopterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.ModelClass.ProductLevel1;
import com.tkxel.admin.ordertaking.R;


import java.util.ArrayList;

public class AdapterProductAdvertisement  extends RecyclerView.Adapter<AdapterProductAdvertisement.ProductAdvertisementViewHolder> {

    Context c;
    public ArrayList<ProductLevel1> mProductLevel1List;


    public AdapterProductAdvertisement(Context ctx,ArrayList<ProductLevel1> mproductLevel1List)
    {
        this.c=ctx;
        this.mProductLevel1List =mproductLevel1List;
    }



    @Override
    public AdapterProductAdvertisement.ProductAdvertisementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_productadvertisement_row, parent, false);


        return new AdapterProductAdvertisement.ProductAdvertisementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterProductAdvertisement.ProductAdvertisementViewHolder holder, int position) {


        ProductLevel1 productLevel1 =   mProductLevel1List.get(position);

        int et_ProductID = productLevel1.getId();
        String Description1 = productLevel1.getName() ;
        holder.tv_Description1.setText(Description1);
        holder.et_ProductID.setText(Integer.toString(et_ProductID));

//        holder.setIsRecyclable(false);


    }

    @Override
    public int getItemCount() {
        return mProductLevel1List.size();
    }




    class ProductAdvertisementViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Description1;
        EditText et_1,et_2,et_3;
        EditText et_5,et_6,et_7;
//                et_4;
        EditText et_ProductID;

        ProductAdvertisementViewHolder(View itemView) {
            super(itemView);

            tv_Description1 = (TextView) itemView.findViewById(R.id.tv_Description1);
            et_ProductID = (EditText) itemView.findViewById(R.id.et_ProductID);
            et_1 = (EditText) itemView.findViewById(R.id.et_1);
            et_2 = (EditText) itemView.findViewById(R.id.et_2);
            et_3 = (EditText) itemView.findViewById(R.id.et_3);
//            et_4 = (EditText) itemView.findViewById(R.id.et_4);
            et_5 = (EditText) itemView.findViewById(R.id.et_5);
            et_6 = (EditText) itemView.findViewById(R.id.et_6);
            et_7 = (EditText) itemView.findViewById(R.id.et_7);


        }



    }

}


