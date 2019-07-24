package com.tkxel.admin.ordertaking.AdopterClass;

/**
 * Created by admin on 10/6/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.ModelClass.ModelCustomer;
import com.tkxel.admin.ordertaking.R;

import java.util.List;


public class Adapter_All_Customer extends BaseAdapter {
    Context context;
    private List<ModelCustomer> listUsers;
    LayoutInflater inflter;

    public Adapter_All_Customer(Context applicationContext, List<ModelCustomer> listUsers) {
        this.context = applicationContext;

        this.listUsers = listUsers;
         inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return listUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        //  view = inflter.inflate(R.layout.adopter_alluser_item, null);


        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.adopter_alluser_item, viewGroup, false);
            holder = new ViewHolder();
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_city = (TextView) view.findViewById(R.id.tv_city);

            holder.img_edit = (ImageView) view.findViewById(R.id.img_edit);
            holder.img_delet = (ImageView) view.findViewById(R.id.img_delete);
            // holder.img_1 = (ImageView) view.findViewById(R.id.img_1);
            view.setTag(holder);
        } else {
            // details of every shop //
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_name.setText(listUsers.get(i).get_CUSTOMER_NAME() + "");
        //  holder.tv_name.setText(listUsers.get(i).getName() + "");

            holder.tv_city.setText("" + listUsers.get(i).get_CREDIT_LIMIT());


        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, Update_Customer_Activity.class);
//                intent.putExtra("position", i);
//                context.startActivity(intent);

            }
        });
        holder.img_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   NoView_Dialog(listUsers.get(i).getName() + "", i);
            }
        });


        return view;
    }

    void NoView_Dialog(String str, final int position) {

        final Dialog dialog = new Dialog(this.context);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.item_no_view_dialog);

        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView tv_delete = (TextView) dialog.findViewById(R.id.tv_delete);
        tv_delete.setText("Are you sure you want to Delete\n" + str);


        dialog.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }




    public class ViewHolder {
        TextView tv_name, tv_city;
        ImageView img_edit, img_delet;

    }
}