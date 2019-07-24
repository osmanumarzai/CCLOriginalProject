package com.tkxel.admin.ordertaking.AdopterClass;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.AllClass.ProductAvailAddActivity;
import com.tkxel.admin.ordertaking.Loyalty.CustomFilter;
import com.tkxel.admin.ordertaking.ModelClass.Customer;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.SharedPreferencesSingletonStringKey;

import java.util.ArrayList;
import java.util.List;

public class AdapterAllCustomer extends RecyclerView.Adapter<AdapterAllCustomer.CustomerViewHolder>  implements Filterable {

    Context c;
    public ArrayList<Customer> customers,filterList;
    CustomFilter filter;

    public AdapterAllCustomer(Context ctx,ArrayList<Customer> customers)
    {
        this.c=ctx;
        this.customers=customers;
        this.filterList=customers;
    }



    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_customer_row, parent, false);

//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_customer_row,null);



        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {


        Customer cust = customers.get(position);

//        String Description1 = cust.getShopName()+ " - " + cust.getShopType() ;
//        String Description2 = cust.getShopkeeperName()+ " - " + cust.getContactNum();
//        String Description3 = cust.getAddress();

//        String Description1 = cust.getShopName();
//        String Description2 = cust.getShopkeeperName();
//        String Description3 = cust.getAddress();

        String Description1 = cust.getShopName();
        String Description2 = cust.getCity();
        String Description3 = cust.getArea();

        holder.tv_Description1.setText(Description1);
        holder.tv_Description2.setText(Description2);
        holder.tv_Description3.setText(Description3);

        //IMPLEMENT CLICK LISTENET
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //Toast.makeText(v, "a1" , Toast.LENGTH_LONG).show();
                Snackbar.make(v,customers.get(pos).getShopName() + String.valueOf(pos),Snackbar.LENGTH_SHORT).show();
                int custID = customers.get(pos).getCustomer_Id();

                SharedPreferencesSingletonStringKey.getInstance().put(SharedPreferencesSingletonStringKey.Key.CustomerID,custID);


            }
        });

        //holder.setIsRecyclable(false);


    }

    @Override
    public int getItemCount() {
        return customers.size();
    }




    class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemClickListener itemClickListener;
        TextView tv_Description1, tv_Description2,tv_Description3;

        CustomerViewHolder(View itemView) {
            super(itemView);

            tv_Description1 = (TextView) itemView.findViewById(R.id.tv_Description1);
            tv_Description2 = (TextView) itemView.findViewById(R.id.tv_Description2);
            tv_Description3 = (TextView) itemView.findViewById(R.id.tv_Description3);
//
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());

        }

        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }

    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    customerListFiltered = dataList;
//                } else {
//                    List<Customer> filteredList = new ArrayList<>();
//                    for (Customer row : dataList) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
////                        if (row.getShopName().toLowerCase().contains(charString.toLowerCase()) || row.getContactNum().contains(charSequence)) {
////                            filteredList.add(row);
////                        }
//
//                        if (row.getShopName().contains(charString) ) {
//                            filteredList.add(row);
//                        }
//
//                    }
//
//                    customerListFiltered = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = customerListFiltered;
//                filterResults.count = customerListFiltered.size();
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//
//
//                customerListFiltered = (ArrayList<Customer>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    //RETURN FILTER OBJ
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }

        return filter;
    }


    public interface ItemClickListener {

        void onItemClick(View v,int pos);


    }

}

