package com.tkxel.admin.ordertaking.Loyalty;

import android.widget.Filter;

import com.tkxel.admin.ordertaking.AdopterClass.AdapterAllCustomer;
import com.tkxel.admin.ordertaking.ModelClass.Customer;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    AdapterAllCustomer adapter;
    ArrayList<Customer> filterList;

    String store="",city="",area="";

    public CustomFilter(ArrayList<Customer> filterList,AdapterAllCustomer adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {

//           final  StringBuffer stringBuffer = new StringBuffer(constraint);
//            StringBuilder sb = new StringBuilder(constraint.length());
//            sb.append(constraint);
//
//            String text =  sb.toString();
//
//            String[] separated = text.split("-");
//
//            if (separated != null &&   separated.length == 1)
//            {
//                store = separated[0];
//            }
//            if (separated != null && separated.length == 2)
//            {
//                store = separated[0];
//                city = separated[1];
//            }
//            if (separated != null && separated.length == 3)
//            {
//                store = separated[0];
//                city = separated[1];
//                area = separated[2];
//            }
//
//
//            int x = 1;

            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Customer> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
//                if (store != null)
//                {
//                    //CHECK
//                    if(filterList.get(i).getShopName().toUpperCase().contains(store))
//                    {
//                        //ADD PLAYER TO FILTERED PLAYERS
//                        filteredPlayers.add(filterList.get(i));
//                    }
//
//                }
//
//                if (city != null)
//                {
//                    //CHECK
//                    if(filterList.get(i).getCity().toUpperCase().contains(city))
//                    {
//                        //ADD PLAYER TO FILTERED PLAYERS
//                        filteredPlayers.add(filterList.get(i));
//                    }
//
//
//                }
//
//                if (area != null)
//                {
//                    //CHECK
//                    if(filterList.get(i).getArea().toUpperCase().contains(area))
//                    {
//                        //ADD PLAYER TO FILTERED PLAYERS
//                        filteredPlayers.add(filterList.get(i));
//                    }
//
//                }

                //CHECK
                if(filterList.get(i).getShopName().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }

                //Check
//                if(filterList.get(i).getShopName().toUpperCase().contains(store))
//                {
//                    //ADD PLAYER TO FILTERED PLAYERS
//                    filteredPlayers.add(filterList.get(i));
//                }

            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.customers = (ArrayList<Customer>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
