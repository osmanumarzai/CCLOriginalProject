

package com.tkxel.admin.ordertaking.AllClass;

/**
 * Created by admin on 10/16/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.tkxel.admin.ordertaking.AdopterClass.Adapter_All_Targets;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;


public class All_TargetList extends Activity  {

    ListView main_lisrview;
    private SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_targets);

        main_lisrview = (ListView) findViewById(R.id.main_lisrview);
        mSearchView = (SearchView) findViewById(R.id.searchView1);

        fun_SetData();
        findViewById(R.id.add_new_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    void fun_SetData() {

        main_lisrview.setAdapter(new Adapter_All_Targets(All_TargetList.this, MyConstants.listEmployeeTarget,1));
        main_lisrview.setTextFilterEnabled(true);
        setupSearchView();
    }


    private void setupSearchView() {
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }




}