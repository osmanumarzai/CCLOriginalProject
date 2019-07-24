package com.tkxel.admin.ordertaking.AllClass;

/**
 * Created by admin on 12/20/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.R;

public class ListviewActivity extends Activity {

    private String[] arrText =
            new String[]{"Text1", "Text2", "Text3", "Text4"
                    , "Text5", "Text6", "Text7", "Text8", "Text9", "Text10"
                    , "Text11", "Text12", "Text13", "Text14", "Text15"
                    , "Text16", "Text17", "Text18", "Text19", "Text20"
                    , "Text21", "Text22", "Text23", "Text24"};
    private String[] arrTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeorder);

        arrTemp = new String[arrText.length];

        MyListAdapter myListAdapter = new MyListAdapter();
        ListView listView = (ListView) findViewById(R.id.main_lisrview);
        listView.setAdapter(myListAdapter);
    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if (arrText != null && arrText.length != 0) {
                return arrText.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return arrText[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //ViewHolder holder = null;
            final ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                LayoutInflater inflater = ListviewActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.list_row_products, null);
                holder.textView1 = (TextView) convertView.findViewById(R.id.p_name);
                holder.editText1 = (EditText) convertView.findViewById(R.id.etp_counter);

                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            holder.textView1.setText(arrText[position]);
            holder.editText1.setText(arrTemp[position]);
            holder.editText1.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                    arrTemp[holder.ref] = arg0.toString();
                }
            });

            return convertView;
        }

        private class ViewHolder {
            TextView textView1;
            EditText editText1;
            int ref;
        }


    }

}