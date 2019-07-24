

package com.tkxel.admin.ordertaking.AllClass;

/**
 * Created by admin on 10/16/2017.
 */

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.AdopterClass.Adapter_PriceList;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;


public class All_PriceList extends Activity implements View.OnClickListener , SearchView.OnQueryTextListener {

    ListView main_lisrview;

    private SearchView mSearchView;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_product);

        main_lisrview = (ListView) findViewById(R.id.main_lisrview);
        mSearchView = (SearchView) findViewById(R.id.searchView1);
        databaseHelper = new DatabaseHelper(All_PriceList.this);

        fun_SetData();
        findViewById(R.id.add_new_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    int[] colors = {0, 0xFF4B0082, 0}; // red for the example
    TextView line;
    Adapter_PriceList adapter_priceList;

    void fun_SetData() {
        line = (TextView) findViewById(R.id.line);
        adapter_priceList = new Adapter_PriceList(All_PriceList.this, MyConstants.mArrayProducts);
        main_lisrview.setAdapter(adapter_priceList);

        main_lisrview.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        main_lisrview.setDividerHeight(1);
        main_lisrview.setTextFilterEnabled(true);
        line.setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        setupSearchView();
    }


    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);

        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter_priceList.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (TextUtils.isEmpty(newText)) {
            main_lisrview.clearTextFilter();
        } else {
            main_lisrview.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public void onClick(View v) {


//        if (TextUtils.isEmpty(newText)) {
//            main_lisrview.clearTextFilter();
//        } else {
//            main_lisrview.setFilterText(newText);
//        }
    }


}