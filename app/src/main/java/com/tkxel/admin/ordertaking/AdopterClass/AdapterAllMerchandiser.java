package com.tkxel.admin.ordertaking.AdopterClass;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.ModelClass.Merchandiser;
import com.tkxel.admin.ordertaking.R;

import java.util.ArrayList;

public class AdapterAllMerchandiser extends RecyclerView.Adapter<AdapterAllMerchandiser.MerchandiserViewHolder> {
    private ArrayList<Merchandiser > dataList;

    public AdapterAllMerchandiser(ArrayList<Merchandiser> dataList) {
        this.dataList = dataList;
    }

    @Override
    public AdapterAllMerchandiser.MerchandiserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_merchandiser_row, parent, false);
        return new AdapterAllMerchandiser.MerchandiserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterAllMerchandiser.MerchandiserViewHolder holder, int position) {


        Merchandiser mer = dataList.get(position);

        String Description1 = mer.getMerchandiserName() + " | " + mer.getFatherName();
        String Description2 = mer.getMobileNumber();
        String Description3 = mer.getAddress();

        holder.tv_Description1.setText(Description1);
        holder.tv_Description2.setText(Description2);
        holder.tv_Description3.setText(Description3);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MerchandiserViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Description1, tv_Description2,tv_Description3;

        MerchandiserViewHolder(View itemView) {
            super(itemView);

            tv_Description1 = (TextView) itemView.findViewById(R.id.tv_Description1);
            tv_Description2 = (TextView) itemView.findViewById(R.id.tv_Description2);
            tv_Description3 = (TextView) itemView.findViewById(R.id.tv_Description3);
        }
    }
}

